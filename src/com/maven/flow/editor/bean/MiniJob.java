package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

public class MiniJob implements Serializable {

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

    private long miniJobId; //����ʵ������������	MiniJobID		int	FALSE	TRUE	FALSE	TRUE

    private long jobId; //��������ʵ��	JobID		int	FALSE	FALSE	FALSE	TRUE

    private long miniJobUser; //������	MiniJobUser		int	FALSE	FALSE	FALSE	TRUE

    private Date beginTime; //��ʼʱ��	BeginTime		datetime	FALSE	FALSE	FALSE	TRUE

    private Date endTime; //����ʱ��	EndTime		datetime	FALSE	FALSE	FALSE	FALSE

    private int miniJobStatus; //״̬	MiniJobStatus	0:������,1:�����,2:�쳣	int	FALSE	FALSE	FALSE	TRUE

    //����Ϊ��չ����
    private String miniJobName = ""; //�������ƣ���Ӧ���������

    private String miniJobUserName = ""; //����Ա������

    private int projectPayNoticePrintStatus; //��ӡ�շ�֪ͨ��״̬

    private Date projectPlanEndTime; //��Ŀ�ƻ����ʱ��

    private String projectBusinessName; //��Ŀҵ������

    private String projectName; //��Ŀ����

    private String projectNo; //��Ŀ���

    private int projectStatus; //��Ŀ״̬

    private Date projectDelegateTime; //��Ŀί������

    private int payNoticePrintStatus; //���״̬

    private int invocieStatus; //��Ŀ��Ʊ״̬

    private int payStatus; //��Ŀ�տ�״̬

    private String multiJobHandle;
    private long projectID;//��ĿID
    private String delegateCompany;//��Ŀί�е�λ

    private String reSend; // �����ٴ�ѡ�������

    /**
     * @return ���� beginTime��
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime Ҫ���õ� beginTime��
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
     * @param endTime Ҫ���õ� endTime��
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
     * @param jobId Ҫ���õ� jobId��
     */
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    /**
     * @return ���� miniJobId��
     */
    public long getMiniJobId() {
        return miniJobId;
    }

    /**
     * @param miniJobId Ҫ���õ� miniJobId��
     */
    public void setMiniJobId(long miniJobId) {
        this.miniJobId = miniJobId;
    }

    /**
     * @return ���� miniJobStatus��
     */
    public int getMiniJobStatus() {
        return miniJobStatus;
    }

    /**
     * @param miniJobStatus Ҫ���õ� miniJobStatus��
     */
    public void setMiniJobStatus(int miniJobStatus) {
        this.miniJobStatus = miniJobStatus;
    }

    /**
     * @return ���� miniJobUser��
     */
    public long getMiniJobUser() {
        return miniJobUser;
    }

    /**
     * @param miniJobUser Ҫ���õ� miniJobUser��
     */
    public void setMiniJobUser(long miniJobUser) {
        this.miniJobUser = miniJobUser;
    }

    /**
     * @return ���� miniJobName��
     */
    public String getMiniJobName() {
        return miniJobName;
    }

    /**
     * @param miniJobName Ҫ���õ� miniJobName��
     */
    public void setMiniJobName(String miniJobName) {
        this.miniJobName = miniJobName;
    }

    /**
     * @return ���� miniJobUserName��
     */
    public String getMiniJobUserName() {
        return miniJobUserName;
    }

    /**
     * @param miniJobUserName Ҫ���õ� miniJobUserName��
     */
    public void setMiniJobUserName(String miniJobUserName) {
        this.miniJobUserName = miniJobUserName;
    }

    public String getProjectBusinessName() {
        return projectBusinessName;
    }

    public void setProjectBusinessName(String projectBusinessName) {
        this.projectBusinessName = projectBusinessName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public int getProjectPayNoticePrintStatus() {
        return projectPayNoticePrintStatus;
    }

    public void setProjectPayNoticePrintStatus(int projectPayNoticeStatus) {
        this.projectPayNoticePrintStatus = projectPayNoticeStatus;
    }

    public Date getProjectPlanEndTime() {
        return projectPlanEndTime;
    }

    public void setProjectPlanEndTime(Date projectPlandEndTime) {
        this.projectPlanEndTime = projectPlandEndTime;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getProjectDelegateTime() {
        return projectDelegateTime;
    }

    public void setProjectDelegateTime(Date projectDelegateTime) {
        this.projectDelegateTime = projectDelegateTime;
    }

    public int getInvocieStatus() {
        return invocieStatus;
    }

    public void setInvocieStatus(int invocieStatus) {
        this.invocieStatus = invocieStatus;
    }

    public int getPayNoticePrintStatus() {
        return payNoticePrintStatus;
    }

    public void setPayNoticePrintStatus(int payNoticePrintStatus) {
        this.payNoticePrintStatus = payNoticePrintStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public String getMultiJobHandle() {
        return multiJobHandle;
    }

    public long getProjectID() {
        return projectID;
    }

    public String getDelegateCompany() {
        return delegateCompany;
    }

    public String getReSend() {
        return reSend;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public void setMultiJobHandle(String multiJobHandle) {
        this.multiJobHandle = multiJobHandle;
    }

    public void setProjectID(long projectID) {
        this.projectID = projectID;
    }

    public void setDelegateCompany(String delegateCompany) {
        this.delegateCompany = delegateCompany;
    }

    public void setReSend(String reSend) {
        this.reSend = reSend;
    }

}
