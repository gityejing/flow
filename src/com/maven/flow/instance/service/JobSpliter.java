/**
 * @(#)JobSpliter.java 2007-6-13
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.instance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.maven.flow.core.ICustomizeJobSpliter;
import com.maven.flow.hibernate.dao.TblEmployeeInfo;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessPeople;
import com.maven.flow.hibernate.dao.TblProcessPeopleDAO;
import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblMiniJob;
import com.maven.flow.service.EmployeeInfoService;


/**
 * 处理步骤任务分解的相关功能
 *
 * @author      kinz
 * @version     1.0 2007-6-13
 * @since       JDK1.5
 */

public class JobSpliter {


    protected Log log = LogFactory.getLog(this.getClass());
    
    public JobSpliter(){
      
    }
    
    
    /**
     * 将一个JobBase分解成一个或者多个MiniJob
     * @param process
     * @param job
     * @return
     * @throws Exception
     */
    public TblMiniJob[] splitJob(TblProcess process, TblJobBase job) throws Exception {
        if (process.getProcessJobSplitRule() == TblProcess.SPLIT_RULE_DEFAULT) {
            log.debug("任务分解规则类型： [默认规则]");
            return this.splitJobByDefault(process, job);
        } else {
            log.debug("任务分解规则类型： [自定义规则]");
            ICustomizeJobSpliter spliter = this.getCustomizeJobSpliter(process);
            if (spliter == null) {
                throw new Exception("未能获取自定义规则的任务分解器");
            } else {
                log.debug("自定义任务分解规则处理器：[处理器名称："+spliter.getSpliterName()+"，实现类名："+spliter.getClass().getName()+"]");
                //return spliter.splitJob(process, job);
                
                return spliter.splitJob(process, job);
            }
        }
    }
    


    /**
     * 使用默认规则分解任务
     * 逻辑：根据步骤权限，给每个有权限的人员生成一个MiniJob
     * @param process
     * @param job
     * @return
     * @throws Exception
     */
    private TblMiniJob[] splitJobByDefault(TblProcess process, TblJobBase job)
            throws Exception {
    	/*
        //获取步骤的权限信息
        ProcessAccessLogic pal = new ProcessAccessLogic();
        List accesses = pal.listProcessAccessByProcessId(process.getProcessId());
        //如果没有权限信息，抛出异常
        if (accesses == null || accesses.size() == 0)
            throw new Exception("步骤不存在任何权限信息，无法分解任务");

        //MiniJob缓存
        List tmpMiniJobs = new ArrayList();
        //缓存已经分配的人员
        List userCache = new ArrayList();
        
        //为每个角色以及员工分配MiniJob
        for (Iterator it = accesses.iterator(); it.hasNext();) {
            ProcessAccess pa = (ProcessAccess) it.next();

            long[] userIds = null;
            if (pa.getRoleType() == ProcessAccess.ROLE_TYPE_ROLE) {
                //角色类型的授权，需要获取角色下所有的员工信息
                userIds = engine.getRoleUserIds(pa.getRoleId());
            } else {
                //员工授权
                userIds = new long[] { pa.getRoleId() };
            }

            for (int i = 0; i < userIds.length; i++) {
            	if(userCache.contains(new Long(userIds[i])))
            		continue;
            	
                TblMiniJob mj = new TblMiniJob();
                mj.setMiniJobStatus(TblMiniJob.STATUS_HANDLING);
                mj.setMiniJobUser(userIds[i]);
                mj.setJobId(job.getJobId());
                mj.setBeginTime(new Date());

                tmpMiniJobs.add(mj);
                
                userCache.add(new Long(userIds[i]));
            }
        }
        return (TblMiniJob[]) tmpMiniJobs.toArray(new TblMiniJob[tmpMiniJobs.size()]);
        */
    	TblProcessPeopleDAO p=new TblProcessPeopleDAO();
    	EmployeeInfoService service = new EmployeeInfoService();
    	
    	List miniJobs=new ArrayList();
    	HashMap pm=new HashMap();
    	List peoples=p.findByProperty("processId", process.getProcessId());
    	if(peoples!=null && peoples.size()>0){
    		for(int i=0;i<peoples.size();i++){
    			
    			TblProcessPeople pp=(TblProcessPeople)peoples.get(i);
				if(pp.getRealFsn()==null || pp.getRealFsn().equals(""))
				{
					continue;
				}
				
    			if(pp.getPeopleType()==1){//role
    				List users=service.findByProperty("froleSn",new Long(pp.getRealFsn()));
    				for(int j=0;j<users.size();j++){
    					TblEmployeeInfo ei=(TblEmployeeInfo)users.get(j);
        				if(pm.containsKey(ei.getFemployeeSn())){
        					continue;
        				}
        				pm.put(ei.getFemployeeSn(), "");
        				
        				TblMiniJob miniJob = new TblMiniJob();
        				//miniJob.setFsn(new Long(idService.getMiniJobId()));
        				miniJob.setFjobId(job.getFsn());
        				miniJob.setFisFromHuiQian(0);
        				miniJob.setFbeginTime(new java.util.Date());
        				miniJob.setFisAsynchronism(0);
        				miniJob.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
        				miniJob.setFminiJobUserId(new Integer(ei.getFemployeeSn()+""));
        				miniJob.setFminiJobUserName("");
        				miniJob.setFsubmitOption("");
        				
                        miniJobs.add(miniJob);
    					
    				}
    			}else if(pp.getPeopleType()==2){//人员
    				TblEmployeeInfo ei=service.findById(new Long(pp.getRealFsn()));
    				if(pm.containsKey(ei.getFemployeeSn())){
    					continue;
    				}
    				pm.put(ei.getFemployeeSn(), "");
    				
    				TblMiniJob miniJob = new TblMiniJob();
    				//miniJob.setFsn(new Long(idService.getMiniJobId()));
    				miniJob.setFjobId(job.getFsn());
    				miniJob.setFisFromHuiQian(0);
    				miniJob.setFbeginTime(new java.util.Date());
    				miniJob.setFisAsynchronism(0);
    				miniJob.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
    				miniJob.setFminiJobUserId(new Integer(ei.getFemployeeSn()+""));
    				miniJob.setFminiJobUserName("");
    				miniJob.setFsubmitOption("");
    				
                    miniJobs.add(miniJob);
    				
    			}else if(pp.getPeopleType()==3){//Project
    				
    				Long taskId=job.getFtaskId();
    				
    			}
    		}
    		return (TblMiniJob[]) miniJobs.toArray(new TblMiniJob[miniJobs.size()]);
    	}
    	
    	
    	return null;
    }

    /**
     * 获取自定义规则的任务分解器
     * @return
     */
    private ICustomizeJobSpliter getCustomizeJobSpliter(TblProcess process) throws Exception{
        String clz = process.getProcessJobSplitCustomize();
        Object obj = null;
        try{
            Class clzz = Class.forName(clz);
            obj = clzz.newInstance();
        }catch(Exception ex){
            throw new Exception("无法获取自定义任务分解规则处理实现");
        }
        
        if(obj instanceof ICustomizeJobSpliter){
            return (ICustomizeJobSpliter) obj;
        }else{
            throw new Exception("步骤指定的任务分解规则处理实现必需实现接口 ["+ICustomizeJobSpliter.class.getName()+"]");
        }
    }
}