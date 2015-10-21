package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

public class JobBase implements Serializable {

	/**
	 * ״̬��������
	 */
	public static final int STATUS_HANDLING = 0;

	/**
	 * ״̬�������
	 */
	public static final int STATUS_COMPLETE = 1;

	/**
	 * ״̬���쳣
	 */
	public static final int STATUS_EXCEPTION = 2;

	private long jobId;// ���̲���ʵ����� JobID int FALSE TRUE FALSE TRUE

	private Date beginTime;// ��ʼʱ�� BeginTime datetime FALSE FALSE FALSE TRUE

	private Date endTime;// ����ʱ�� EndTime datetime FALSE FALSE FALSE FALSE

	private int jobStatus;// ״̬ JobStatus 0:������,1:�����,2:�쳣 int FALSE FALSE

	// FALSE TRUE

	private long taskId;// ��������ʵ����� TaskID int FALSE FALSE FALSE TRUE

	private long processId;// �������̲����� ProcessID int FALSE FALSE FALSE TRUE

	private long appId;// �������̱�� AppID int FALSE FALSE FALSE TRUE

	private Date orderTime;// Ӧ�����ʱ�� OrderTime datetime FALSE FALSE FALSE FALSE

	// ����Ϊ��չ����
	private String processName = "";// ��Ӧ���������

	private long preJobBaseId;// ��һ����JobBase�ı��.

	private long subProjectId;// ��Ӧ�ĵ�λ���̵ı��.

	private long projectFolderId;// ��λ����[��Ҫ����ֳɵ����]. ��Ӧ��Ŀ¼���

	private int step;// �����̵ļ���;

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
	 * @return ���� appId��
	 */
	public long getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            Ҫ���õ� appId��
	 */
	public void setAppId(long appId) {
		this.appId = appId;
	}

	/**
	 * @return ���� beginTime��
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            Ҫ���õ� beginTime��
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return ���� endTime��
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            Ҫ���õ� endTime��
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return ���� jobId��
	 */
	public long getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 *            Ҫ���õ� jobId��
	 */
	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return ���� jobStatus��
	 */
	public int getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            Ҫ���õ� jobStatus��
	 */
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return ���� orderTime��
	 */
	public Date getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            Ҫ���õ� orderTime��
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * @return ���� processId��
	 */
	public long getProcessId() {
		return processId;
	}

	/**
	 * @param processId
	 *            Ҫ���õ� processId��
	 */
	public void setProcessId(long processId) {
		this.processId = processId;
	}

	/**
	 * @return ���� taskId��
	 */
	public long getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            Ҫ���õ� taskId��
	 */
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return ���� processName��
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @param processName
	 *            Ҫ���õ� processName��
	 */
	public void setProcessName(String processName) {
		this.processName = processName;
	}

}
