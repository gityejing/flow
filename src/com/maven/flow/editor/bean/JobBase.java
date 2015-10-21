package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

public class JobBase implements Serializable {

	/**
	 * 状态：处理中
	 */
	public static final int STATUS_HANDLING = 0;

	/**
	 * 状态：已完成
	 */
	public static final int STATUS_COMPLETE = 1;

	/**
	 * 状态：异常
	 */
	public static final int STATUS_EXCEPTION = 2;

	private long jobId;// 流程步骤实例编号 JobID int FALSE TRUE FALSE TRUE

	private Date beginTime;// 开始时间 BeginTime datetime FALSE FALSE FALSE TRUE

	private Date endTime;// 结束时间 EndTime datetime FALSE FALSE FALSE FALSE

	private int jobStatus;// 状态 JobStatus 0:处理中,1:已完成,2:异常 int FALSE FALSE

	// FALSE TRUE

	private long taskId;// 所属流程实例编号 TaskID int FALSE FALSE FALSE TRUE

	private long processId;// 所属流程步骤编号 ProcessID int FALSE FALSE FALSE TRUE

	private long appId;// 所属流程编号 AppID int FALSE FALSE FALSE TRUE

	private Date orderTime;// 应当完成时间 OrderTime datetime FALSE FALSE FALSE FALSE

	// 以下为扩展属性
	private String processName = "";// 对应步骤的名称

	private long preJobBaseId;// 上一级的JobBase的编号.

	private long subProjectId;// 对应的单位工程的编号.

	private long projectFolderId;// 单位工程[需要被拆分成单项工程]. 对应的目录编号

	private int step;// 子流程的级数;

	private String key;//

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public long getProjectFolderId() {
		return projectFolderId;
	}

	public void setProjectFolderId(long projectFolderId) {
		this.projectFolderId = projectFolderId;
	}

	public long getPreJobBaseId() {
		return preJobBaseId;
	}

	public void setPreJobBaseId(long preJobBaseId) {
		this.preJobBaseId = preJobBaseId;
	}

	public long getSubProjectId() {
		return subProjectId;
	}

	public void setSubProjectId(long subProjectId) {
		this.subProjectId = subProjectId;
	}

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
	 * @return 返回 beginTime。
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            要设置的 beginTime。
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return 返回 endTime。
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            要设置的 endTime。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 返回 jobId。
	 */
	public long getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            要设置的 jobId。
	 */
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return 返回 jobStatus。
	 */
	public int getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            要设置的 jobStatus。
	 */
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return 返回 orderTime。
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            要设置的 orderTime。
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return 返回 processId。
	 */
	public long getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            要设置的 processId。
	 */
	public void setProcessId(long processId) {
		this.processId = processId;
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
	 * @return 返回 processName。
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName
	 *            要设置的 processName。
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
