package com.maven.flow.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblRole.
 * 
 * @see com.maven.flow.hibernate.dao.TblRole
 * @author MyEclipse Persistence Tools
 */

public class TblRoleDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblRoleDAO.class);

	// property constants
	public static final String FROLE_NAME = "froleName";

	public static final String FROLE_DESC = "froleDesc";

	public static final String FROLE_FUNCTION = "froleFunction";

	public static final String FROLE_CODE = "froleCode";

	public void save(TblRole transientInstance) {
		log.debug("saving TblRole instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TblRole persistentInstance) {
		log.debug("deleting TblRole instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblRole findById(java.lang.Long id) {
		log.debug("getting TblRole instance with id: " + id);
		try {
			TblRole instance = (TblRole) getSession().get(
					"com.maven.flow.instance.hibernate.dao.TblRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblRole instance) {
		log.debug("finding TblRole instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.instance.hibernate.dao.TblRole").add(
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
		log.debug("finding TblRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblRole as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFroleName(Object froleName) {
		return findByProperty(FROLE_NAME, froleName);
	}

	public List findByFroleDesc(Object froleDesc) {
		return findByProperty(FROLE_DESC, froleDesc);
	}

	public List findByFroleFunction(Object froleFunction) {
		return findByProperty(FROLE_FUNCTION, froleFunction);
	}

	public List findByFroleCode(Object froleCode) {
		return findByProperty(FROLE_CODE, froleCode);
	}

	public List findAll() {
		log.debug("finding all TblRole instances");
		try {
			String queryString = "from TblRole";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblRole merge(TblRole detachedInstance) {
		log.debug("merging TblRole instance");
		try {
			TblRole result = (TblRole) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblRole instance) {
		log.debug("attaching dirty TblRole instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblRole instance) {
		log.debug("attaching clean TblRole instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}