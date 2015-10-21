package com.maven.flow.instance.hibernate.dao;

import java.util.Date;


/**
 * TblTaskBase generated by MyEclipse - Hibernate Tools
 */

public class TblTaskBase  implements java.io.Serializable {
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

    // Fields    

     private Long fsn;
     private Integer fappId;
     private String ftaskInitiator;
     private String ftaskInitiatorName;
     private Date ftaskStartTime;
     private Date ftaskEndTime;
     private Integer ftaskStatus;
     private Integer ftaskDeleteFlag;


    // Constructors

    /** default constructor */
    public TblTaskBase() {
    }

	/** minimal constructor */
    public TblTaskBase(Long fsn) {
        this.fsn = fsn;
    }
    
    /** full constructor */
    public TblTaskBase(Long fsn, Integer fappId, String ftaskInitiator, String ftaskInitiatorName, Date ftaskStartTime, Date ftaskEndTime, Integer ftaskStatus, Integer ftaskDeleteFlag) {
        this.fsn = fsn;
        this.fappId = fappId;
        this.ftaskInitiator = ftaskInitiator;
        this.ftaskInitiatorName = ftaskInitiatorName;
        this.ftaskStartTime = ftaskStartTime;
        this.ftaskEndTime = ftaskEndTime;
        this.ftaskStatus = ftaskStatus;
        this.ftaskDeleteFlag = ftaskDeleteFlag;
    }

   
    // Property accessors

    public Long getFsn() {
        return this.fsn;
    }
    
    public void setFsn(Long fsn) {
        this.fsn = fsn;
    }

    public Integer getFappId() {
        return this.fappId;
    }
    
    public void setFappId(Integer fappId) {
        this.fappId = fappId;
    }

    public String getFtaskInitiator() {
        return this.ftaskInitiator;
    }
    
    public void setFtaskInitiator(String ftaskInitiator) {
        this.ftaskInitiator = ftaskInitiator;
    }

    public String getFtaskInitiatorName() {
        return this.ftaskInitiatorName;
    }
    
    public void setFtaskInitiatorName(String ftaskInitiatorName) {
        this.ftaskInitiatorName = ftaskInitiatorName;
    }

    public Date getFtaskStartTime() {
        return this.ftaskStartTime;
    }
    
    public void setFtaskStartTime(Date ftaskStartTime) {
        this.ftaskStartTime = ftaskStartTime;
    }

    public Date getFtaskEndTime() {
        return this.ftaskEndTime;
    }
    
    public void setFtaskEndTime(Date ftaskEndTime) {
        this.ftaskEndTime = ftaskEndTime;
    }

    public Integer getFtaskStatus() {
        return this.ftaskStatus;
    }
    
    public void setFtaskStatus(Integer ftaskStatus) {
        this.ftaskStatus = ftaskStatus;
    }

    public Integer getFtaskDeleteFlag() {
        return this.ftaskDeleteFlag;
    }
    
    public void setFtaskDeleteFlag(Integer ftaskDeleteFlag) {
        this.ftaskDeleteFlag = ftaskDeleteFlag;
    }
   








}