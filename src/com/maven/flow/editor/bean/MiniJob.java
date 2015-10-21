package com.maven.flow.editor.bean;

import java.io.Serializable;
import java.util.Date;

public class MiniJob implements Serializable {

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

    private long miniJobId; //步骤实例个体操作编号	MiniJobID		int	FALSE	TRUE	FALSE	TRUE

    private long jobId; //所属步骤实例	JobID		int	FALSE	FALSE	FALSE	TRUE

    private long miniJobUser; //操作人	MiniJobUser		int	FALSE	FALSE	FALSE	TRUE

    private Date beginTime; //开始时间	BeginTime		datetime	FALSE	FALSE	FALSE	TRUE

    private Date endTime; //结束时间	EndTime		datetime	FALSE	FALSE	FALSE	FALSE

    private int miniJobStatus; //状态	MiniJobStatus	0:处理中,1:已完成,2:异常	int	FALSE	FALSE	FALSE	TRUE

    //以下为扩展属性
    private String miniJobName = ""; //任务名称，对应步骤的名称

    private String miniJobUserName = ""; //步骤员工名称

    private int projectPayNoticePrintStatus; //打印收费通知单状态

    private Date projectPlanEndTime; //项目计划完成时间

    private String projectBusinessName; //项目业务类型

    private String projectName; //项目名称

    private String projectNo; //项目编号

    private int projectStatus; //项目状态

    private Date projectDelegateTime; //项目委托日期

    private int payNoticePrintStatus; //请款状态

    private int invocieStatus; //项目开票状态

    private int payStatus; //项目收款状态

    private String multiJobHandle;
    private long projectID;//项目ID
    private String delegateCompany;//项目委托单位

    private String reSend; // 可以再次选择接收人

    /**
     * @return 返回 beginTime。
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime 要设置的 beginTime。
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
     * @param endTime 要设置的 endTime。
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
     * @param jobId 要设置的 jobId。
     */
    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    /**
     * @return 返回 miniJobId。
     */
    public long getMiniJobId() {
        return miniJobId;
    }

    /**
     * @param miniJobId 要设置的 miniJobId。
     */
    public void setMiniJobId(long miniJobId) {
        this.miniJobId = miniJobId;
    }

    /**
     * @return 返回 miniJobStatus。
     */
    public int getMiniJobStatus() {
        return miniJobStatus;
    }

    /**
     * @param miniJobStatus 要设置的 miniJobStatus。
     */
    public void setMiniJobStatus(int miniJobStatus) {
        this.miniJobStatus = miniJobStatus;
    }

    /**
     * @return 返回 miniJobUser。
     */
    public long getMiniJobUser() {
        return miniJobUser;
    }

    /**
     * @param miniJobUser 要设置的 miniJobUser。
     */
    public void setMiniJobUser(long miniJobUser) {
        this.miniJobUser = miniJobUser;
    }

    /**
     * @return 返回 miniJobName。
     */
    public String getMiniJobName() {
        return miniJobName;
    }

    /**
     * @param miniJobName 要设置的 miniJobName。
     */
    public void setMiniJobName(String miniJobName) {
        this.miniJobName = miniJobName;
    }

    /**
     * @return 返回 miniJobUserName。
     */
    public String getMiniJobUserName() {
        return miniJobUserName;
    }

    /**
     * @param miniJobUserName 要设置的 miniJobUserName。
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
