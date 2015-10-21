package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

public class TblProcessPeopleDAO extends BaseHibernateDAO{
	private static final Log log = LogFactory.getLog(TblProcessDAO.class);
	
	public void save(TblProcessPeople transientInstance) {
		log.debug("saving TblProcessPeople instance");
		try {
			Transaction tran=getSession().beginTransaction();
			//transientInstance=(TblProcessPeople)this.getSession().merge(transientInstance);
			getSession().saveOrUpdate(transientInstance);
			tran.commit();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(TblProcessPeople transientInstance) {
		log.debug("update TblProcessPeople instance");
		try {
			Transaction tran=getSession().beginTransaction();
			this.getSession().flush();
			getSession().update(transientInstance);
			tran.commit();
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void delete(TblProcessPeople persistentInstance) {
		log.debug("deleting TblProcessPeople instance");
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
	public void deleteAllProcessByProcessId(Integer pid) {
		log.debug("deleting TblProcessPeople instance");
		try {
			Transaction tran=getSession().beginTransaction();
			Query query=getSession().createQuery("delete from TblProcessPeople where processId="+pid);
			query.executeUpdate();
			
			/*
			List list=this.findByProperty("processId", pid);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					TblProcessPeople ar=(TblProcessPeople)list.get(i);
					this.delete(ar);
				}
			}
			*/
			//this.getSession().close();
			
			tran.commit();
			this.getSession().close();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public TblProcessPeople findById(java.lang.Integer id) {
		log.debug("getting TblProcessPeople instance with id: " + id);
		try {
			TblProcessPeople instance = (TblProcessPeople) getSession().get(
					"com.maven.flow.hibernate.dao.TblProcessPeople", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblProcessPeople instance) {
		log.debug("finding TblProcessPeople instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblProcessPeople").add(
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
		log.debug("finding TblProcessPeople instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblProcessPeople as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
