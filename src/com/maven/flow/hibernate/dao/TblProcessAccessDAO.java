package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblProcessAccess.
 * 
 * @see com.maven.flow.hibernate.dao.TblProcessAccess
 * @author MyEclipse Persistence Tools
 */

public class TblProcessAccessDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblProcessAccessDAO.class);

	// property constants
	public static final String PROCESS_ID = "processId";

	public static final String ROLE_ID = "roleId";

	public static final String ROLE_TYPE = "roleType";

	public void save(TblProcessAccess transientInstance) {
		log.debug("saving TblProcessAccess instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TblProcessAccess persistentInstance) {
		log.debug("deleting TblProcessAccess instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	
	public void deleteAllProcessByAppId(Integer appId) {
		log.debug("deleting TblProcessAccess instance");
		try {
			//Query query=getSession().e .createQuery("delete from TblProcessAccess where appId="+appId);
			Transaction tx=getSession().beginTransaction();

			Query query=getSession().createQuery("delete from TblProcessAccess where processId in (select processId from TblProcess where appId="+appId.intValue()+" )");
            //System.out.println("maven appId================"+appId.intValue()); 
			//query.setInteger(0,appId);
			query.executeUpdate();
			tx.commit();

			getSession().close();


		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public TblProcessAccess findById(java.lang.Integer id) {
		log.debug("getting TblProcessAccess instance with id: " + id);
		try {
			TblProcessAccess instance = (TblProcessAccess) getSession().get(
					"com.maven.flow.hibernate.dao.TblProcessAccess", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblProcessAccess instance) {
		log.debug("finding TblProcessAccess instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblProcessAccess").add(
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
		log.debug("finding TblProcessAccess instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TblProcessAccess as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProcessId(Object processId) {
		return findByProperty(PROCESS_ID, processId);
	}

	public List findByRoleId(Object roleId) {
		return findByProperty(ROLE_ID, roleId);
	}

	public List findByRoleType(Object roleType) {
		return findByProperty(ROLE_TYPE, roleType);
	}

	public List findAll() {
		log.debug("finding all TblProcessAccess instances");
		try {
			String queryString = "from TblProcessAccess";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblProcessAccess merge(TblProcessAccess detachedInstance) {
		log.debug("merging TblProcessAccess instance");
		try {
			TblProcessAccess result = (TblProcessAccess) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblProcessAccess instance) {
		log.debug("attaching dirty TblProcessAccess instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblProcessAccess instance) {
		log.debug("attaching clean TblProcessAccess instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}