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
 * Data access object (DAO) for domain model class TblMiniJob.
 * 
 * @see com.maven.flow.instance.hibernate.dao.TblMiniJob
 * @author MyEclipse - Hibernate Tools
 */
public class TblMiniJobDAO extends BaseHibernateDAO {

	private static final Log log = LogFactory.getLog(TblJobBaseDAO.class);

	protected void initDao() {
		// do nothing
	}

	public void save(TblMiniJob transientInstance) {
		log.debug("saving TblMiniJob instance");
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

	public void update(TblMiniJob transientInstance) {
		log.debug("update TblMiniJob instance");
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

	public void delete(TblMiniJob persistentInstance) {
		log.debug("deleting TblMiniJob instance");
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

	public TblMiniJob findById(java.lang.Long id) {
		log.debug("getting TblMiniJob instance with id: " + id);
		try {
			TblMiniJob instance = (TblMiniJob) getSession().get(
					"com.maven.flow.instance.hibernate.dao.TblMiniJob", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	
	public List findHandingJob(long taskId) {
		try {
			String queryString = "from TblMiniJob as model where 1=1  and fminiJobStatus="+TblMiniJob.STATUS_HANDLING+" " +
					" and fjobId in(select fsn from TblJobBase where ftaskId="+taskId+" ) ";
			Query queryObject = getSession().createQuery(queryString);
			//queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblMiniJob instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblMiniJob as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public int getHandlingMiniJobCount(TblJobBase job){
		
		String sql=" select count(*) from TblMiniJob where 1=1 " +
				"and fminiJobStatus="+TblJobBase.STATUS_HANDLING+" and fjobId="+job.getFsn();
		Query queryObject = getSession().createQuery(sql);
		List o=queryObject.list();
		if(o==null || o.size()==0){
			return 0;
		}
		
		return new Integer(o.get(0)+"");
	}

	public static TblMiniJobDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TblMiniJobDAO) ctx.getBean("TblMiniJobDAO");
	}
}