package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * ��װ����ʵ����Ϣ
 * 
 * @author kinz
 * @version 1.0 2007-6-12
 * @since JDK1.5
 */

public class TaskBase implements Serializable {

	/**
	 * ����״̬��������
	 */
	public static final int STATUS_RUNNING = 0;

	/**
	 * ����״̬���ѽ���
	 */
	public static final int STATUS_END = 1;

	/**
	 * ����״̬����ͣ
	 */
	public static final int STATUS_PAUSE = 2;

	/**
	 * ����״̬���쳣
	 */
	public static final int STATUS_EXCEPTION = 3;

	/**
	 * ɾ����ǣ�����
	 */
	public static final int FLAG_NORMAL = 0;

	/**
	 * ɾ����ǣ�ɾ��
	 */
	public static final int FLAG_DELETE = 1;

	private long taskId;// ����ʵ����� TaskID int FALSE TRUE FALSE TRUE

	private long appId;// �������̱�� AppID int FALSE FALSE FALSE TRUE

	private long taskInitiator;// ����ʵ�������� TaskInitiator int FALSE FALSE FALSE
								// TRUE

	private Date taskStartTime;// ����ʵ������ʱ�� TaskStartTime datetime FALSE FALSE
								// FALSE TRUE

	private Date taskEndTime;// ����ʵ������ʱ�� TaskEndTime datetime FALSE FALSE
								// FALSE FALSE

	private int taskStatus;// ����ʵ������״̬ TaskStatus 0:������,1:�ѽ���,2:��ͣ,3:�쳣 int
							// FALSE FALSE FALSE TRUE

	private int taskDeleteFlag;// ����ʵ��ɾ�����λ TaskDeleteFlag 0:����,1:ɾ�� int FALSE
								// FALSE FALSE TRUE

	// ��������չ����
	private String taskName;// ����ʵ�������ƣ�ʵ�����Ƕ�Ӧ��Ŀ������

	private String taskInitiatorName;// ��Ŀ����������

	private String appName;// ���̵�����

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
	 * @return ���� taskDeleteFlag��
	 */
	public int getTaskDeleteFlag() {
		return taskDeleteFlag;
	}

	/**
	 * @param taskDeleteFlag
	 *            Ҫ���õ� taskDeleteFlag��
	 */
	public void setTaskDeleteFlag(int taskDeleteFlag) {
		this.taskDeleteFlag = taskDeleteFlag;
	}

	/**
	 * @return ���� taskEndTime��
	 */
	public Date getTaskEndTime() {
		return taskEndTime;
	}

	/**
	 * @param taskEndTime
	 *            Ҫ���õ� taskEndTime��
	 */
	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
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
	 * @return ���� taskInitiator��
	 */
	public long getTaskInitiator() {
		return taskInitiator;
	}

	/**
	 * @param taskInitiator
	 *            Ҫ���õ� taskInitiator��
	 */
	public void setTaskInitiator(long taskInitiator) {
		this.taskInitiator = taskInitiator;
	}

	/**
	 * @return ���� taskStartTime��
	 */
	public Date getTaskStartTime() {
		return taskStartTime;
	}

	/**
	 * @param taskStartTime
	 *            Ҫ���õ� taskStartTime��
	 */
	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	/**
	 * @return ���� taskStatus��
	 */
	public int getTaskStatus() {
		return taskStatus;
	}

	/**
	 * @param taskStatus
	 *            Ҫ���õ� taskStatus��
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
