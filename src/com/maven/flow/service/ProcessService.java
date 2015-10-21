package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessDAO;

public class ProcessService {

	private TblProcessDAO processDAO = new TblProcessDAO();
	
	public List getProcessByAppId(Integer appId)
	{
		return processDAO.findByAppId(appId);
	}
	
	public TblProcess findByPrimaryKey(Integer id){
		return processDAO.findById(id);
		
	}
	
	
	public int save(TblProcess process) {
		int result = -1;
		try {

			processDAO.save(process);
			result = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	
	public int saveOrUpdate(TblProcess process) {
		int result = -1;
		try {

			processDAO.saveOrUpdate(process);
			result = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	public void deleteAllProcessByAppId(Integer appId)
	{
		processDAO.deleteAllProcessByAppId(appId);
	}
}
