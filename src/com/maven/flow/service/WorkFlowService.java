package com.maven.flow.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maven.flow.editor.SystemGloable;
import com.maven.flow.editor.adapter.ResultObject;
import com.maven.flow.editor.adapter.impl.FlowGraphInfo;
import com.maven.flow.editor.ui.WorkFlowEditor;
import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessAccess;
import com.maven.flow.util.ByteUtil;
import com.maven.flow.util.StringUtil;
import com.maven.flow.util.WorkFlowChecker;

//model 下的是原来的bean  hibernate下的是新的类 add by maven 2009-4-27
public class WorkFlowService {

	private Log log = LogFactory.getLog(this.getClass());

	private final IDService  idService=new IDService();
	//
	private final ApplicationService appService = new ApplicationService();
	
	private final ProcessService  proService=new ProcessService();
	
	private final ProcessAccessService processAccessService=new ProcessAccessService();
	
	private final RoleService roleService=new RoleService();
	
	private final EmployeeInfoService employeeService=new EmployeeInfoService();
	
	private final RouteService routeService =new RouteService();

	// in是存入数据库中的流
	public boolean deploy(TblApplication application) throws Exception {
		boolean flag = false;
		try {

			// 反解码为application对象.
			// Application application = (Application) ByteUtil
			// .objectBase64Decode(new String(this.readBytes(in)));
			// 从数据库中取到流程
			Integer appid =new Integer("" + application.getAppId());
			//
			// log.info("检查流程");
			// System.out.println("检查流程");
			WorkFlowChecker flowChecker = new WorkFlowChecker(application);// 流程检查器
			flowChecker.checkWorkFlow();

			// flagresult.setMessages(flowChecker.getMessageList());

			// 如果错误信息大于0，则不能进行发布

			if (flowChecker.getErrorCount() > 0) {
				// log.info("错误数太多 [" + application.getAppName() +
				// "]"+flowChecker.getErrorCount());
				// System.out.println("错误数太多 [" + application.getAppName() +
				// "]"+flowChecker.getErrorCount());
				List list = flowChecker.getMessageList();
				WorkFlowEditor editor = (WorkFlowEditor) SystemGloable.frame;
				editor.messageView.addMessage(list);
				return flag;
			}

			log.info("开始发布流程 [" + application.getAppName() + "]");

			try {
				// Integer i = Integer.parseInt("" + application.getAppId());
				// 转成新的类
				// System.out.println("appid==========="+appid);
				TblApplication oldApp = appService.findByAppId(appid);
				if (oldApp==null)
				{
					
					//如果没有保存，则先保存
					appService.createApplication(application);
					//appid = Integer.parseInt("" + application.getAppId());
					oldApp = appService.findByAppId(appid);
				}
				oldApp.setAppValidStatus(new Integer(TblApplication.VALID_YES));
				oldApp.setAppReleaseStatus(new Integer(TblApplication.RELEASE_YES));
                
				//
				//System.out.println("11  application.getAppId()=="
				//		+ application.getAppId());
				//System.out.println("11  oldApp.getAppId()=="
				//		+ oldApp.getAppId());
				//
				//if (application.getAppId() > 0) {
                this.clearDeployInfo(application);
				appService.updateApplication(oldApp);
				//} else {

				//	appService.createApplication(oldApp);// 创建流程对象本身.
				//}
			} catch (Exception ex) {
				// 清空已经插入的数据
				ex.printStackTrace();
				// this.prepareForRedeploy(application);
				//this.clearDeployInfo(application);

				//System.out.println("插入流程信息发生错误");

			}

			// 流程的监控权限[相对于全局的]
			// try {
			// 插入流程监控自定义授权信息
			// List monitorAccesses = application.getMonitorAccesses();
			// log.info("该流程共有[" + monitorAccesses.size() + "]个监控授权信息");
			// ApplicationMonitorAccessLogic amaLogic = new
			// ApplicationMonitorAccessLogic();
			// if (monitorAccesses != null && monitorAccesses.size() > 0) {
			// for (Iterator it = monitorAccesses.iterator(); it.hasNext();) {
			// ApplicationMonitorAccess ama = (ApplicationMonitorAccess) it
			// .next();
			// ama.setAppId(application.getAppId());
			// amaLogic.createMonitorAccess(ama);
			// }
			// }
			// } catch (Exception ex) {
			// 清空已经插入的数据
			 this.prepareForRedeploy(application);
			 //this.clearDeployInfo(application);

			// log.error("插入监控授权信息发生错误", ex);
			// result.setResult(ResultObject.FAILED);
			// result.getMessages().add(
			// Message.createErrorMessage(ex.getMessage()));
			// return result;
			// }
			//System.out.println("22=============");
			//log.info("路径");
			try {
				List routes = application.getAppRoutes();// 获得全部路径信息.

				// log.info("该流程共有[" + routes.size() + "]个路径信息");
				// AppRouteLogic routeLogic = new AppRouteLogic();

				// 每个路径都有一个开始步骤和一个结束步骤.
				for (Iterator it = routes.iterator(); it.hasNext();) {
					// 处理每一个路径
					TblAppRoute r = (TblAppRoute) it.next();

					// 先处理路径相关的步骤
					TblProcess start = r.getBeginProcess();
					TblProcess end = r.getEndProcess();
					if (start != null) {
						start.setAppId(appid);
						this.createProcess(start);
						r.setRouteBeginProcess(start.getProcessId());
					}

					// 需要修改这里....如果Process end这个对象是子流程的初节点..那么就应该记录起.父Process
					// 的processId.
					if (end != null) {
						end.setAppId(appid);
						this.createProcess(end);
						r.setRouteEndProcess(end.getProcessId());
					}
                    //----
                    
                    //
                    //
                    //
                    //
					r.setAppId(appid);
					r.setRouteId(new Integer(this.idService.getRouteID()));
					//System.out.println("AA插入流程路径发生错误");
					routeService.save(r);
					//System.out.println("BB插入流程路径发生错误");
				}

			} catch (Exception ex) {
				// 清空已经插入的数据
				// this.prepareForRedeploy(application);
				ex.printStackTrace();
				//this.clearDeployInfo(application);

				//System.out.println("插入流程路径发生错误");
				// result.setResult(ResultObject.FAILED);
				// result.getMessages().add(
				// Message.createErrorMessage(ex.getMessage()));
				// return result;
			}
			//System.out.println("33=============");
			// try {
			// 插入步骤目录权限信息
			// this.createProcessDocFolderAccess(application);
			// } catch (Exception ex) {
			// 清空已经插入的数据
			// this.prepareForRedeploy(application);
			// this.clearDeployInfo(application);

			// log.error("插入步骤文档目录权限发生错误", ex);
			// result.setResult(ResultObject.FAILED);
			// result.getMessages().add(
			// Message.createErrorMessage(ex.getMessage()));
			// return result;
			// }
			flag = true;
			//log.info("流程发布完成");
			//System.out.println("44=============");
			// return new Long(application.getAppId());
			// result.setObject(new Long(application.getAppId()));

			// return result;
		} catch (Exception ex) {
			throw ex;
		}
		return flag;
	}

	/**
	 * 保存工作流程图
	 * 
	 * @param in
	 * @return
	 */
	private void save(InputStream in, long operator) throws Exception {

		byte[] datas = this.readBytes(in);

		FlowGraphInfo graphInfo = (FlowGraphInfo) ByteUtil
				.objectBase64Decode(new String(datas));

		Integer appId =new Integer(""
				+ graphInfo.getGraphElement().getAppId());
		TblApplication app = appService.findByAppId(appId);
		if (app != null) {
			app.setAppName(graphInfo.getGraphElement().getName());
			app.setAppDesc(graphInfo.getGraphElement().getDescription());
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
			app.setAppFlowData(new String(datas));
			appService.updateApplication(app);
			// appService.updateFlowData(app.getAppId(), datas);

		} else {
			app = new TblApplication();
			app.setAppCreateTime(graphInfo.getGraphElement().getCreateTime());
			// app.setAppCreateUser(Integer.parseInt(graphInfo.getGraphElement().getCreator()));
			// app
			app.setAppValidStatus(new Integer(TblApplication.VALID_NO));
			app.setAppReleaseStatus(new Integer(TblApplication.RELEASE_NO));
			app.setAppName(graphInfo.getGraphElement().getName());
			app.setAppDesc(graphInfo.getGraphElement().getDescription());
			if (!StringUtil.isNull(graphInfo.getGraphElement().getCreator())) {
				app.setAppCreateUser(new Integer(graphInfo
						.getGraphElement().getCreator()));
			}
			if (!StringUtil.isNull(graphInfo.getGraphElement().getPublisher())) {
				app.setAppReleaseUser(new Integer(graphInfo
						.getGraphElement().getPublisher()));
			}
			app.setAppCreateTime(new Date());

			app.setAppFlowData(null);

			graphInfo.getGraphElement().setAppId(new Long(app.getAppId()+"").longValue());
			app.setAppFlowData(new String(datas));
			appService.createApplication(app);

		}

	}

	private ResultObject load(Integer appId) throws Exception {
		try {

			// String fileName = (String) ByteUtil.objectBase64Decode(new
			// String(
			// this.readBytes(in)));

			/*
			 * byte[] datas = FileUtil.loadFromFile("workflow/" + fileName);
			 * 
			 * FlowGraphInfo info = (FlowGraphInfo)
			 * ByteUtil.objectBase64Decode(new String(datas));
			 */

			// Integer appId = -1;
			// try {
			// appId = Long.parseLong(fileName);
			// } catch (Exception e) {
			// }
			TblApplication app = appService.appDAO.findById(appId);
			if (app == null) {
				throw new Exception("流程不存在");
			}
			byte[] datas = app.getAppFlowData().getBytes();
			if (datas == null || datas.length == 0) {
				throw new Exception("流程数据不正确，请重新创建一个流程");
			}
			FlowGraphInfo info = (FlowGraphInfo) ByteUtil
					.objectBase64Decode(new String(datas));
			if (app.getAppValidStatus().intValue() == TblApplication.VALID_YES) {
				info.getGraphElement().setDeployed(true);
			} else {
				info.getGraphElement().setDeployed(false);
			}
			info.getGraphElement().setName(app.getAppName());
			info.getGraphElement().setDescription(app.getAppDesc());
			info.getGraphElement().setValidStatus(app.getAppValidStatus().intValue());
			info.getGraphElement().setReleaseStatus(app.getAppReleaseStatus().intValue());

			ResultObject ro = new ResultObject();
			ro.setResult(ResultObject.SUCCESS);
			ro.setObject(info);
			return ro;
		} catch (Exception ex) {
			// ro.setResult(ResultObject.FAILED);
			throw ex;
		}
	}

	private byte[] readBytes(InputStream in) throws Exception {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		int c = -1;

		while ((c = in.read()) != -1) {
			out.write(c);
		}

		byte[] datas = out.toByteArray();

		return datas;
	}

	
	//
	private void createProcess(TblProcess process) throws Exception {
		if (process == null)
			return;
		// 如果已经插入到数据库，也不插入，判断依据：ProcessId>0
		//System.out.println("process.getProcessId()=="+process.getProcessId());
		//if (process.getProcessId()!=null)
		//	return;
		log.info("开始处理步骤[" + process.getProcessName() + "]");
		// 开始插入所有的活动－－操作步骤
		//System.out.println("process.getProcessId()="+process.getProcessId());
		if (process.getProcessId()==null)
		{
			Integer i=new Integer(idService.getProcessID());
			process.setProcessId(i);
		}
		proService.save(process);
		/*
		 * switch (process.getProcessType()) { case Process.TYPE_APPROVAL: new
		 * ApprovalProcessLogic() .createApprovalProcess((ApprovalProcess)
		 * process);// 创建审核步骤 break; case Process.TYPE_HANDLE: new
		 * HandleProcessLogic() .createHandleProcess((HandleProcess) process);//
		 * 处理步骤. break; case Process.TYPE_START: new
		 * StartProcessLogic().createStartProcess((StartProcess) process);//
		 * 开始步骤. break; default: new ProcessLogic().createProcess(process);//
		 * 默认步骤. }
		 */
		// 插入步骤权限
		List accesses = process.getProcessAccesses();
		if (accesses != null && accesses.size() > 0) {
			// this.appService.processAccessDAO
			for (Iterator it = accesses.iterator(); it.hasNext();) {
				TblProcessAccess a = (TblProcessAccess) it.next();
				a.setProcessId(process.getProcessId());
				Integer processAccessRecordId=new Integer(idService.getProcessAccessID());
				//System.out.println("processAccessRecordId=="+processAccessRecordId);
				a.setProcessAccessRecordId(processAccessRecordId);
				processAccessService.save(a);
			}
		}
		// 目录结构先放下来，add by maven 2009 .4 .27

		// 插入步骤目录结构
		/*
		 * List folders = process.getDocFolders(); log.info("步骤目录：" + (folders ==
		 * null ? 0 : folders.size())); if (folders == null || folders.size() ==
		 * 0) { // 如果没有目录，创建默认目录 ProcessDocFolderTemplate folder = new
		 * ProcessDocFolderTemplate();
		 * folder.setProcessDocTemplateFolderName(process.getProcessName());
		 * folder.setSubProjectFlag(ProcessDocFolderTemplate.SUB_PROJECT_NO);
		 * folder.setProcessDocTemplateFolderDesc(process.getProcessName());
		 * 
		 * this.createProcessDocFolder(folder, null, process); // 默认给该目录授权
		 * ProcessDocFolderAccess pfa = new ProcessDocFolderAccess();
		 * pfa.setDocFolder(folder);
		 * pfa.setAddFolderFlag(ProcessDocFolderAccess.FLAG_YES);
		 * pfa.setDeleteFolderFlag(ProcessDocFolderAccess.FLAG_YES);
		 * pfa.setOperateDocFlag(ProcessDocFolderAccess.FLAG_YES);
		 * pfa.setUpdateFolderFlag(ProcessDocFolderAccess.FLAG_YES);
		 * pfa.setViewDocFlag(ProcessDocFolderAccess.FLAG_YES); if
		 * (process.getDocFolderAccess() == null) { List tmp = new ArrayList();
		 * tmp.add(pfa); process.setDocFolderAccess(tmp); } else {
		 * process.getDocFolderAccess().add(pfa); } } else { for (Iterator it =
		 * folders.iterator(); it.hasNext();) { ProcessDocFolderTemplate folder =
		 * (ProcessDocFolderTemplate) it .next();
		 * this.createProcessDocFolder(folder, null, process); } }
		 */
		log.info("步骤[" + process.getProcessName() + "]处理完毕");
	}

	/**
	 * 插入步骤的文档目录结构，采用递归的方式插入所有的目录结构
	 * 
	 * @param docFolder
	 * @param parentFolder
	 *            如果上级目录为null，则表示是第一层的目录
	 * @param process
	 * @throws Exception
	 */
	/*
	 * private void createProcessDocFolder(ProcessDocFolderTemplate docFolder,
	 * ProcessDocFolderTemplate parentFolder, Process process) throws Exception {
	 * log.info("处理文档目录[" + docFolder.getProcessDocTemplateFolderName() + ", 上级: " +
	 * (parentFolder == null ? "无" : parentFolder
	 * .getProcessDocTemplateFolderName()) + ", 步骤: " + process.getProcessName() +
	 * "]"); docFolder.setProcessId(process.getProcessId()); if (parentFolder !=
	 * null) { docFolder.setParentFolderId(parentFolder
	 * .getProcessDocTemplateFolderId()); } else {
	 * docFolder.setParentFolderId(0); } // 插入目录 new
	 * ProcessDocFolderTemplateLogic()
	 * .createProcessDocFolderTemplate(docFolder); // 插入子目录 List children =
	 * docFolder.getChildrenFolders(); if (children != null && children.size() >
	 * 0) { for (Iterator it = children.iterator(); it.hasNext();) {
	 * ProcessDocFolderTemplate folder = (ProcessDocFolderTemplate) it .next();
	 * this.createProcessDocFolder(folder, docFolder, process); } }
	 * log.info("文档目录[" + docFolder.getProcessDocTemplateFolderName() +
	 * "]处理完毕"); }
	 */
	/**
	 * 插入所有步骤的文档目录权限，必需在所有步骤、所有文档结构插入完成后执行
	 * 
	 * @param application
	 */
	/*
	 * private void createProcessDocFolderAccess(Application application) throws
	 * Exception { ProcessDocFolderAccessLogic logic = new
	 * ProcessDocFolderAccessLogic(); List ps = application.getProcesses(); for
	 * (Iterator it = ps.iterator(); it.hasNext();) { Process p = (Process)
	 * it.next(); List pdas = p.getDocFolderAccess(); for (Iterator it1 =
	 * pdas.iterator(); it1.hasNext();) { ProcessDocFolderAccess a =
	 * (ProcessDocFolderAccess) it1.next(); if (a.getDocFolder() == null)
	 * continue; a.setProcessDocFolderId(a.getDocFolder()
	 * .getProcessDocTemplateFolderId()); a.setProcessId(p.getProcessId());
	 * 
	 * logic.createProcessDocFolderAccess(a); } } }
	 */
	private void clearDeployInfo(TblApplication application) throws Exception {

		Integer appid =new Integer("" + application.getAppId());
		log.info("正在准备清空流程数据");

		// 删除步骤目录权限
		// new ProcessDocFolderAccessLogic()
		// .deleteDocFolderAccessByAppId(application.getAppId());
		// log.info("步骤目录权限信息已清空");

		// 删除步骤目录
		// new
		// ProcessDocFolderTemplateLogic().deleteDocFolderByAppId(application
		// .getAppId());
		// log.info("步骤文档目录信息已清空");

		// 删除步骤权限
		this.processAccessService.deleteAllProcessByAppId(appid);
		log.info("步骤权限信息已清空");

		// 删除开始步骤
		proService.deleteAllProcessByAppId(appid);
		// log.info("开始步骤信息已清空");

		// 删除审批步骤
		// new ApprovalProcessLogic().deleteApprovalProcessByAppId(application
		// .getAppId());
		// log.info("审批步骤信息已清空");

		// 删除处理类的步骤
		// new HandleProcessLogic().deleteHandleProcessByAppId(application
		// .getAppId());
		// log.info("操作步骤信息已清空");

		// 删除所有步骤基本信息
		// new ProcessLogic().deleteProcessByAppId(application.getAppId());
		// log.info("步骤基本信息已清空");

		// 删除所有路径信息
		routeService.deleteRouteByAppId(appid);
		log.info("路径信息已清空");

		// 删除监控授权信息
		// new ApplicationMonitorAccessLogic()
		// .deleteMonitorAccessByAppId(application.getAppId());
		// log.info("流程监控授权信息已清空");

		// 删除流程基本信息
		appService.appDAO.deleteyAppId(appid);
		log.info("流程基本信息已清空");
	}

	/**
	 * 做一些重新发布的准备工作 1、删除监控授权信息 2、删除相关步骤的所有信息 3、删除所有路径信息 原则：先删除最外层的信息
	 * 
	 * @param application
	 */
	private void prepareForRedeploy(TblApplication application)
			throws Exception {
		TblApplication app = appService.findByAppId(new Integer(""
				+ application.getAppId()));
		if (app != null) {
			// app.setAppValidStatus(Application.VALID_NO);
			app.setAppReleaseStatus(new Integer(TblApplication.RELEASE_NO));
			appService.updateApplication(app);

		}
	}

	/**
	 * 获取角色信息
	 * 
	 * @return
	 */
	private ResultObject getRoles() throws Exception {
		ResultObject result = new ResultObject();
		result.setResult(ResultObject.SUCCESS);
		result.setObject(roleService.getRoleList());

		return result;
	}

	/**
	 * 获取员工信息
	 * 
	 * @return
	 */
	private ResultObject getUsers() throws Exception {
		ResultObject result = new ResultObject();
		result.setResult(ResultObject.SUCCESS);
		result.setObject(employeeService.getAllEmployeeInfo());

		return result;
	}
	public static void main(String[] args)
	{
		WorkFlowService s=new WorkFlowService();
		TblApplication application=new TblApplication();
		try
		{
			application.setAppId(new Integer(18));
		s.clearDeployInfo(application);
		}
		catch(Exception ex)
		{ex.printStackTrace();}
	}

}
