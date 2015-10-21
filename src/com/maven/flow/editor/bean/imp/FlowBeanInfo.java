package com.maven.flow.editor.bean.imp;

import java.util.Date;

import com.maven.flow.editor.SystemGloable;
import com.maven.flow.editor.adapter.impl.FlowCellInfo;
import com.maven.flow.editor.adapter.impl.FlowEdgeInfo;
import com.maven.flow.editor.adapter.impl.FlowGraphInfo;
import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessAccess;
import com.maven.flow.hibernate.dao.TblProcessPeople;
import com.maven.flow.service.ApplicationService;
import com.maven.flow.service.IDService;
import com.maven.flow.service.ProcessService;
import com.maven.flow.service.RouteService;
import com.maven.flow.service.TblProcessPeopleService;
import com.maven.flow.util.StringUtil;

public class FlowBeanInfo {

	IDService idservice = new IDService();

	ApplicationService appService = new ApplicationService();

	ProcessService processService = new ProcessService();

	RouteService routeService = new RouteService();

	//
	// 模型 流程
	private FlowGraphInfo graphInfo;

	// 流程主信息，
	private TblApplication application;

	// 操作线
	private TblAppRoute[] appRoutes;

	// 操作步骤
	private TblProcess[] appProcess;

	// 步骤访问权限
	private TblProcessAccess[] appProcessaccess;

	public FlowBeanInfo(FlowGraphInfo graphInfo) {
		this.graphInfo = graphInfo;
		analyGraph();
	}

	private void analyGraph() {
		try {

			// 先分解出流程
			initApplication();
			// 再分解出流程步骤
			initPorcess();
			// 再分解流程联线
			initRouter();
			// 流程步骤权限
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public TblApplication getApplication() {

		return application;
	}

	public void setApplication(TblApplication application) {
		this.application = application;
	}

	public TblProcess[] getAppProcess() {
		return appProcess;
	}

	public void setAppProcess(TblProcess[] appProcess) {
		this.appProcess = appProcess;
	}

	public TblProcessAccess[] getAppProcessaccess() {
		return appProcessaccess;
	}

	public void setAppProcessaccess(TblProcessAccess[] appProcessaccess) {
		this.appProcessaccess = appProcessaccess;
	}

	public TblAppRoute[] getAppRoutes() {
		return appRoutes;
	}

	public void setAppRoutes(TblAppRoute[] appRoutes) {
		this.appRoutes = appRoutes;
	}

	private void initApplication() throws Exception {

		Integer appId = new Integer("" + graphInfo.getGraphElement().getAppId());
		TblApplication app = appService.findByAppId(appId);

		if (app != null) {
			app.setAppName(graphInfo.getGraphElement().getName());
			app.setAppDesc(graphInfo.getGraphElement().getDescription());
			app.setApplicationCode(graphInfo.getGraphElement().getApplicationCode());
			try {
				if (!StringUtil
						.isNull(graphInfo.getGraphElement().getCreator())) {
					app.setAppCreateUser(new Integer(graphInfo
							.getGraphElement().getCreator()));
				}
				if (!StringUtil.isNull(graphInfo.getGraphElement()
						.getPublisher())) {
					app.setAppReleaseUser(new Integer(graphInfo
							.getGraphElement().getPublisher()));
				}
			} catch (Exception ex) {
			}
			app.setAppReleaseTime(graphInfo.getGraphElement().getPublishTime());
			// 保存了流程主信息
			// appDao.attachDirty(app);
			//
		} else {
			app = new TblApplication();
			app.setAppValidStatus(new Integer(TblApplication.VALID_NO));
			app.setAppReleaseStatus(new Integer(TblApplication.RELEASE_NO));
			app.setAppName(graphInfo.getGraphElement().getName());
			app.setAppDesc(graphInfo.getGraphElement().getDescription());
			
			app.setApplicationCode(graphInfo.getGraphElement().getApplicationCode());
			
			if (!StringUtil.isNull(graphInfo.getGraphElement().getCreator())) {
				app.setAppCreateUser(new Integer(SystemGloable.CurUserInfo
						.getFemployeeSn()+""));
				app.setAppCreateUserName(SystemGloable.CurUserInfo
						.getFemployeeName());

			}
			if (!StringUtil.isNull(graphInfo.getGraphElement().getPublisher())) {
				app.setAppReleaseUserName(SystemGloable.CurUserInfo
						.getFemployeeSn()+"");
				app.setAppReleaseUser(new Integer(SystemGloable.CurUserInfo
						.getFemployeeName()));
			}
			app.setAppCreateTime(new Date());

			app.setAppFlowData(null);
			// 这里要取一个ID
			appId = new Integer(idservice.getApplicationID());
			app.setAppId(appId);
			// app.setAppFlowData(ByteUtil.objectBase64Encode(graphInfo));
			// appDao.save(app);
			graphInfo.getGraphElement().setAppId(
					new Long(app.getAppId() + "").longValue());
		}
		application = app;

	}

	private void initPorcess() {
		FlowCellInfo[] flowCellInfo = graphInfo.getCells();
		int len = flowCellInfo.length;
		appProcess = new TblProcess[len];
		for (int i = 0; i < len; i++) {
			appProcess[i] = new TblProcess();
			appProcess[i].setAppId(this.application.getAppId());
			appProcess[i].assign(flowCellInfo[i]);

			if (appProcess[i].getProcessId() == null) {
				//int newPId = idservice.getProcessID();
				//appProcess[i].setProcessId(new Integer(newPId));
				//flowCellInfo[i].setProcessId(newPId);
			}
		}
	}

	private void initRouter() {
		FlowEdgeInfo[] flowedgeInfos = graphInfo.getEdges();
		int len = flowedgeInfos.length;
		appRoutes = new TblAppRoute[len];
		for (int i = 0; i < len; i++) {
			appRoutes[i] = new TblAppRoute();
			appRoutes[i].assign(flowedgeInfos[i]);
			appRoutes[i].setAppId(this.application.getAppId());
		}
	}

	public void Save() throws Exception {

		Integer appId = application.getAppId();
		//appService.deleteyAppId(appId);
		processService.deleteAllProcessByAppId(appId);
		//
		this.appService.createApplication(this.application);
		

		TblProcessPeopleService pps=new TblProcessPeopleService();
		
		for (int i = 0; i < appProcess.length; i++) {
			int savw = processService.save(appProcess[i]);
			pps.deleteAllProcessByProcessId(appProcess[i].getProcessId());
			
			//System.out.println("processName="+appProcess[i].getProcessName()+",process People size="+appProcess[i].getAccessRoles().size()+",processId="+appProcess[i].getProcessId());
			if(appProcess[i].getAccessRoles()!=null && appProcess[i].getAccessRoles().size()>0){
				for(int j=0;j<appProcess[i].getAccessRoles().size();j++){
					TblProcessPeople tpp=(TblProcessPeople)appProcess[i].getAccessRoles().get(j);
					tpp.setFsn(new Integer(idservice.getProcessPeopleId()));
					pps.save(tpp);
				}
			}
		}
		
		routeService.deleteRouteByAppId(new Integer(appId+""));
		
		for (int i = 0; i < this.appRoutes.length; i++) {
			
			try {
				TblAppRoute appRoute=appRoutes[i];
				/**
				TblProcess begin = processService.findByPrimaryKey(new Integer(
						appRoutes[i].getBeginProcessId() + ""));
				TblProcess targetProcess = processService
						.findByPrimaryKey(new Integer(appRoutes[i]
								.getTargetProcessId()
								+ ""));
				*/
				
				appRoute.setAppId(appId);
				appRoute.setRouteId(new Integer(idservice.getRouteID()));
				//appRoutes[i].setBeginProcess(begin);
				//appRoutes[i].setEndProcess(targetProcess);
				//appRoutes[i].setRouteBeginProcess(begin.getProcessId().intValue());
				//appRoutes[i].setRouteEndProcess(targetProcess.getProcessId().intValue());
				//System.out.println("--------------------the appRoute==========="+appRoute);
				routeService.save(appRoute);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
