package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class TblMenuBaseDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblMenuBaseDAO.class);

	// property constants


	public void save(TblMenuBase transientInstance) {
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
	
	public void saveOrUpdate(TblMenuBase transientInstance) {
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
	
	

	public void delete(TblMenuBase persistentInstance) {
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
	public TblMenuBase findById(java.lang.Integer id) {
		log.debug("getting TblProcess instance with id: " + id);
		try {
			TblMenuBase instance = (TblMenuBase) getSession().get(
					"com.maven.flow.hibernate.dao.TblMenuBase", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List findAll() {
		
		try {
			String queryString = "from TblMenuBase as model ";
			Query queryObject = getSession().createQuery(queryString);
			
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
