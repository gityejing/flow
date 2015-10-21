package com.maven.flow.instance.service;

import java.util.List;

import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblJobBaseDAO;
import com.maven.flow.instance.hibernate.dao.TblMiniJob;
import com.maven.flow.instance.hibernate.dao.TblMiniJobDAO;
import com.maven.flow.instance.hibernate.dao.TblTaskBaseDAO;

public class MiniJobService {
	
	TblMiniJobDAO miniJobDao = new TblMiniJobDAO();
	
	TblJobBaseDAO jobBaseDao = new TblJobBaseDAO();
	
	TblTaskBaseDAO taskBaseDao = new TblTaskBaseDAO();
	
	public void save(TblMiniJob transientInstance) {
		miniJobDao.save(transientInstance);
	}

	public void update(TblMiniJob transientInstance) {
		miniJobDao.update(transientInstance);
	}

	public void delete(TblMiniJob persistentInstance) {
		miniJobDao.delete(persistentInstance);
	}

	public TblMiniJob findById(java.lang.Long id) {
		return miniJobDao.findById(id);
	}

	public List findByProperty(String propertyName, Object value) {
		return miniJobDao.findByProperty(propertyName, value);
	}
}
