package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装流程实例信息
 * 
 * @author kinz
 * @version 1.0 2007-6-12
 * @since JDK1.5
 */

public class TaskBase implements Serializable {

	/**
	 * 运行状态：运行中
	 */
	public static final int STATUS_RUNNING = 0;

	/**
	 * 运行状态：已结束
	 */
	public static final int STATUS_END = 1;

	/**
	 * 运行状态：暂停
	 */
	public static final int STATUS_PAUSE = 2;

	/**
	 * 运行状态：异常
	 */
	public static final int STATUS_EXCEPTION = 3;

	/**
	 * 删除标记：正常
	 */
	public static final int FLAG_NORMAL = 0;

	/**
	 * 删除标记：删除
	 */
	public static final int FLAG_DELETE = 1;

	private long taskId;// 流程实例编号 TaskID int FALSE TRUE FALSE TRUE

	private long appId;// 所属流程编号 AppID int FALSE FALSE FALSE TRUE

	private long taskInitiator;// 流程实例发起人 TaskInitiator int FALSE FALSE FALSE
								// TRUE

	private Date taskStartTime;// 流程实例发起时间 TaskStartTime datetime FALSE FALSE
								// FALSE TRUE

	private Date taskEndTime;// 流程实例结束时间 TaskEndTime datetime FALSE FALSE
								// FALSE FALSE

	private int taskStatus;// 流程实例运行状态 TaskStatus 0:运行中,1:已结束,2:暂停,3:异常 int
							// FALSE FALSE FALSE TRUE

	private int taskDeleteFlag;// 流程实例删除标记位 TaskDeleteFlag 0:正常,1:删除 int FALSE
								// FALSE FALSE TRUE

	// 以下是扩展属性
	private String taskName;// 流程实例的名称，实际上是对应项目的名称

	private String taskInitiatorName;// 项目发起人名称

	private String appName;// 流程的名称

	/**
	 * @return 返回 appId。
	 */
	public long getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            要设置的 appId。
	 */
	public void setAppId(long appId) {
		this.appId = appId;
	}

	/**
	 * @return 返回 taskDeleteFlag。
	 */
	public int getTaskDeleteFlag() {
		return taskDeleteFlag;
	}

	/**
	 * @param taskDeleteFlag
	 *            要设置的 taskDeleteFlag。
	 */
	public void setTaskDeleteFlag(int taskDeleteFlag) {
		this.taskDeleteFlag = taskDeleteFlag;
	}

	/**
	 * @return 返回 taskEndTime。
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}

	/**
	 * @param taskEndTime
	 *            要设置的 taskEndTime。
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	/**
	 * @return 返回 taskId。
	 */
	public long getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            要设置的 taskId。
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return 返回 taskInitiator。
	 */
	public long getTaskInitiator() {
		return taskInitiator;
	}

	/**
	 * @param taskInitiator
	 *            要设置的 taskInitiator。
	 */
	public void setTaskInitiator(long taskInitiator) {
		this.taskInitiator = taskInitiator;
	}

	/**
	 * @return 返回 taskStartTime。
	 */
	public Date getTaskStartTime() {
		return taskStartTime;
	}

	/**
	 * @param taskStartTime
	 *            要设置的 taskStartTime。
	 */
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	/**
	 * @return 返回 taskStatus。
	 */
	public int getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus
	 *            要设置的 taskStatus。
	 */
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskInitiatorName(String taskInitiatorName) {
		this.taskInitiatorName = taskInitiatorName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTaskInitiatorName() {
		return this.taskInitiatorName;
	}

	public String getAppName() {
		return appName;
	}
}
