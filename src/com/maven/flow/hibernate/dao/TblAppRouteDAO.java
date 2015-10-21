package com.maven.flow.hibernate.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class TblAppRoute.
 * 
 * @see com.maven.flow.hibernate.dao.TblAppRoute
 * @author MyEclipse Persistence Tools
 */

public class TblAppRouteDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(TblAppRouteDAO.class);

	// property constants
	public static final String APP_ID = "appId";

	public static final String ROUTE_NAME = "routeName";

	public static final String ROUTE_DESC = "routeDesc";

	public static final String ROUTE_DISPLAY_TEXT = "routeDisplayText";

	public static final String ROUTE_BEGIN_PROCESS = "routeBeginProcess";

	public static final String ROUTE_END_PROCESS = "routeEndProcess";

	public static final String ROUTE_POSITION_INFO = "routePositionInfo";

	public static final String SUB_FLOW_PATH = "subFlowPath";

	public void save(TblAppRoute transientInstance) {
		log.debug("saving TblAppRoute instance");
		try {
			Transaction tran=getSession().beginTransaction();
			
			getSession().saveOrUpdate(transientInstance);
			tran.commit();
			System.out.println("saving the TblAppRoute..........");
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			
			throw re;
		}
	}

	public void delete(TblAppRoute persistentInstance) {
		log.debug("deleting TblAppRoute instance");
		try {
			Transaction tran=getSession().beginTransaction();
			//this.getSession().flush();
			getSession().delete(persistentInstance);
			//this.getSession().close();
			tran.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public void deleteRouteByAppId(Integer appId) {
		log.debug("deleting TblAppRoute instance");
		try {
			Transaction tran=getSession().beginTransaction();
			
			Query query=getSession().createQuery("delete from TblAppRoute where appId="+appId);
			query.executeUpdate();
			
			/*
			List list=this.findByProperty("appId", appId);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					TblAppRoute ar=(TblAppRoute)list.get(i);
					this.delete(ar);
				}
			}
			*/
			//this.getSession().flush();
			
			tran.commit();
			this.getSession().close();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	public TblAppRoute findById(java.lang.Integer id) {
		log.debug("getting TblAppRoute instance with id: " + id);
		try {
			TblAppRoute instance = (TblAppRoute) getSession().get(
					"com.maven.flow.hibernate.dao.TblAppRoute", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TblAppRoute instance) {
		log.debug("finding TblAppRoute instance by example");
		try {
			List results = getSession().createCriteria(
					"com.maven.flow.hibernate.dao.TblAppRoute").add(
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
		log.debug("finding TblAppRoute instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TblAppRoute as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAppId(Object appId) {
		return findByProperty(APP_ID, appId);
	}

	public List findByRouteName(Object routeName) {
		return findByProperty(ROUTE_NAME, routeName);
	}

	public List findByRouteDesc(Object routeDesc) {
		return findByProperty(ROUTE_DESC, routeDesc);
	}

	public List findByRouteDisplayText(Object routeDisplayText) {
		return findByProperty(ROUTE_DISPLAY_TEXT, routeDisplayText);
	}

	public List findByRouteBeginProcess(Object routeBeginProcess) {
		return findByProperty(ROUTE_BEGIN_PROCESS, routeBeginProcess);
	}

	public List findByRouteEndProcess(Object routeEndProcess) {
		return findByProperty(ROUTE_END_PROCESS, routeEndProcess);
	}

	public List findByRoutePositionInfo(Object routePositionInfo) {
		return findByProperty(ROUTE_POSITION_INFO, routePositionInfo);
	}

	public List findBySubFlowPath(Object subFlowPath) {
		return findByProperty(SUB_FLOW_PATH, subFlowPath);
	}

	public List findAll() {
		log.debug("finding all TblAppRoute instances");
		try {
			String queryString = "from TblAppRoute";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	
	public TblAppRoute findAppRouteFromBeginProcessAndEndProcess(long startProcessId,long endProcessId)
	{
		
		String sql=" from TblAppRoute where routeBeginProcess="+startProcessId+" " +
				" and routeEndProcess="+endProcessId;
		
		Query queryObject = getSession().createQuery(sql);
		List l=queryObject.list();
		if(l==null || l.size()<1){
			return null;
		}
		return (TblAppRoute)l.get(0);
	}
	public TblAppRoute merge(TblAppRoute detachedInstance) {
		log.debug("merging TblAppRoute instance");
		try {
			TblAppRoute result = (TblAppRoute) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TblAppRoute instance) {
		log.debug("attaching dirty TblAppRoute instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			re.printStackTrace();
			throw re;
		}
	}

	public void attachClean(TblAppRoute instance) {
		log.debug("attaching clean TblAppRoute instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}