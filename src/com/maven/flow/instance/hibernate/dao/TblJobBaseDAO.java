package com.maven.flow.instance.hibernate.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.maven.flow.hibernate.dao.BaseHibernateDAO;

/**
 * Data access object (DAO) for domain model class TblJobBase.
 * @see com.maven.flow.instance.hibernate.dao.TblJobBase
 * @author MyEclipse - Hibernate Tools
 */
public class TblJobBaseDAO extends BaseHibernateDAO  {

    private static final Log log = LogFactory.getLog(TblJobBaseDAO.class);

	protected void initDao() {
		//do nothing
	}
    
    public void save(TblJobBase transientInstance) {
        log.debug("saving TblJobBase instance");
        try {
        	Transaction tran=getSession().beginTransaction();
        	getSession().save(transientInstance);
        	tran.commit();
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void update(TblJobBase transientInstance) {
        log.debug("update TblJobBase instance");
        try {
        	Transaction tran=getSession().beginTransaction();
        	getSession().update(transientInstance);
        	tran.commit();
            log.debug("update successful");
        } catch (RuntimeException re) {
            log.error("update failed", re);
            throw re;
        }
    }
    
	public void delete(TblJobBase persistentInstance) {
        log.debug("deleting TblJobBase instance");
        try {
        	Transaction tran=getSession().beginTransaction();
        	getSession().delete(persistentInstance);
        	tran.commit();
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
	public List findByMap(Map map){
		
		String sql=" from TblJobBase where 1=1 " +
				"  ";
		if(map.containsKey("jobStatus")){
			sql+=" and fjobStatus="+map.get("jobStatus");
		}
		
		if(map.containsKey("processId")){
			sql+=" and fprocessId="+map.get("processId");
		}
		
		if(map.containsKey("taskId")){
			sql+=" and ftaskId="+map.get("taskId");
		}
		
		Query queryObject = getSession().createQuery(sql);
		
		return queryObject.list();
	}
    
    public TblJobBase findById( java.lang.Long id) {
        log.debug("getting TblJobBase instance with id: " + id);
        try {
            TblJobBase instance = (TblJobBase) getSession()
                    .get("com.maven.flow.instance.hibernate.dao.TblJobBase", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findUnHandleJobBase(Long taskId,Long jobId){
    	
    	String sql=" from TblJobBase where fsn!="+jobId+" " +
    			"and fjobStatus="+TblJobBase.STATUS_HANDLING+" and ftaskId="+taskId;
    	Query queryObject = getSession().createQuery(sql);
    	return queryObject.list();
    }

    public List findHandlingJob(Long taskId){
    	
    	String sql=" from TblJobBase where  " +
    			" fjobStatus="+TblJobBase.STATUS_HANDLING+" and ftaskId="+taskId;
    	Query queryObject = getSession().createQuery(sql);
    	
    	
    	return queryObject.list();
    }
    
    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding TblJobBase instance with property: " + propertyName
            + ", value: " + value);
		log.debug("finding TblApplication instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TblJobBase as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public static TblJobBaseDAO getFromApplicationContext(ApplicationContext ctx) {
    	return (TblJobBaseDAO) ctx.getBean("TblJobBaseDAO");
	}
}