package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblApplication.
 * 
 * @see com.maven.flow.hibernate.dao.TblApplication
 * @author MyEclipse Persistence Tools
 */

public class TblApplicationDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblApplicationDAO.class);

	// property constants
	public static final String APP_NAME = "appName";

	public static final String APP_DESC = "appDesc";

	public static final String APP_CREATE_USER = "appCreateUser";

	public static final String APP_CREATE_USER_NAME = "appCreateUserName";

	public static final String APP_RELEASE_USER_NAME = "appReleaseUserName";

	public static final String APP_RELEASE_USER = "appReleaseUser";

	public static final String APP_MONITOR_RULE = "appMonitorRule";

	public static final String APP_VALID_STATUS = "appValidStatus";

	public static final String APP_RELEASE_STATUS = "appReleaseStatus";

	public static final String APP_FLOW_DATA = "appFlowData";

	public static final String SUB_FLOW_FLAG = "subFlowFlag";

	public void save(TblApplication transientInstance) {
		log.debug("saving TblApplication instance");
		try {
			Transaction tran=getSession().beginTransaction();
			//transientInstance=(TblApplication)this.getSession().merge(transientInstance);
			getSession().saveOrUpdate(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TblApplication persistentInstance) {
		log.debug("deleting TblApplication instance");
		try {
			Transaction trans=getSession().beginTransaction();
			getSession().delete(persistentInstance);
			trans.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void deleteyAppId(Integer AppId) {
		log.debug("deleting TblApplication instance");
		try {
			Transaction trans=getSession().beginTransaction();
			Query query=getSession().createQuery("delete from TblApplication where appId="+AppId);
            query.executeUpdate();
			trans.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public TblApplication findById(java.lang.Integer id) {
		log.debug("getting TblApplication instance with id: " + id);
		try {
			TblApplication instance = (TblApplication) getSession().get(
					"com.maven.flow.hibernate.dao.TblApplication", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblApplication instance) {
		log.debug("finding TblApplication instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblApplication").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblApplication instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TblApplication as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAppName(Object appName) {
		return findByProperty(APP_NAME, appName);
	}

	public List findByAppDesc(Object appDesc) {
		return findByProperty(APP_DESC, appDesc);
	}

	public List findByAppCreateUser(Object appCreateUser) {
		return findByProperty(APP_CREATE_USER, appCreateUser);
	}

	public List findByAppCreateUserName(Object appCreateUserName) {
		return findByProperty(APP_CREATE_USER_NAME, appCreateUserName);
	}

	public List findByAppReleaseUserName(Object appReleaseUserName) {
		return findByProperty(APP_RELEASE_USER_NAME, appReleaseUserName);
	}

	public List findByAppReleaseUser(Object appReleaseUser) {
		return findByProperty(APP_RELEASE_USER, appReleaseUser);
	}

	public List findByAppMonitorRule(Object appMonitorRule) {
		return findByProperty(APP_MONITOR_RULE, appMonitorRule);
	}

	public List findByAppValidStatus(Object appValidStatus) {
		return findByProperty(APP_VALID_STATUS, appValidStatus);
	}

	public List findByAppReleaseStatus(Object appReleaseStatus) {
		return findByProperty(APP_RELEASE_STATUS, appReleaseStatus);
	}

	public List findByAppFlowData(Object appFlowData) {
		return findByProperty(APP_FLOW_DATA, appFlowData);
	}

	public List findBySubFlowFlag(Object subFlowFlag) {
		return findByProperty(SUB_FLOW_FLAG, subFlowFlag);
	}

	public List findAll() {
		log.debug("finding all TblApplication instances");
		try {
			String queryString = "from TblApplication";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblApplication merge(TblApplication detachedInstance) {
		log.debug("merging TblApplication instance");
		try {
			TblApplication result = (TblApplication) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblApplication instance) {
		log.debug("attaching dirty TblApplication instance");
		try {
			Transaction trans=getSession().beginTransaction();
			getSession().saveOrUpdate(instance);
			trans.commit();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblApplication instance) {
		log.debug("attaching clean TblApplication instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}