package com.maven.flow.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblProcess.
 * 
 * @see com.maven.flow.hibernate.dao.TblProcess
 * @author MyEclipse Persistence Tools
 */

public class TblProcessDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblProcessDAO.class);

	// property constants
	public static final String PROCESS_TYPE = "processType";

	public static final String APP_ID = "appId";

	public static final String PROCESS_NAME = "processName";

	public static final String PROCESS_DESC = "processDesc";

	public static final String PROCESS_DISPLAY_TEXT = "processDisplayText";

	public static final String PROCESS_ROUTE_RULE = "processRouteRule";

	public static final String PROCESS_JOB_SPLIT_RULE = "processJobSplitRule";

	public static final String PROCESS_JOB_SPLIT_CUSTOMIZE = "processJobSplitCustomize";

	public static final String PROCESS_COMPLETE_CHECK_RULE = "processCompleteCheckRule";

	public static final String PROCESS_COMPLETE_RULE_CUSTOMIZE = "processCompleteRuleCustomize";

	public static final String PROCESS_DOC_FOLDER_ACCESS_RULE = "processDocFolderAccessRule";

	public static final String PROCESS_POSITION_INFO = "processPositionInfo";

	public static final String MULTI_JOB_HANDLE = "multiJobHandle";

	public static final String PARENT_PROCESS_ID = "parentProcessId";

	public static final String IS_WAIT_FOR_SUB_FLOW = "isWaitForSubFlow";

	public static final String IFCAN_SPLIT_FLOW = "ifcanSplitFlow";

	public static final String ISFIRST_SUB_FLOW = "isfirstSubFlow";

	public static final String PROCESS_STATE = "processState";

	public static final String SPLIT_TYPE = "splitType";

	public static final String SPLIT_PROCESS_HANDLE = "splitProcessHandle";

	public static final String IS_SUB_FLOW_STEP = "isSubFlowStep";

	public static final String INNER_STEP = "innerStep";

	public static final String STEP = "step";

	public static final String UNITE_STEP = "uniteStep";

	public static final String STEP_STATE = "stepState";

	public void save(TblProcess transientInstance) {
		log.debug("saving TblProcess instance");
		try {
			Transaction tran=getSession().beginTransaction();
			//transientInstance=(TblProcess)this.getSession().merge(transientInstance);
			
			getSession().saveOrUpdate(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			
			throw re;
		}
	}
	
	public void saveOrUpdate(TblProcess transientInstance) {
		log.debug("saving TblProcess instance");
		try {
			Transaction tran=getSession().beginTransaction();
			this.getSession().flush();
			getSession().saveOrUpdate(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	

	public void delete(TblProcess persistentInstance) {
		log.debug("deleting TblProcess instance");
		try {
			Transaction tran=getSession().beginTransaction();
			getSession().delete(persistentInstance);
			//this.getSession().close();
			tran.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);

			throw re;
		}
	}
	public void deleteAllProcessByAppId(Integer AppId) {
		log.debug("deleting TblProcess instance");
		try {
			Transaction tran=getSession().beginTransaction();
			Query query=getSession().createQuery("delete from TblProcess where appId="+AppId);
			query.executeUpdate();
			
			/*
			List list=this.findByProperty("appId", AppId);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					TblProcess ar=(TblProcess)list.get(i);
					this.delete(ar);
				}
			}
			*/
			tran.commit();
			
			this.getSession().close();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public TblProcess findById(java.lang.Integer id) {
		log.debug("getting TblProcess instance with id: " + id);
		try {
			TblProcess instance = (TblProcess) getSession().get(
					"com.maven.flow.hibernate.dao.TblProcess", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblProcess instance) {
		log.debug("finding TblProcess instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblProcess").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public static void main(String args[]){
		TblProcessDAO p=new TblProcessDAO();
		List l=p.listNoBackNextProcess(1082);
		
		System.out.println(l.size());
		
	}
	
	public  List listNoBackNextProcess(long curProcessId){
		
		try{
			/*
			String sql=" select new TblProcess(),new TblAppRoute(r.routeId, r.appId, r.routeName, r.routeDesc, r.routeDisplayText," +
					" r.routeBeginProcess, r.routeEndProcess, r.routePositionInfo, r.subFlowPath, " +
					"r.isConditionPath, r.isBack, r.isRouteAsynchronism, r.conditionClass) " +
					*/
			String sql="from TblProcess as p,TblAppRoute as r  " +
					" where p.processId=r.routeEndProcess " +
					" and (r.isBack is null or r.isBack='0') " +
					" and r.routeBeginProcess="+curProcessId+" " +
					" order by p.processType desc,CONVERT(money, p.processCode) ";
			
			System.out.println(sql);
			
			Query queryObject = getSession().createQuery(sql);
			List returnList=new ArrayList();
			
			List objectArrays=queryObject.list();
			if(objectArrays!=null && objectArrays.size()>0){
				for(int i=0;i<objectArrays.size();i++){
					
					Object o[]=(Object[])objectArrays.get(i);
					TblProcess p=(TblProcess)o[0];
					TblAppRoute r=(TblAppRoute)o[1];
					p.setAppRoute(r);
					returnList.add(p);
				}
			}
			
			return returnList;
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return new ArrayList();
	}
	
	
	public List findTypeProcessByAppId(Integer appid,Integer processType)
	{
		
		String sql=" from TblProcess as model where model.appId="+appid+"" +
				" and processType="+processType;
		Query queryObject = getSession().createQuery(sql);
		return queryObject.list();
		
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblProcess instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblProcess as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProcessType(Object processType) {
		return findByProperty(PROCESS_TYPE, processType);
	}

	public List findByAppId(Object appId) {
		return findByProperty(APP_ID, appId);
	}

	public List findByProcessName(Object processName) {
		return findByProperty(PROCESS_NAME, processName);
	}

	public List findByProcessDesc(Object processDesc) {
		return findByProperty(PROCESS_DESC, processDesc);
	}

	public List findByProcessDisplayText(Object processDisplayText) {
		return findByProperty(PROCESS_DISPLAY_TEXT, processDisplayText);
	}

	public List findByProcessRouteRule(Object processRouteRule) {
		return findByProperty(PROCESS_ROUTE_RULE, processRouteRule);
	}

	public List findByProcessJobSplitRule(Object processJobSplitRule) {
		return findByProperty(PROCESS_JOB_SPLIT_RULE, processJobSplitRule);
	}

	public List findByProcessJobSplitCustomize(Object processJobSplitCustomize) {
		return findByProperty(PROCESS_JOB_SPLIT_CUSTOMIZE,
				processJobSplitCustomize);
	}

	public List findByProcessCompleteCheckRule(Object processCompleteCheckRule) {
		return findByProperty(PROCESS_COMPLETE_CHECK_RULE,
				processCompleteCheckRule);
	}

	public List findByProcessCompleteRuleCustomize(
			Object processCompleteRuleCustomize) {
		return findByProperty(PROCESS_COMPLETE_RULE_CUSTOMIZE,
				processCompleteRuleCustomize);
	}

	public List findByProcessDocFolderAccessRule(
			Object processDocFolderAccessRule) {
		return findByProperty(PROCESS_DOC_FOLDER_ACCESS_RULE,
				processDocFolderAccessRule);
	}

	public List findByProcessPositionInfo(Object processPositionInfo) {
		return findByProperty(PROCESS_POSITION_INFO, processPositionInfo);
	}

	public List findByMultiJobHandle(Object multiJobHandle) {
		return findByProperty(MULTI_JOB_HANDLE, multiJobHandle);
	}

	public List findByParentProcessId(Object parentProcessId) {
		return findByProperty(PARENT_PROCESS_ID, parentProcessId);
	}

	public List findByIsWaitForSubFlow(Object isWaitForSubFlow) {
		return findByProperty(IS_WAIT_FOR_SUB_FLOW, isWaitForSubFlow);
	}

	public List findByIfcanSplitFlow(Object ifcanSplitFlow) {
		return findByProperty(IFCAN_SPLIT_FLOW, ifcanSplitFlow);
	}

	public List findByIsfirstSubFlow(Object isfirstSubFlow) {
		return findByProperty(ISFIRST_SUB_FLOW, isfirstSubFlow);
	}

	public List findByProcessState(Object processState) {
		return findByProperty(PROCESS_STATE, processState);
	}

	public List findBySplitType(Object splitType) {
		return findByProperty(SPLIT_TYPE, splitType);
	}

	public List findBySplitProcessHandle(Object splitProcessHandle) {
		return findByProperty(SPLIT_PROCESS_HANDLE, splitProcessHandle);
	}

	public List findByIsSubFlowStep(Object isSubFlowStep) {
		return findByProperty(IS_SUB_FLOW_STEP, isSubFlowStep);
	}

	public List findByInnerStep(Object innerStep) {
		return findByProperty(INNER_STEP, innerStep);
	}

	public List findByStep(Object step) {
		return findByProperty(STEP, step);
	}

	public List findByUniteStep(Object uniteStep) {
		return findByProperty(UNITE_STEP, uniteStep);
	}

	public List findByStepState(Object stepState) {
		return findByProperty(STEP_STATE, stepState);
	}

	public List findAll() {
		log.debug("finding all TblProcess instances");
		try {
			String queryString = "from TblProcess";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblProcess merge(TblProcess detachedInstance) {
		log.debug("merging TblProcess instance");
		try {
			TblProcess result = (TblProcess) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblProcess instance) {
		log.debug("attaching dirty TblProcess instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblProcess instance) {
		log.debug("attaching clean TblProcess instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}