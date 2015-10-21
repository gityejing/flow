package com.maven.flow.instance.service;

import java.util.List;

import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblJobBaseDAO;
import com.maven.flow.instance.hibernate.dao.TblMiniJobDAO;
import com.maven.flow.instance.hibernate.dao.TblTaskBase;
import com.maven.flow.instance.hibernate.dao.TblTaskBaseDAO;

public class TaskBaseService {
	TblTaskBaseDAO taskBaseDao = new TblTaskBaseDAO();

	TblMiniJobDAO miniJobDao = new TblMiniJobDAO();
	
	TblJobBaseDAO jobBaseDao = new TblJobBaseDAO();
	
	public void save(TblTaskBase transientInstance) {
		taskBaseDao.save(transientInstance);
	}

	public void update(TblTaskBase transientInstance) {
		taskBaseDao.update(transientInstance);
	}

	public void delete(TblTaskBase persistentInstance) {
		taskBaseDao.delete(persistentInstance);
	}

	public TblTaskBase findById(java.lang.Long id) {
		return taskBaseDao.findById(id);
	}

	public List findByProperty(String propertyName, Object value) {
		return taskBaseDao.findByProperty(propertyName, value);
	}
}
