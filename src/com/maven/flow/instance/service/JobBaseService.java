package com.maven.flow.instance.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Transaction;

import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblJobBaseDAO;
import com.maven.flow.instance.hibernate.dao.TblMiniJob;
import com.maven.flow.instance.hibernate.dao.TblMiniJobDAO;
import com.maven.flow.instance.hibernate.dao.TblTaskBase;
import com.maven.flow.instance.hibernate.dao.TblTaskBaseDAO;
import com.maven.flow.service.IDService;
import com.maven.flow.editor.bean.JobBase;
import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.editor.extend.interfaces.IBeforeHandleClass;
import com.maven.flow.hibernate.dao.*;

public class JobBaseService {

	TblMiniJobDAO miniJobDao = new TblMiniJobDAO();

	TblJobBaseDAO jobBaseDao = new TblJobBaseDAO();

	TblTaskBaseDAO taskBaseDao = new TblTaskBaseDAO();

	TblApplicationDAO appDao = new TblApplicationDAO();

	TblAppRouteDAO routeDao = new TblAppRouteDAO();

	TblEmployeeInfoDAO eiDao = new TblEmployeeInfoDAO();

	TblProcessPeopleDAO proPeoDao = new TblProcessPeopleDAO();

	TblRoleDAO roleDao = new TblRoleDAO();

	TblProcessDAO processDao = new TblProcessDAO();

	IDService idService = new IDService();

	JobSpliter jobSpliter = new JobSpliter();



	public void save(TblJobBase transientInstance) {
		jobBaseDao.save(transientInstance);
	}

	public boolean checkProcessProcess(TblEmployeeInfo ei, int appid) {

		return true;
	}

	public TblTaskBase instanceApplication(int appid, TblEmployeeInfo ei)
			throws Exception {

		TblApplication application = appDao.findById(new Integer(appid + ""));
		if (application == null) {
			throw new Exception("��Ҫ���������̲�����!");
		}

		TblTaskBase tb = new TblTaskBase();
		tb.setFsn(new Long(idService.getTaskBaseId() + ""));
		tb.setFtaskDeleteFlag(TblTaskBase.FLAG_NORMAL);
		tb.setFappId(new Integer(appid));

		tb.setFtaskInitiator(ei.getFemployeeSn() + "");
		tb.setFtaskInitiatorName(ei.getFemployeeName());
		tb.setFtaskStartTime(new Date());
		tb.setFtaskStatus(TblTaskBase.STATUS_RUNNING);

		// ���뵽���ݿ���
		taskBaseDao.save(tb);

		return tb;
	}
	


	public TblJobBase processToJob(TblTaskBase tb, TblProcess process) {
		TblJobBase job = new TblJobBase();
		job.setFsn(new Long(idService.getJobBaseId() + ""));
		job.setFappId(process.getAppId());
		job.setFjobStatus(TblJobBase.STATUS_HANDLING);
		job.setFprocessId(process.getProcessId());
		// job.setBeginTime(new Date());
		job.setFtaskId(tb.getFsn());
		job.setFishidden(0);

		job.setFifOpen(0);
		job.setFprocessId(process.getProcessId());
		job.setForderDate(new java.util.Date());
		job.setForderTime(new java.util.Date());
		
		jobBaseDao.save(job);
		return job;
	}

	public long[] instantiateProcess(TblTaskBase tb, TblProcess process)
			throws Exception {

		TblJobBase jb = processToJob(tb, process);
		TblMiniJob[] miniJobs = jobSpliter.splitJob(process, jb);
		if (miniJobs.length < 1) {
			throw new Exception("��һ������û������");
		}

		for (int i = 0; i < miniJobs.length; i++) {
			TblMiniJob mj = (TblMiniJob) miniJobs[i];
			//mj.setFsn(new Long("" + idService.getMiniJobId()));
			//miniJobDao.save(mj);
			this.createMiniJob(mj);
		}

		return new long[] { jb.getFsn(), -1 };

	}

	public long[] instantiateStartProcess(TblTaskBase tb, TblProcess process,
			TblEmployeeInfo ei) throws Exception {

		TblJobBase jb = processToJob(tb, process);
		TblMiniJob miniJob = new TblMiniJob();
		miniJob.setFsn(new Long(idService.getMiniJobId()));
		miniJob.setFjobId(jb.getFsn());
		miniJob.setFisFromHuiQian(0);
		miniJob.setFbeginTime(new java.util.Date());
		miniJob.setFisAsynchronism(0);
		miniJob.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
		miniJob.setFminiJobUserId(new Integer(ei.getFemployeeSn()+""));
		miniJob.setFminiJobUserName(ei.getFemployeeName());
		miniJob.setFsubmitOption("");

		//miniJob=this.createMiniJob(miniJob);
		miniJobDao.save(miniJob);

		return new long[] { jb.getFsn(), miniJob.getFsn().longValue() };

	}
	
	public static void main(String args[])throws Exception{
		JobBaseService bs=new JobBaseService();
		TblEmployeeInfo ei=new TblEmployeeInfo();
		ei.setFemployeeSn(new Long("2"));
		
		Map map=new HashMap();
		//bs.startWorkFlow(90, ei, false);
		
		
		bs.processMiniJob(18, 1106, ei, map);
	}
	public void parseWorkFlow(long taskId){
		
		TblTaskBase tb=taskBaseDao.findById(taskId);
		tb.setFtaskStatus(TblTaskBase.STATUS_PAUSE);
		taskBaseDao.update(tb);
		
	}

	public void normalWorkFlow(long taskId){
		
		TblTaskBase tb=taskBaseDao.findById(taskId);
		tb.setFtaskStatus(TblTaskBase.FLAG_NORMAL);
		taskBaseDao.update(tb);
		
	}
	
	public long[] startWorkFlow(int appid, TblEmployeeInfo ei,
			boolean checkAccess) throws Exception {
		long[] ids = { -1, -1, -1 };
		if (checkAccess) {
			if (!checkProcessProcess(ei, appid)) {
				throw new Exception("�Բ�����û��Ȩ�������������̣�");
			}
		}
		TblTaskBase tb = instanceApplication(appid, ei);
		ids[0] = tb.getFsn();

		List start = processDao.findTypeProcessByAppId(appid,new Integer(
				TblProcess.TYPE_START));
		if (start == null || start.size() < 1) {
			throw new Exception("������û�ж��忪ʼ���裬�޷���������");
		}
		TblProcess p = (TblProcess) start.get(0);

		long[] jobMiniJobId = this.instantiateStartProcess(tb, p, ei);

		ids[1] = jobMiniJobId[0];
		ids[2] = jobMiniJobId[1];
		return ids;
	}

	public List listNoBackNextProcess(long curProcessId) {
		return processDao.listNoBackNextProcess(curProcessId);
	}

	public List findNornalNextProcess(long curProcessId) {
		List noBackNextProcess = this.listNoBackNextProcess(curProcessId);
		for (int i = 0; i < noBackNextProcess.size(); i++) {
			TblProcess p = (TblProcess) noBackNextProcess.get(i);
			boolean isRouteAsynchronism = (p.getAppRoute()
					.getIsRouteAsynchronism() != null && p.getAppRoute()
					.getIsRouteAsynchronism().equals("1")) ? true : false;

			if (p.getAppRoute().getIsConditionPath() != null
					&& p.getAppRoute().getIsConditionPath().equals("1")) {
				noBackNextProcess.remove(i);
			} else if (isRouteAsynchronism) {
				noBackNextProcess.remove(i);
			}
		}
		return noBackNextProcess;
	}
	
	
	/**
	 * ����ת��
	 * 
	 */
    public void changeProcess(long taskId, int processId, Map params)throws Exception {
    	
    	TblTaskBase taskBase = taskBaseDao.findById(taskId);
		List l=jobBaseDao.findHandlingJob(taskId);
		if(l==null || l.size()<1){
			throw new Exception("����Ŀ���ܽ���������ʧ��");
		}
		
		TblJobBase handingJob=(TblJobBase)l.get(0);
		List handingMiniJob=miniJobDao.findHandingJob(taskId);
		if(handingMiniJob!=null){
			for(int i=0;i<handingMiniJob.size();i++){
				TblMiniJob mj=(TblMiniJob)handingMiniJob.get(i);
				mj.setFminiJobStatus(TblMiniJob.STATUS_COMPLETE);
				miniJobDao.update(mj);
				this.deleteMiniMessage(mj);
			}
		}
		handingJob.setFjobStatus(TblJobBase.STATUS_CHANGE);
		jobBaseDao.update(handingJob);
		TblProcess curProcess=processDao.findById(handingJob.getFprocessId());
		if(curProcess.getAfterHandleClass()!=null && !"".equals(curProcess.getAfterHandleClass()))
		{
			//������������⡣�ݲ�ִ�к��ô�����������ִ��ǰ�ô�������
		}
		
		TblProcess targetProcess=processDao.findById(processId);
		if (targetProcess.getBeforeHandleClass() != null
				&& !targetProcess.getBeforeHandleClass().equals("")) {

			IBeforeHandleClass ahc = this.getBeforeHandleClass(targetProcess);
			Map returnMap = ahc.handle(targetProcess, handingJob);
			
		}
		
		if(targetProcess.getProcessType()==TblProcess.TYPE_END){
			taskBase.setFtaskStatus(TblTaskBase.STATUS_END);
			taskBaseDao.update(taskBase);
			return;
		}
		else if (targetProcess.getIsProcessAsynchronism() != null
				&& "1".equals(targetProcess.getIsProcessAsynchronism())) {

			Map paramMap = new HashMap();
			paramMap.put("jobStatus", TblJobBase.STATUS_HANDLING);
			paramMap.put("processId", new Long(targetProcess
					.getProcessId()));
			paramMap.put("taskId", new Long(taskBase.getFsn()));

			// ��ѯ��ǰ�����Ƿ��Ѿ���ʵ�����������ڴ���
			List list = jobBaseDao.findByMap(paramMap);
			if (list == null || list.size() < 1) {
				// ���Ŀ�경��û�����ڱ�����
				this.instantiateProcess(taskBase, targetProcess);
			} else {
				// ��һ��������һ���Ѿ�ʵ���������ڴ����;
				TblJobBase jb = (TblJobBase) list.get(0);
				if (jb.getFishidden() != null
						&& jb.getFishidden().equals("1")) {
					jb.setFishidden(0);
					jb.setForderDate(new java.util.Date());
					this.updateJobBaseAndMessageInfo(jb);
					// ids=new long[]{jb.getJobId(),0};
				}
			}
		}else {
			this.instantiateProcess(taskBase, targetProcess);
		}
		
		
		
		
		
    }
	
	
	
	/**
	 * ����һ������
	 * 
	 */
	public long[] backAMiniJob(long taskId,long miniJobId,TblEmployeeInfo ei)throws Exception{
		long[] ids={-1,-1};
		
		
		List l=jobBaseDao.findHandlingJob(taskId);
		if(l==null || l.size()<1){
			throw new Exception("����Ŀ���ܽ���������ʧ��");
		}
		TblJobBase handingJob=(TblJobBase)l.get(0);
		
		TblMiniJob miniJob = miniJobDao.findById(miniJobId);
		TblJobBase miniJobsJobBase = jobBaseDao.findById(miniJob.getFjobId());
		
		if(handingJob.getFsn()==miniJobsJobBase.getFsn()){
			TblMiniJob miniJob2=miniJob.clone();
			miniJob2.setFminiJobUserId(new Integer(""+ei.getFemployeeSn()));
			miniJob2.setFendTime(null);
			miniJob2.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
			miniJob2.setFsubmitOption("");
			miniJob2.setFbeginTime(null);
			miniJob2.setFsn(new Long(""+idService.getMiniJobId()));
			miniJob2.setFminiJobUserName(ei.getFemployeeName());
			this.createMiniJob(miniJob2);
			
			ids[0]=handingJob.getFsn();
			ids[1]=miniJob2.getFsn();
			return ids;
		}
		
		if(handingJob.getFifOpen()!=null && handingJob.getFifOpen()==1){
			throw new Exception("��һ�������Ѿ����򿪣��㲻�ܳ�����");
		}
		
		List miniJobs=miniJobDao.findByProperty("fjobId", handingJob.getFsn());
		if(miniJobs!=null){
			for(int i=0;i<miniJobs.size();i++){
				TblMiniJob curJob=(TblMiniJob)miniJobs.get(i);
				curJob.setFminiJobStatus(TblMiniJob.STATUS_BACK);
				this.deleteMiniMessage(curJob);
				miniJobDao.update(curJob);
			}
		}
		handingJob.setFjobStatus(TblMiniJob.STATUS_BACK);
		jobBaseDao.update(handingJob);
		
		
		TblJobBase miniJobsJobBase2=miniJobsJobBase.clone();
		miniJobsJobBase2.setFendTime(null);
		miniJobsJobBase2.setFjobStatus(TblJobBase.STATUS_HANDLING);
		miniJobsJobBase2.setFsn(new Long(""+idService.getJobBaseId()));
		jobBaseDao.save(miniJobsJobBase2);
		
		TblMiniJob miniJob2=miniJob.clone();
		miniJob2.setFminiJobUserId(new Integer(""+ei.getFemployeeSn()));
		miniJob2.setFendTime(null);
		miniJob2.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
		miniJob2.setFsubmitOption("");
		miniJob2.setFbeginTime(null);
		miniJob2.setFsn(new Long(""+idService.getMiniJobId()));
		miniJob2.setFminiJobUserName(ei.getFemployeeName());
		this.createMiniJob(miniJob2);
		
		ids[0]=handingJob.getFsn();
		ids[1]=miniJob2.getFsn();
		
		return ids;
		
		
		
	}

	public Map processMiniJob(long miniJobId, long nextProcessId,
			TblEmployeeInfo ei, Map otherArg) throws Exception {
		Map map = new HashMap();

		TblMiniJob miniJob = miniJobDao.findById(miniJobId);
		if (miniJob == null) {
			throw new Exception("�Ҳ���Ҫ���������");
		}
		TblJobBase job = jobBaseDao.findById(miniJob.getFjobId());
		TblTaskBase taskBase = taskBaseDao.findById(job.getFtaskId());
		try {
			this.checkIfCanProcessJob(miniJob, job, taskBase, ei);
			TblProcess curProcess = processDao.findById(job.getFprocessId());
			TblProcess nextProcess = processDao.findById(new Integer(
					nextProcessId + ""));
			if (job.getFisAsynchronism() != null
					&& job.getFisAsynchronism().equals("1")) {// �첽�����������������ġ�
				// ����δ��ɵ�JobBase������
				int count = 0;
				List l = findUnHandleJobBase(taskBase.getFsn(), job.getFsn());
				if (l != null) {
					count = l.size();
				}
				if (count > 0) {
					throw new Exception("��������֣�������ɸò��裡");
				}
			}
			int unHandlingJobCount = miniJobDao.getHandlingMiniJobCount(job);
			boolean isBack = false;
			
			TblAppRoute ar = routeDao
					.findAppRouteFromBeginProcessAndEndProcess(job
							.getFprocessId(), nextProcessId);
			if (ar != null) {
				if ("1".equals(ar.getIsBack())) {
					isBack = true;
				}
			}

			miniJob.setFminiJobStatus(TblMiniJob.STATUS_COMPLETE);
			miniJobDao.update(miniJob);

			if (!isBack && unHandlingJobCount > 1) {
				return null;
			}

			// ִ�е�ǰ����ĺ��ô�������
			if (curProcess.getAfterHandleClass() != null
					&& !curProcess.getAfterHandleClass().equals("")) {
				IAfterHandleClass ahc = this.getAfterHandleClass(curProcess);

				ahc.handle(curProcess, job);
			}

			if (isBack) {
				List l = miniJobDao.findByProperty("fjobId", job.getFsn());
				if (l != null && l.size() > 0) {
					for (int i = 0; i < l.size(); i++) {
						TblMiniJob mj = (TblMiniJob) l.get(i);
						mj.setFminiJobStatus(TblMiniJob.STATUS_BACK);
						miniJobDao.update(mj);
						// ɾ�������Ӧ��Ϣ��
						
						deleteMiniMessage(mj);
					}
				}
				job.setFjobStatus(TblJobBase.STATUS_BACK);
				jobBaseDao.update(job);
			}

			boolean ifRecordSubmitInfo = false;
			try {
				String code = curProcess.getProcessCode();
				if(code!=null && !code.equals("")){
					float a = Float.parseFloat(code);
					if (a > 10 && a < 16) {
						ifRecordSubmitInfo = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			String fromTo = "��[" + curProcess.getProcessName() + "]��["
					+ nextProcess.getProcessName() + "]";
			boolean isFormChecker = false;
			if (curProcess.getProcessCode() != null
					&& curProcess.getProcessCode().equals("11")) {
				isFormChecker = true;
			}

			if (unHandlingJobCount <= 1 && !isBack && ifRecordSubmitInfo) {
				// ����ҵ����Լ������

			}

			if (unHandlingJobCount <= 1 || isBack) {
				job.setFjobStatus(TblJobBase.STATUS_COMPLETE);
				job.setFendTime(new java.util.Date());
				jobBaseDao.update(job);

				// ִ����һ�������ǰ�ô���
				if (nextProcess.getBeforeHandleClass() != null
						&& !nextProcess.getBeforeHandleClass().equals("")) {

					IBeforeHandleClass ahc = this.getBeforeHandleClass(nextProcess);
					Map returnMap = ahc.handle(nextProcess, job);
					
				}

				if (nextProcess.getProcessType() == TblProcess.TYPE_END) {
					taskBase.setFtaskEndTime(new Date());
					taskBase.setFtaskStatus(TblTaskBase.STATUS_END);

					taskBaseDao.update(taskBase);
				} else {
					
					if (nextProcess.getIsProcessAsynchronism() != null
							&& "1".equals(nextProcess.getIsProcessAsynchronism())) {

						Map paramMap = new HashMap();
						paramMap.put("jobStatus", TblJobBase.STATUS_HANDLING);
						paramMap.put("processId", new Long(nextProcess
								.getProcessId()));
						paramMap.put("taskId", new Long(taskBase.getFsn()));

						// ��ѯ��ǰ�����Ƿ��Ѿ���ʵ�����������ڴ���
						List list = jobBaseDao.findByMap(paramMap);
						if (list == null || list.size() < 1) {
							// ���Ŀ�경��û�����ڱ�����
							if (isBack) {
								instantiateProcess(taskBase, nextProcess, true);
							} else {
								instantiateProcessAsynchronism(taskBase,nextProcess);
							}
							
						} else {
							// ��һ��������һ���Ѿ�ʵ���������ڴ����;
							TblJobBase jb = (TblJobBase) list.get(0);
							if (jb.getFishidden() != null
									&& jb.getFishidden().equals("1")) {

								jb.setFishidden(0);
								jb.setForderDate(new java.util.Date());
								this.updateJobBaseAndMessageInfo(jb);
								// ids=new long[]{jb.getJobId(),0};
							}
							
						}
					} else {
						// ���첽���衣
						instantiateProcess(taskBase, nextProcess, false);
						
					}
					
					// ���ҵ�ǰ�����������һ�����衣 ������ѯ��һ�����衣
					List nextProcesses = this.listNoBackNextProcess(job.getFprocessId());
					
					if (nextProcesses != null && nextProcesses.size() > 0) {
						for (int i = 0; i < nextProcesses.size(); i++) {
							TblProcess np = (TblProcess) nextProcesses.get(i);
							if (np.getProcessId() == nextProcess.getProcessId()) {
								continue;
							}
							if (np.getIsProcessAsynchronism() != null
									&& np.getIsProcessAsynchronism()
											.equals("1")
									&& np.getAppRoute()
											.getIsRouteAsynchronism() != null
									&& np.getAppRoute()
											.getIsRouteAsynchronism().equals(
													"1")) {

								// �첽
								Map paramMap = new HashMap();
								paramMap.put("jobStatus",
										TblJobBase.STATUS_HANDLING);
								paramMap.put("processId", new Long(nextProcess
										.getProcessId()));
								paramMap.put("taskId", new Long(taskBase
										.getFsn()));

								// ��ѯ��ǰ�����Ƿ��Ѿ���ʵ�����������ڴ���
								List list = jobBaseDao.findByMap(paramMap);
								if (list.size() < 1) {
									if (np.getBeforeHandleClass() != null
											&& !np.getBeforeHandleClass()
													.equals("")) {

										IBeforeHandleClass ahc = this
												.getBeforeHandleClass(nextProcess);
										Map returnMap = ahc.handle(np, job);
									}

									instantiateProcessAsynchronism(taskBase,
											nextProcess);
								}
							}
						}
					}

				}
			}
			deleteMiniMessage(miniJob);
		} catch (Exception e) {
			this.backToPreJob(miniJob, job);
			throw e;
		}
		return map;
	}
	
	public void deleteMiniMessage(TblMiniJob mj){
		
	}

	public void instantiateProcessAsynchronism(TblTaskBase taskBase,
			TblProcess nextProcess) throws Exception {
		TblJobBase job = this.processToJobAsync(taskBase, nextProcess);
		// ��JobBase�ֽ��MiniJob
		TblMiniJob[] miniJobs = jobSpliter.splitJob(nextProcess, job);
		if (miniJobs == null || miniJobs.length == 0) {
			throw new Exception("��һ������û�д�����");
		}
		if (miniJobs != null && miniJobs.length > 0) {
			for (int i = 0; i < miniJobs.length; i++) {
				// miniJobs[i].setfm(nextProcess.getProcessName());
				TblMiniJob mj = (TblMiniJob) miniJobs[i];
				createMiniJob(mj);
			}
		}
	}

	// ����������Ϣ��
	public void updateJobBaseAndMessageInfo(TblJobBase jb) {
		jobBaseDao.update(jb);
	}

	public void instantiateProcess(TblTaskBase taskBase,
			TblProcess nextProcess, boolean isBack) throws Exception {

		TblJobBase job = this.processToJob(taskBase, nextProcess);
		// ��JobBase�ֽ��MiniJob
		TblMiniJob[] miniJobs = jobSpliter.splitJob(nextProcess, job);
		if (miniJobs == null || miniJobs.length == 0) {
			jobBaseDao.delete(job);
			throw new Exception("��һ������û�д�����");
		}
		if (miniJobs != null && miniJobs.length > 0) {
			for (int i = 0; i < miniJobs.length; i++) {
				// miniJobs[i].setfm(nextProcess.getProcessName());
				TblMiniJob mj = (TblMiniJob) miniJobs[i];
				createMiniJob(mj);
			}
		}
	}

	private TblJobBase processToJobAsync(TblTaskBase task, TblProcess process) {
		TblJobBase job = new TblJobBase();

		job.setFappId(new Integer(process.getAppId() + ""));
		job.setFjobStatus(TblJobBase.STATUS_HANDLING);
		job.setFprocessId(new Integer(process.getProcessId() + ""));
		// job.setBeginTime(new Date());
		job.setFtaskId(new Long(task.getFsn() + ""));
		job.setFisAsynchronism(1);
		job.setFishidden(1);
		job.setForderDate(new java.util.Date());
		job.setFsn(new Long(idService.getJobBaseId()));
		job.setFifOpen(0);
		
		jobBaseDao.save(job);
		return job;
	}

	public TblMiniJob createMiniJob(TblMiniJob mj) {
		mj.setFsn(new Long(idService.getMiniJobId()));
		miniJobDao.save(mj);

		return mj;
	}

	public void backToPreJob(TblMiniJob mj, TblJobBase job) {

		job.setFjobStatus(TblJobBase.STATUS_HANDLING);
		job.setFendTime(null);
		jobBaseDao.update(job);
		
		

		mj.setFminiJobStatus(TblMiniJob.STATUS_HANDLING);
		mj.setFendTime(null);
		miniJobDao.update(mj);

	}

	public List findUnHandleJobBase(Long taskId, Long jobId) {
		return jobBaseDao.findUnHandleJobBase(taskId, jobId);
	}

	public boolean checkIfCanProcessJob(long miniJobId, TblEmployeeInfo ei)
			throws Exception {

		TblMiniJob miniJob = miniJobDao.findById(miniJobId);
		if (miniJob == null) {
			throw new Exception("�Ҳ���Ҫ���������");
		}
		TblJobBase job = jobBaseDao.findById(miniJob.getFjobId());
		TblTaskBase taskBase = taskBaseDao.findById(job.getFtaskId());

		return this.checkIfCanProcessJob(miniJob, job, taskBase, ei);
	}

	public boolean checkIfCanProcessJob(TblMiniJob miniJob, TblJobBase job,
			TblTaskBase taskBase, TblEmployeeInfo ei) throws Exception {
		
		if (miniJob == null) {
			throw new Exception("�Ҳ���Ҫ���������");
		}
		
		if (!(miniJob.getFminiJobUserId() + "").equals(ei.getFemployeeSn()+"")) {
			throw new Exception("�Բ�������Ȩ����");
		}
		
		if (taskBase.getFtaskStatus().intValue() == TblTaskBase.STATUS_PAUSE) {
			throw new Exception("�����Ѿ�����ͣ����Ĵ�����Ч");
		}
		if (taskBase.getFtaskStatus().intValue() == TblTaskBase.FLAG_DELETE) {
			throw new Exception("�����Ѿ���ɾ������Ĵ�����Ч");
		}

		if (taskBase.getFtaskStatus().intValue() == TblTaskBase.STATUS_EXCEPTION) {
			throw new Exception("���̴����쳣����Ĵ�����Ч");
		}
		
		if (miniJob == null) {
			throw new Exception("��Ҫ��������񲻴��ڣ���ˢ���б�");
		}
		if (miniJob.getFminiJobStatus() == TblMiniJob.STATUS_COMPLETE) {
			throw new Exception("�������Ѿ�������ɣ������ٴν��д�����ˢ���б�");
		}
		if (miniJob.getFminiJobStatus() == TblMiniJob.STATUS_EXCEPTION) {
			throw new Exception("���������쳣״̬�������Ѿ���ֹͣ������ˢ���б�");
		}
		if (miniJob.getFminiJobStatus() == TblMiniJob.STATUS_BACK) {
			throw new Exception("�������Ѿ������أ���ˢ���б�");
		}

		if (job.getFjobStatus() == TblJobBase.STATUS_EXCEPTION) {
			throw new Exception("���������쳣״̬�������Ѿ���ֹͣ������ˢ���б�");
		}
		if (job.getFjobStatus() == TblJobBase.STATUS_COMPLETE) {
			throw new Exception("�������Ѿ���ɣ������ٴν��д�����ˢ���б�");
		}

		if (job.getFjobStatus() == TblJobBase.STATUS_BACK) {
			throw new Exception("�������Ѿ������أ������ٴν��д�����ˢ���б�");
		}

		if (job.getFjobStatus() == TblJobBase.STATUS_CHANGE) {
			throw new Exception("�������Ѿ�����ת�������ٴν��д�����ˢ���б�");
		}

		return true;
	}

	private IBeforeHandleClass getBeforeHandleClass(TblProcess process)
			throws Exception {
		String clz = process.getBeforeHandleClass();
		Object obj = null;
		try {
			Class clzz = Class.forName(clz);
			obj = clzz.newInstance();
		} catch (Exception ex) {
			throw new Exception("�޷���ȡʵ��");
		}

		if (obj instanceof IBeforeHandleClass) {
			return (IBeforeHandleClass) obj;
		} else {
			throw new Exception("����ʵ�ֽӿ� [" + IBeforeHandleClass.class.getName()
					+ "]");
		}
	}

	private IAfterHandleClass getAfterHandleClass(TblProcess process)
			throws Exception {
		String clz = process.getAfterHandleClass();
		Object obj = null;
		try {
			Class clzz = Class.forName(clz);
			obj = clzz.newInstance();
		} catch (Exception ex) {
			throw new Exception("�޷���ȡʵ��");
		}

		if (obj instanceof IAfterHandleClass) {
			return (IAfterHandleClass) obj;
		} else {
			throw new Exception("����ʵ�ֽӿ� [" + IAfterHandleClass.class.getName()
					+ "]");
		}
	}

	public void update(TblJobBase transientInstance) {
		jobBaseDao.update(transientInstance);
	}

	public void delete(TblJobBase persistentInstance) {
		jobBaseDao.delete(persistentInstance);
	}

	public TblJobBase findById(java.lang.Long id) {
		return jobBaseDao.findById(id);
	}

	public List findByProperty(String propertyName, Object value) {
		return jobBaseDao.findByProperty(propertyName, value);
	}

}
