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

//model �µ���ԭ����bean  hibernate�µ����µ��� add by maven 2009-4-27
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

	// in�Ǵ������ݿ��е���
	public boolean deploy(TblApplication application) throws Exception {
		boolean flag = false;
		try {

			// ������Ϊapplication����.
			// Application application = (Application) ByteUtil
			// .objectBase64Decode(new String(this.readBytes(in)));
			// �����ݿ���ȡ������
			Integer appid =new Integer("" + application.getAppId());
			//
			// log.info("�������");
			// System.out.println("�������");
			WorkFlowChecker flowChecker = new WorkFlowChecker(application);// ���̼����
			flowChecker.checkWorkFlow();

			// flagresult.setMessages(flowChecker.getMessageList());

			// ���������Ϣ����0�����ܽ��з���

			if (flowChecker.getErrorCount() > 0) {
				// log.info("������̫�� [" + application.getAppName() +
				// "]"+flowChecker.getErrorCount());
				// System.out.println("������̫�� [" + application.getAppName() +
				// "]"+flowChecker.getErrorCount());
				List list = flowChecker.getMessageList();
				WorkFlowEditor editor = (WorkFlowEditor) SystemGloable.frame;
				editor.messageView.addMessage(list);
				return flag;
			}

			log.info("��ʼ�������� [" + application.getAppName() + "]");

			try {
				// Integer i = Integer.parseInt("" + application.getAppId());
				// ת���µ���
				// System.out.println("appid==========="+appid);
				TblApplication oldApp = appService.findByAppId(appid);
				if (oldApp==null)
				{
					
					//���û�б��棬���ȱ���
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

				//	appService.createApplication(oldApp);// �������̶�����.
				//}
			} catch (Exception ex) {
				// ����Ѿ����������
				ex.printStackTrace();
				// this.prepareForRedeploy(application);
				//this.clearDeployInfo(application);

				//System.out.println("����������Ϣ��������");

			}

			// ���̵ļ��Ȩ��[�����ȫ�ֵ�]
			// try {
			// �������̼���Զ�����Ȩ��Ϣ
			// List monitorAccesses = application.getMonitorAccesses();
			// log.info("�����̹���[" + monitorAccesses.size() + "]�������Ȩ��Ϣ");
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
			// ����Ѿ����������
			 this.prepareForRedeploy(application);
			 //this.clearDeployInfo(application);

			// log.error("��������Ȩ��Ϣ��������", ex);
			// result.setResult(ResultObject.FAILED);
			// result.getMessages().add(
			// Message.createErrorMessage(ex.getMessage()));
			// return result;
			// }
			//System.out.println("22=============");
			//log.info("·��");
			try {
				List routes = application.getAppRoutes();// ���ȫ��·����Ϣ.

				// log.info("�����̹���[" + routes.size() + "]��·����Ϣ");
				// AppRouteLogic routeLogic = new AppRouteLogic();

				// ÿ��·������һ����ʼ�����һ����������.
				for (Iterator it = routes.iterator(); it.hasNext();) {
					// ����ÿһ��·��
					TblAppRoute r = (TblAppRoute) it.next();

					// �ȴ���·����صĲ���
					TblProcess start = r.getBeginProcess();
					TblProcess end = r.getEndProcess();
					if (start != null) {
						start.setAppId(appid);
						this.createProcess(start);
						r.setRouteBeginProcess(start.getProcessId());
					}

					// ��Ҫ�޸�����....���Process end��������������̵ĳ��ڵ�..��ô��Ӧ�ü�¼��.��Process
					// ��processId.
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
					//System.out.println("AA��������·����������");
					routeService.save(r);
					//System.out.println("BB��������·����������");
				}

			} catch (Exception ex) {
				// ����Ѿ����������
				// this.prepareForRedeploy(application);
				ex.printStackTrace();
				//this.clearDeployInfo(application);

				//System.out.println("��������·����������");
				// result.setResult(ResultObject.FAILED);
				// result.getMessages().add(
				// Message.createErrorMessage(ex.getMessage()));
				// return result;
			}
			//System.out.println("33=============");
			// try {
			// ���벽��Ŀ¼Ȩ����Ϣ
			// this.createProcessDocFolderAccess(application);
			// } catch (Exception ex) {
			// ����Ѿ����������
			// this.prepareForRedeploy(application);
			// this.clearDeployInfo(application);

			// log.error("���벽���ĵ�Ŀ¼Ȩ�޷�������", ex);
			// result.setResult(ResultObject.FAILED);
			// result.getMessages().add(
			// Message.createErrorMessage(ex.getMessage()));
			// return result;
			// }
			flag = true;
			//log.info("���̷������");
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
	 * ���湤������ͼ
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
				throw new Exception("���̲�����");
			}
			byte[] datas = app.getAppFlowData().getBytes();
			if (datas == null || datas.length == 0) {
				throw new Exception("�������ݲ���ȷ�������´���һ������");
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
		// ����Ѿ����뵽���ݿ⣬Ҳ�����룬�ж����ݣ�ProcessId>0
		//System.out.println("process.getProcessId()=="+process.getProcessId());
		//if (process.getProcessId()!=null)
		//	return;
		log.info("��ʼ������[" + process.getProcessName() + "]");
		// ��ʼ�������еĻ������������
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
		 * process);// ������˲��� break; case Process.TYPE_HANDLE: new
		 * HandleProcessLogic() .createHandleProcess((HandleProcess) process);//
		 * ������. break; case Process.TYPE_START: new
		 * StartProcessLogic().createStartProcess((StartProcess) process);//
		 * ��ʼ����. break; default: new ProcessLogic().createProcess(process);//
		 * Ĭ�ϲ���. }
		 */
		// ���벽��Ȩ��
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
		// Ŀ¼�ṹ�ȷ�������add by maven 2009 .4 .27

		// ���벽��Ŀ¼�ṹ
		/*
		 * List folders = process.getDocFolders(); log.info("����Ŀ¼��" + (folders ==
		 * null ? 0 : folders.size())); if (folders == null || folders.size() ==
		 * 0) { // ���û��Ŀ¼������Ĭ��Ŀ¼ ProcessDocFolderTemplate folder = new
		 * ProcessDocFolderTemplate();
		 * folder.setProcessDocTemplateFolderName(process.getProcessName());
		 * folder.setSubProjectFlag(ProcessDocFolderTemplate.SUB_PROJECT_NO);
		 * folder.setProcessDocTemplateFolderDesc(process.getProcessName());
		 * 
		 * this.createProcessDocFolder(folder, null, process); // Ĭ�ϸ���Ŀ¼��Ȩ
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
		log.info("����[" + process.getProcessName() + "]�������");
	}

	/**
	 * ���벽����ĵ�Ŀ¼�ṹ�����õݹ�ķ�ʽ�������е�Ŀ¼�ṹ
	 * 
	 * @param docFolder
	 * @param parentFolder
	 *            ����ϼ�Ŀ¼Ϊnull�����ʾ�ǵ�һ���Ŀ¼
	 * @param process
	 * @throws Exception
	 */
	/*
	 * private void createProcessDocFolder(ProcessDocFolderTemplate docFolder,
	 * ProcessDocFolderTemplate parentFolder, Process process) throws Exception {
	 * log.info("�����ĵ�Ŀ¼[" + docFolder.getProcessDocTemplateFolderName() + ", �ϼ�: " +
	 * (parentFolder == null ? "��" : parentFolder
	 * .getProcessDocTemplateFolderName()) + ", ����: " + process.getProcessName() +
	 * "]"); docFolder.setProcessId(process.getProcessId()); if (parentFolder !=
	 * null) { docFolder.setParentFolderId(parentFolder
	 * .getProcessDocTemplateFolderId()); } else {
	 * docFolder.setParentFolderId(0); } // ����Ŀ¼ new
	 * ProcessDocFolderTemplateLogic()
	 * .createProcessDocFolderTemplate(docFolder); // ������Ŀ¼ List children =
	 * docFolder.getChildrenFolders(); if (children != null && children.size() >
	 * 0) { for (Iterator it = children.iterator(); it.hasNext();) {
	 * ProcessDocFolderTemplate folder = (ProcessDocFolderTemplate) it .next();
	 * this.createProcessDocFolder(folder, docFolder, process); } }
	 * log.info("�ĵ�Ŀ¼[" + docFolder.getProcessDocTemplateFolderName() +
	 * "]�������"); }
	 */
	/**
	 * �������в�����ĵ�Ŀ¼Ȩ�ޣ����������в��衢�����ĵ��ṹ������ɺ�ִ��
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
		log.info("����׼�������������");

		// ɾ������Ŀ¼Ȩ��
		// new ProcessDocFolderAccessLogic()
		// .deleteDocFolderAccessByAppId(application.getAppId());
		// log.info("����Ŀ¼Ȩ����Ϣ�����");

		// ɾ������Ŀ¼
		// new
		// ProcessDocFolderTemplateLogic().deleteDocFolderByAppId(application
		// .getAppId());
		// log.info("�����ĵ�Ŀ¼��Ϣ�����");

		// ɾ������Ȩ��
		this.processAccessService.deleteAllProcessByAppId(appid);
		log.info("����Ȩ����Ϣ�����");

		// ɾ����ʼ����
		proService.deleteAllProcessByAppId(appid);
		// log.info("��ʼ������Ϣ�����");

		// ɾ����������
		// new ApprovalProcessLogic().deleteApprovalProcessByAppId(application
		// .getAppId());
		// log.info("����������Ϣ�����");

		// ɾ��������Ĳ���
		// new HandleProcessLogic().deleteHandleProcessByAppId(application
		// .getAppId());
		// log.info("����������Ϣ�����");

		// ɾ�����в��������Ϣ
		// new ProcessLogic().deleteProcessByAppId(application.getAppId());
		// log.info("���������Ϣ�����");

		// ɾ������·����Ϣ
		routeService.deleteRouteByAppId(appid);
		log.info("·����Ϣ�����");

		// ɾ�������Ȩ��Ϣ
		// new ApplicationMonitorAccessLogic()
		// .deleteMonitorAccessByAppId(application.getAppId());
		// log.info("���̼����Ȩ��Ϣ�����");

		// ɾ�����̻�����Ϣ
		appService.appDAO.deleteyAppId(appid);
		log.info("���̻�����Ϣ�����");
	}

	/**
	 * ��һЩ���·�����׼������ 1��ɾ�������Ȩ��Ϣ 2��ɾ����ز����������Ϣ 3��ɾ������·����Ϣ ԭ����ɾ����������Ϣ
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
	 * ��ȡ��ɫ��Ϣ
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
	 * ��ȡԱ����Ϣ
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
