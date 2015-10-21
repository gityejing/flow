package com.maven.flow.hibernate.dao;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Data access object (DAO) for domain model class TblEmployeeInfo.
 * @see com.maven.flow.hibernate.dao.TblEmployeeInfo
 * @author MyEclipse - Hibernate Tools
 */
public class TblEmployeeInfoDAO extends BaseHibernateDAO {

    private static final Log log = LogFactory.getLog(TblEmployeeInfoDAO.class);

	protected void initDao() {
		//do nothing
	}
    
    public void save(TblEmployeeInfo transientInstance) {
        log.debug("saving TblEmployeeInfo instance");
        try {
        	
			Transaction tran=getSession().beginTransaction();
			getSession().save(transientInstance);
			tran.commit();
        	
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void update(TblEmployeeInfo transientInstance) {
        log.debug("update TblEmployeeInfo instance");
        try {
        	
			Transaction tran=getSession().beginTransaction();
			getSession().update(transientInstance);
			tran.commit();
        	
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    
    
    
	public void delete(TblEmployeeInfo persistentInstance) {
        log.debug("deleting TblEmployeeInfo instance");
        try {
        	Transaction tran=getSession().beginTransaction();
        	getSession().delete(persistentInstance);
            log.debug("delete successful");
        	tran.commit();
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TblEmployeeInfo findById( java.lang.Long id) {
        log.debug("getting TblEmployeeInfo instance with id: " + id);
        try {
            TblEmployeeInfo instance = (TblEmployeeInfo) getSession()
                    .get("com.maven.flow.hibernate.dao.TblEmployeeInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findAll( ) {
       // log.debug("getting TblEmployeeInfo instance with id: " + id);
        try {
            	String queryString = "from TblEmployeeInfo ";
            	return getSession().createQuery(queryString).list();
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    
    public List findByProperty(String propertyName, Object value) {
		log.debug("finding TblEmployeeInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblEmployeeInfo as model where model."
					+ propertyName + "= ? and model.fisDel=0 ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
      
	}

    public TblEmployeeInfo merge(TblEmployeeInfo detachedInstance) {
        log.debug("merging TblEmployeeInfo instance");
        try {
            TblEmployeeInfo result = (TblEmployeeInfo) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TblEmployeeInfo instance) {
        log.debug("attaching dirty TblEmployeeInfo instance");
        try {
        	getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TblEmployeeInfo instance) {
        log.debug("attaching clean TblEmployeeInfo instance");
        try {
        	getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

	public static TblEmployeeInfoDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (TblEmployeeInfoDAO) ctx.getBean("TblEmployeeInfoDAO");
	}
}