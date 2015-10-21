package com.maven.flow.instance.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.maven.flow.hibernate.dao.BaseHibernateDAO;

/**
 * Data access object (DAO) for domain model class TblTaskBase.
 * 
 * @see com.maven.flow.instance.hibernate.dao.TblTaskBase
 * @author MyEclipse - Hibernate Tools
 */
public class TblTaskBaseDAO extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(TblJobBaseDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(TblTaskBase transientInstance) {
		log.debug("saving TblTaskBase instance");
		try {
			Transaction tran = getSession().beginTransaction();
			getSession().save(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(TblTaskBase transientInstance) {
		log.debug("update TblTaskBase instance");
		try {
			Transaction tran = getSession().beginTransaction();
			getSession().update(transientInstance);
			tran.commit();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(TblTaskBase persistentInstance) {
		log.debug("deleting TblTaskBase instance");
		try {
			Transaction tran = getSession().beginTransaction();
			getSession().delete(persistentInstance);
			tran.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblTaskBase findById(java.lang.Long id) {
		log.debug("getting TblTaskBase instance with id: " + id);
		try {
			TblTaskBase instance = (TblTaskBase) getSession().get(
					"com.maven.flow.instance.hibernate.dao.TblTaskBase", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblTaskBase instance with property: " + propertyName
				+ ", value: " + value);
		log.debug("finding TblApplication instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TblTaskBase as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public static TblTaskBaseDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TblTaskBaseDAO) ctx.getBean("TblTaskBaseDAO");
	}
}