package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblAppRouteDAO;

public class RouteService {

	private TblAppRouteDAO routeDAO=new TblAppRouteDAO();
	
	public List getRouteByAppId(Integer appId)
	{
		return routeDAO.findByAppId(appId);
	}
	
	public void deleteRouteByAppId(Integer appid)
	{
		routeDAO.deleteRouteByAppId(appid);
	}
	public void save(TblAppRoute route)
	{
		//routeDAO.attachDirty(route);
		routeDAO.save(route);
	}
	
}
