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
 * ����������ֽ����ع���
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
     * ��һ��JobBase�ֽ��һ�����߶��MiniJob
     * @param process
     * @param job
     * @return
     * @throws Exception
     */
    public TblMiniJob[] splitJob(TblProcess process, TblJobBase job) throws Exception {
        if (process.getProcessJobSplitRule() == TblProcess.SPLIT_RULE_DEFAULT) {
            log.debug("����ֽ�������ͣ� [Ĭ�Ϲ���]");
            return this.splitJobByDefault(process, job);
        } else {
            log.debug("����ֽ�������ͣ� [�Զ������]");
            ICustomizeJobSpliter spliter = this.getCustomizeJobSpliter(process);
            if (spliter == null) {
                throw new Exception("δ�ܻ�ȡ�Զ�����������ֽ���");
            } else {
                log.debug("�Զ�������ֽ����������[���������ƣ�"+spliter.getSpliterName()+"��ʵ��������"+spliter.getClass().getName()+"]");
                //return spliter.splitJob(process, job);
                
                return spliter.splitJob(process, job);
            }
        }
    }
    


    /**
     * ʹ��Ĭ�Ϲ���ֽ�����
     * �߼������ݲ���Ȩ�ޣ���ÿ����Ȩ�޵���Ա����һ��MiniJob
     * @param process
     * @param job
     * @return
     * @throws Exception
     */
    private TblMiniJob[] splitJobByDefault(TblProcess process, TblJobBase job)
            throws Exception {
    	/*
        //��ȡ�����Ȩ����Ϣ
        ProcessAccessLogic pal = new ProcessAccessLogic();
        List accesses = pal.listProcessAccessByProcessId(process.getProcessId());
        //���û��Ȩ����Ϣ���׳��쳣
        if (accesses == null || accesses.size() == 0)
            throw new Exception("���費�����κ�Ȩ����Ϣ���޷��ֽ�����");

        //MiniJob����
        List tmpMiniJobs = new ArrayList();
        //�����Ѿ��������Ա
        List userCache = new ArrayList();
        
        //Ϊÿ����ɫ�Լ�Ա������MiniJob
        for (Iterator it = accesses.iterator(); it.hasNext();) {
            ProcessAccess pa = (ProcessAccess) it.next();

            long[] userIds = null;
            if (pa.getRoleType() == ProcessAccess.ROLE_TYPE_ROLE) {
                //��ɫ���͵���Ȩ����Ҫ��ȡ��ɫ�����е�Ա����Ϣ
                userIds = engine.getRoleUserIds(pa.getRoleId());
            } else {
                //Ա����Ȩ
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
    			}else if(pp.getPeopleType()==2){//��Ա
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
     * ��ȡ�Զ�����������ֽ���
     * @return
     */
    private ICustomizeJobSpliter getCustomizeJobSpliter(TblProcess process) throws Exception{
        String clz = process.getProcessJobSplitCustomize();
        Object obj = null;
        try{
            Class clzz = Class.forName(clz);
            obj = clzz.newInstance();
        }catch(Exception ex){
            throw new Exception("�޷���ȡ�Զ�������ֽ������ʵ��");
        }
        
        if(obj instanceof ICustomizeJobSpliter){
            return (ICustomizeJobSpliter) obj;
        }else{
            throw new Exception("����ָ��������ֽ������ʵ�ֱ���ʵ�ֽӿ� ["+ICustomizeJobSpliter.class.getName()+"]");
        }
    }
}