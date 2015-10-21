package com.maven.flow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maven.flow.editor.model.Message;
import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblProcess;

/**
 * ���̼���������������̵ĺϷ���
 * 
 * @author kinz
 * @version 1.0 2007-6-7
 * @since JDK1.4
 */

public class WorkFlowChecker {

	private Log log = LogFactory.getLog(this.getClass());// ��־��¼

	private List msgs = new ArrayList();// �������Ϣ

	private int infoCount = 0;// ������Ϣ������

	private int errorCount = 0;// ������Ϣ������

	private int warnCount = 0;// ������Ϣ������

	private int startCount = 0;// ��¼��ʼ��������

	private int endCount = 0;// ��¼������������

	private TblApplication application = null;

	private Map processInRouteCounts = new HashMap();// �洢�������·������

	private Map processOutRouteCounts = new HashMap();// �洢����ĳ�·������

	/**
	 * ����һ�����������
	 * 
	 * @param application
	 */
	public WorkFlowChecker(TblApplication application) {
		this.application = application;
	}

	/**
	 * ��������Ƿ�Ϸ�������Message��һ��List
	 * 
	 * @param application
	 * @return
	 */
	public void checkWorkFlow() {
		this.clearInfos();

		// System.out.println("��ʼ��鹤������[" + application.getAppName() + "]�ĺϷ���");

		checkWholeFlow(application);
		// System.out.println("�������̼����ϣ�������[" + this.getErrorCount() + "]������["
		// + this.getWarnCount() + "]������");

		// ��������Ϣ
		this.processInRouteCounts.clear();
		this.processOutRouteCounts.clear();
	}

	/**
	 * ��ȡ���еļ������Ϣ����Message[]�ķ�ʽ����
	 * 
	 * @return
	 */
	public Message[] getAllCheckMessage() {
		return (Message[]) msgs.toArray(new Message[msgs.size()]);
	}

	/**
	 * ��ȡ���еļ������Ϣ����List�ķ�ʽ����
	 * 
	 * @return
	 */
	public List getMessageList() {
		return msgs;
	}

	/**
	 * ���������飬��������е����̲����Լ�·�� 1������Ƿ�ֻ��һ����ʼ���� 2������Ƿ�ֻ��һ���������� 3������·��������ļ�鷽�����оֲ����
	 * 
	 * @param app
	 * @param msgList
	 */
	private void checkWholeFlow(TblApplication app) {

		// ��������Ϣ
		checkApplication(app);

		// �ȼ�����е�·��
		List routes = app.getAppRoutes();
		for (Iterator it = routes.iterator(); it.hasNext();) {
			this.checkRoute((TblAppRoute) it.next());
		}

		// ������еĲ���
		List processes = app.getProcesses();
		for (Iterator it = processes.iterator(); it.hasNext();) {
			this.checkProcess((TblProcess) it.next());
		}

		if (startCount < 1) {
			this.addError("���̱�����һ����ʼ����");
		} else if (startCount > 1) {
			this.addError("����ֻ����һ����ʼ����");
		}

		if (endCount < 1) {
			this.addError("���̱�����һ����������");
		} else if (endCount > 1) {
			this.addError("����ֻ����һ����������");
		}
	}

	/**
	 * ������̵Ļ�����Ϣ�Ƿ�Ϸ�
	 * 
	 * @param app
	 * @param msgList
	 */
	private void checkApplication(TblApplication app) {
		// �������
		if (StringUtils.isEmpty(app.getAppName())) {
			this.addError("�������������ָ������");
		}

		// �ڼ����Ȩ����Ϊֻ�����Զ��������£�����ָ�������Ȩ��Ա���߽�ɫ
		if (app.getAppMonitorRule().intValue() == TblApplication.MONITOR_RULE_CUSTOMIZE) {
			if (app.getMonitorAccesses() == null
					|| app.getMonitorAccesses().size() == 0) {
				this.addWarn("����ָ���ˡ�ֻ�����Զ��塱�ļ����Ȩģʽ���������̲�δָ���κμ����Ȩ��ɫ����Ա��");
			}
		}
	}

	/**
	 * ���·���Ƿ�Ϸ� ���һ��·���Ŀ�ʼ����ͽ�������ֱ��Ӧ���̵Ŀ�ʼ����ͽ������裬�򲻺Ϸ�
	 * 
	 * @param route
	 * @param msgList
	 */
	private void checkRoute(TblAppRoute route) {
		boolean isStartProcess = false;// ��·���Ƿ�ָ�����̵Ŀ�ʼ����

		// ����Ƿ�������
		if (StringUtils.isEmpty(route.getRouteName()))
			this.addWarn("û��Ϊ·��ָ������");
		// ����Ƿ��п�ʼ����ͽ�������
		if (route.getBeginProcess() == null) {
			this.addError("·��[" + route.getRouteName() + "]ȱ�ٿ�ʼ����");
		} else {
			// ·���Ŀ�ʼ����ĳ�����·��������1
			if (processOutRouteCounts.containsKey(route.getBeginProcess())) {
				Integer c = (Integer) processOutRouteCounts.get(route
						.getBeginProcess());
				processOutRouteCounts.put(route.getBeginProcess(), new Integer(
						c.intValue() + 1));
			} else {
				processOutRouteCounts.put(route.getBeginProcess(), new Integer(
						1));
			}
			isStartProcess = (route.getBeginProcess().getProcessType().intValue() == TblProcess.TYPE_START);
		}
		if (route.getEndProcess() == null) {
			this.addError("·��[" + route.getRouteName() + "]ȱ�ٽ�������");
		} else {
			// ·���Ľ���������뷽��·��������1
			if (processInRouteCounts.containsKey(route.getEndProcess())) {
				Integer c = (Integer) processInRouteCounts.get(route
						.getEndProcess());
				processInRouteCounts.put(route.getEndProcess(), new Integer(c
						.intValue() + 1));
			} else {
				processInRouteCounts.put(route.getEndProcess(), new Integer(1));
			}

			if (isStartProcess
					&& (route.getEndProcess().getProcessType().intValue() ==TblProcess.TYPE_END)) {
				this.addWarn("���̵Ŀ�ʼ����ֱ��ָ�����̵Ľ������裬�����̿��ܲ���ȷ");
			}
		}
	}

	/**
	 * ��鲽���Ƿ�Ϸ�
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkProcess(TblProcess process) {

		// ����ǽ������裬����Ҫ�����¼��
		if (process.getProcessType().intValue() != TblProcess.TYPE_END) {
			// ��������
			if (StringUtils.isEmpty(process.getProcessName())) {
				this.addError("����ȱ������");
			}
			// ����ֽ����
			if (process.getProcessJobSplitRule().intValue() == TblProcess.SPLIT_RULE_CUSTOMIZE
					&& StringUtils.isEmpty(process
							.getProcessJobSplitCustomize())) {
				this.addError("����[" + process.getProcessName()
						+ "]ָ��������ֽ����Ϊ���Զ�����򡱣�����û��ָ������");
			}
			if (process.getProcessJobSplitRule().intValue() == TblProcess.SPLIT_RULE_DEFAULT
					&& (process.getProcessAccesses() == null || process
							.getProcessAccesses().size() == 0)) {
				// ��Ȩ��飬ֻҪ����ָ��Ĭ������ֽ����ʱ����
				this.addError("���� [" + process.getProcessName()
						+ "] ʹ��Ĭ������ֽ����Ҫ�����ָ����Ȩ��ɫ����Ա��");
			}
			// ��ɼ�����
			if (process.getProcessCompleteCheckRule().intValue() == TblProcess.COMPLETE_RULE_WITHCHECK
					&& StringUtils.isEmpty(process
							.getProcessCompleteRuleCustomize())) {
				this.addError("����[" + process.getProcessName()
						+ "]ָ������ɹ���Ϊ����Ҫ��顱������û��ָ������");
			}
		}

		// ��鲽���·����Ϣ
		this.checkProcessRoute(process);

		switch (process.getProcessType().intValue()) {
		case TblProcess.TYPE_START: {
			this.startCount++;
			this.checkStartProcess(process);
			break;
		}
		case TblProcess.TYPE_END: {
			this.endCount++;
			break;
		}
		case TblProcess.TYPE_APPROVAL: {
			this.checkApprovalProcess(process);
			break;
		}
		case TblProcess.TYPE_HANDLE: {
			this.checkHandleProcess(process);
			break;
		}
		}
	}

	/**
	 * �����������·���Ƿ���ȷ 1����ʼ��������ֻ����һ������·�� 2���������������һ�����߶�����·�� 3����������������һ����ͳ���·��
	 */
	private void checkProcessRoute(TblProcess process) {
		int inCount = 0;
		int outCount = 0;
		if (processInRouteCounts.containsKey(process)) {
			inCount = ((Integer) processInRouteCounts.get(process)).intValue();
		}
		if (processOutRouteCounts.containsKey(process)) {
			outCount = ((Integer) processOutRouteCounts.get(process))
					.intValue();
		}

		// ��Բ�ͬ�Ĳ������ͽ��м��
		switch (process.getProcessType().intValue()) {
		case TblProcess.TYPE_APPROVAL:
		case TblProcess.TYPE_HANDLE: {
			if (inCount <= 0)
				this.addError("����[" + process.getProcessName() + "]ȱ���뷽���·��");
			if (outCount <= 0)
				this.addError("����[" + process.getProcessName() + "]ȱ�ٳ������·��");
			break;
		}
		case TblProcess.TYPE_START: {
			if (inCount > 0)
				this.addError("����[" + process.getProcessName()
						+ "]Ϊ���̿�ʼ���裬����������뷽���·��");
			if (outCount <= 0)
				this.addError("����[" + process.getProcessName() + "]ȱ�ٳ������·��");
			// if (outCount > 1)
			// this.addError("����["
			// + process.getProcessName()
			// + "]Ϊ���̿�ʼ���裬ֻ����һ���������·��");
			break;
		}
		case TblProcess.TYPE_END: {
			if (inCount <= 0)
				this.addError("����[" + process.getProcessName() + "]ȱ���뷽���·��");
			if (outCount > 0)
				this.addError("����[" + process.getProcessName()
						+ "]Ϊ���̽������裬�������г������·��");
		}
		}
	}

	/**
	 * ��鿪ʼ��Ĳ����Ƿ�Ϸ�
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkStartProcess(TblProcess process) {
		// ������������ҳ��
		if (StringUtils.isEmpty(process.getHandleURL())) {
			this.addError("����Ϊ��ʼ���� [" + process.getProcessName() + "] ָ�� ����ҳ��");
		}
		// ����ָ����ȨԱ�����߽�ɫ�����������޷�������
		if (process.getProcessAccesses() == null
				|| process.getProcessAccesses().size() == 0) {
			this.addError("����Ϊ��ʼ���� [" + process.getProcessName()
					+ "] ָ����Ȩ��ɫ����Ա��");
		}
	}

	/**
	 * ���������Ĳ����Ƿ�Ϸ�
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkApprovalProcess(TblProcess process) {
		//
	}

	/**
	 * ��������Ĳ����Ƿ�Ϸ�
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkHandleProcess(TblProcess process) {
		// �������ô���ҳ��
		if (StringUtils.isEmpty(process.getHandleURL())) {
			this.addError("����Ϊ�������� [" + process.getProcessName() + "] ָ�� ����ҳ��");
		}
		// ����ָ���鿴ҳ��
		if (StringUtils.isEmpty(process.getViewURL())) {
			this.addError("����Ϊ�������� [" + process.getProcessName() + "] ָ�� �鿴ҳ��");
		}
	}

	private void addError(String message) {
		msgs.add(Message.createErrorMessage(message));
		this.errorCount++;
	}

	private void addInfo(String message) {
		msgs.add(Message.createInfoMessage(message));
		this.infoCount++;
	}

	private void addWarn(String message) {
		msgs.add(Message.createWarnMessage(message));
		this.warnCount++;
	}

	private void clearInfos() {
		msgs.clear();
		this.infoCount = 0;
		this.errorCount = 0;
		this.warnCount = 0;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public int getInfoCount() {
		return infoCount;
	}

	public int getWarnCount() {
		return warnCount;
	}

}
