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
 * 流程检查器，负责检查流程的合法性
 * 
 * @author kinz
 * @version 1.0 2007-6-7
 * @since JDK1.4
 */

public class WorkFlowChecker {

	private Log log = LogFactory.getLog(this.getClass());// 日志记录

	private List msgs = new ArrayList();// 检查结果信息

	private int infoCount = 0;// 常规信息的数量

	private int errorCount = 0;// 错误信息的数量

	private int warnCount = 0;// 警告信息的数量

	private int startCount = 0;// 记录开始步骤数量

	private int endCount = 0;// 记录结束步骤数量

	private TblApplication application = null;

	private Map processInRouteCounts = new HashMap();// 存储步骤的入路径数量

	private Map processOutRouteCounts = new HashMap();// 存储步骤的出路径数量

	/**
	 * 构建一个检查器对象
	 * 
	 * @param application
	 */
	public WorkFlowChecker(TblApplication application) {
		this.application = application;
	}

	/**
	 * 检查流程是否合法，返回Message的一个List
	 * 
	 * @param application
	 * @return
	 */
	public void checkWorkFlow() {
		this.clearInfos();

		// System.out.println("开始检查工作流程[" + application.getAppName() + "]的合法性");

		checkWholeFlow(application);
		// System.out.println("工作流程检查完毕，共检查出[" + this.getErrorCount() + "]个错误，["
		// + this.getWarnCount() + "]个警告");

		// 清空相关信息
		this.processInRouteCounts.clear();
		this.processOutRouteCounts.clear();
	}

	/**
	 * 获取所有的检查结果信息，以Message[]的方式返回
	 * 
	 * @return
	 */
	public Message[] getAllCheckMessage() {
		return (Message[]) msgs.toArray(new Message[msgs.size()]);
	}

	/**
	 * 获取所有的检查结果消息，以List的方式返回
	 * 
	 * @return
	 */
	public List getMessageList() {
		return msgs;
	}

	/**
	 * 流程整体检查，将检查所有的流程步骤以及路径 1、检查是否只有一个开始步骤 2、检查是否只有一个结束步骤 3、调用路径、步骤的检查方法进行局部检查
	 * 
	 * @param app
	 * @param msgList
	 */
	private void checkWholeFlow(TblApplication app) {

		// 检查基本信息
		checkApplication(app);

		// 先检查所有的路径
		List routes = app.getAppRoutes();
		for (Iterator it = routes.iterator(); it.hasNext();) {
			this.checkRoute((TblAppRoute) it.next());
		}

		// 检查所有的步骤
		List processes = app.getProcesses();
		for (Iterator it = processes.iterator(); it.hasNext();) {
			this.checkProcess((TblProcess) it.next());
		}

		if (startCount < 1) {
			this.addError("流程必需有一个开始步骤");
		} else if (startCount > 1) {
			this.addError("流程只能有一个开始步骤");
		}

		if (endCount < 1) {
			this.addError("流程必需有一个结束步骤");
		} else if (endCount > 1) {
			this.addError("流程只能有一个结束步骤");
		}
	}

	/**
	 * 检查流程的基本信息是否合法
	 * 
	 * @param app
	 * @param msgList
	 */
	private void checkApplication(TblApplication app) {
		// 检查名称
		if (StringUtils.isEmpty(app.getAppName())) {
			this.addError("必需给工作流程指定名称");
		}

		// 在监控授权规则为只允许自定义的情况下，必需指定监控授权人员或者角色
		if (app.getAppMonitorRule().intValue() == TblApplication.MONITOR_RULE_CUSTOMIZE) {
			if (app.getMonitorAccesses() == null
					|| app.getMonitorAccesses().size() == 0) {
				this.addWarn("流程指定了“只允许自定义”的监控授权模式，但是流程并未指定任何监控授权角色或者员工");
			}
		}
	}

	/**
	 * 检查路径是否合法 如果一条路径的开始步骤和结束步骤分别对应流程的开始步骤和结束步骤，则不合法
	 * 
	 * @param route
	 * @param msgList
	 */
	private void checkRoute(TblAppRoute route) {
		boolean isStartProcess = false;// 该路径是否指向流程的开始步骤

		// 检查是否有名称
		if (StringUtils.isEmpty(route.getRouteName()))
			this.addWarn("没有为路径指定名称");
		// 检查是否有开始步骤和结束步骤
		if (route.getBeginProcess() == null) {
			this.addError("路径[" + route.getRouteName() + "]缺少开始步骤");
		} else {
			// 路径的开始步骤的出方向路径数量加1
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
			this.addError("路径[" + route.getRouteName() + "]缺少结束步骤");
		} else {
			// 路径的结束步骤的入方向路径数量加1
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
				this.addWarn("流程的开始步骤直接指向到流程的结束步骤，该流程可能不正确");
			}
		}
	}

	/**
	 * 检查步骤是否合法
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkProcess(TblProcess process) {

		// 如果是结束步骤，不需要做以下检查
		if (process.getProcessType().intValue() != TblProcess.TYPE_END) {
			// 步骤名称
			if (StringUtils.isEmpty(process.getProcessName())) {
				this.addError("步骤缺少名称");
			}
			// 任务分解规则
			if (process.getProcessJobSplitRule().intValue() == TblProcess.SPLIT_RULE_CUSTOMIZE
					&& StringUtils.isEmpty(process
							.getProcessJobSplitCustomize())) {
				this.addError("步骤[" + process.getProcessName()
						+ "]指定了任务分解规则为“自定义规则”，但是没有指定规则");
			}
			if (process.getProcessJobSplitRule().intValue() == TblProcess.SPLIT_RULE_DEFAULT
					&& (process.getProcessAccesses() == null || process
							.getProcessAccesses().size() == 0)) {
				// 授权检查，只要求在指定默认任务分解规则时必需
				this.addError("步骤 [" + process.getProcessName()
						+ "] 使用默认任务分解规则，要求必需指定授权角色或者员工");
			}
			// 完成检查规则
			if (process.getProcessCompleteCheckRule().intValue() == TblProcess.COMPLETE_RULE_WITHCHECK
					&& StringUtils.isEmpty(process
							.getProcessCompleteRuleCustomize())) {
				this.addError("步骤[" + process.getProcessName()
						+ "]指定了完成规则为“需要检查”，但是没有指定规则");
			}
		}

		// 检查步骤的路径信息
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
	 * 检查各个步骤的路径是否正确 1、开始步骤有且只能有一个出的路径 2、结束步骤可以有一个或者多个入的路径 3、其它步骤至少有一个入和出的路径
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

		// 针对不同的步骤类型进行检查
		switch (process.getProcessType().intValue()) {
		case TblProcess.TYPE_APPROVAL:
		case TblProcess.TYPE_HANDLE: {
			if (inCount <= 0)
				this.addError("步骤[" + process.getProcessName() + "]缺少入方向的路径");
			if (outCount <= 0)
				this.addError("步骤[" + process.getProcessName() + "]缺少出方向的路径");
			break;
		}
		case TblProcess.TYPE_START: {
			if (inCount > 0)
				this.addError("步骤[" + process.getProcessName()
						+ "]为流程开始步骤，不允许存在入方向的路径");
			if (outCount <= 0)
				this.addError("步骤[" + process.getProcessName() + "]缺少出方向的路径");
			// if (outCount > 1)
			// this.addError("步骤["
			// + process.getProcessName()
			// + "]为流程开始步骤，只能有一个出方向的路径");
			break;
		}
		case TblProcess.TYPE_END: {
			if (inCount <= 0)
				this.addError("步骤[" + process.getProcessName() + "]缺少入方向的路径");
			if (outCount > 0)
				this.addError("步骤[" + process.getProcessName()
						+ "]为流程结束步骤，不允许有出方向的路径");
		}
		}
	}

	/**
	 * 检查开始类的步骤是否合法
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkStartProcess(TblProcess process) {
		// 必需设置启动页面
		if (StringUtils.isEmpty(process.getHandleURL())) {
			this.addError("必需为开始步骤 [" + process.getProcessName() + "] 指定 启动页面");
		}
		// 必需指定授权员工或者角色，否则流程无法被启动
		if (process.getProcessAccesses() == null
				|| process.getProcessAccesses().size() == 0) {
			this.addError("必需为开始步骤 [" + process.getProcessName()
					+ "] 指定授权角色或者员工");
		}
	}

	/**
	 * 检查审批类的步骤是否合法
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkApprovalProcess(TblProcess process) {
		//
	}

	/**
	 * 检查操作类的步骤是否合法
	 * 
	 * @param process
	 * @param msgList
	 */
	private void checkHandleProcess(TblProcess process) {
		// 必需设置处理页面
		if (StringUtils.isEmpty(process.getHandleURL())) {
			this.addError("必需为操作步骤 [" + process.getProcessName() + "] 指定 处理页面");
		}
		// 必需指定查看页面
		if (StringUtils.isEmpty(process.getViewURL())) {
			this.addError("必需为操作步骤 [" + process.getProcessName() + "] 指定 查看页面");
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
