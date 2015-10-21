package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblId.
 * 
 * @see com.maven.flow.hibernate.dao.TblId
 * @author MyEclipse Persistence Tools
 */

public class TblIdDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblIdDAO.class);

	// property constants
	public static final String TABLE_NAME = "tableName";

	public static final String FIELD_NAME = "fieldName";

	public static final String FVALUE = "fvalue";

	public void save(TblId transientInstance) {
		log.debug("saving TblId instance");
		try {
			Transaction trans=getSession().beginTransaction();
			getSession().saveOrUpdate(transientInstance);
			trans.commit();
			getSession().close();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TblId persistentInstance) {
		log.debug("deleting TblId instance");
		try {
			Transaction trans=getSession().beginTransaction();
			getSession().delete(persistentInstance);
			trans.commit();
			getSession().close();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TblId findById(java.lang.Integer id) {
		log.debug("getting TblId instance with id: " + id);
		try {
			TblId instance = (TblId) getSession().get(
					"com.maven.flow.hibernate.dao.TblId", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblId instance) {
		log.debug("finding TblId instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblId").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public TblId findByTableField(String tableName,String fieldName)
	{
		TblId tblid=null;
		try {
			String queryString = "from TblId as model where model.tableName=? and  model.fieldName=? ";
					
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, tableName);
			queryObject.setParameter(1, fieldName);			
			List list= queryObject.list();
			if (list.size()>0 )
			{
				tblid=(TblId)list.get(0);
			}
			
			
			
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by property name failed", re);
			throw re;
		}
		return tblid;
	}
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblId instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblId as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTableName(Object tableName) {
		return findByProperty(TABLE_NAME, tableName);
	}

	public List findByFieldName(Object fieldName) {
		return findByProperty(FIELD_NAME, fieldName);
	}

	public List findByFvalue(Object fvalue) {
		return findByProperty(FVALUE, fvalue);
	}

	public List findAll() {
		log.debug("finding all TblId instances");
		try {
			String queryString = "from TblId";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TblId merge(TblId detachedInstance) {
		log.debug("merging TblId instance");
		try {
			TblId result = (TblId) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblId instance) {
		log.debug("attaching dirty TblId instance");
		try {
			Transaction trans=getSession().beginTransaction();
			getSession().saveOrUpdate(instance);
			trans.commit();
			getSession().close();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TblId instance) {
		log.debug("attaching clean TblId instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}