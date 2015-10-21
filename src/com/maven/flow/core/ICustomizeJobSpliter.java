package com.maven.flow.core;

import com.maven.flow.editor.bean.JobBase;
import com.maven.flow.editor.bean.MiniJob;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblMiniJob;


public interface ICustomizeJobSpliter extends IBaseLogic{

    /**
     * 使用自定义规则对任务进行分解
     * @param process 流程步骤
     * @param task 步骤实例
     * @return 一系列MiniJob对象的数组
     * @throws Exception
     */
    TblMiniJob[] splitJob(TblProcess process, TblJobBase jobBase) throws Exception;
    
    
    public int findNextProcessPeopleCount(TblProcess process, TblJobBase jobBase) throws Exception;
    
    /**
     * 获取任务分解规则名称
     */
    String getSpliterName();

    /**
     * 自定义增加用户的JOB
     * @param users String[] 用户ID数组
     */
    public void setEmployees(String users[]);
    
    
    TblMiniJob[] splitJobUseObject(TblProcess process, TblJobBase jobBase,Object o) throws Exception;
    
}
