package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblProcessPeople;
import com.maven.flow.hibernate.dao.TblProcessPeopleDAO;

public class TblProcessPeopleService {

	TblProcessPeopleDAO dao = new TblProcessPeopleDAO();

	public void save(TblProcessPeople transientInstance) {
		dao.save(transientInstance);
	}	
	
	public void update(TblProcessPeople transientInstance) {
		dao.update(transientInstance);
	}

	public void delete(TblProcessPeople persistentInstance) {
		dao.delete(persistentInstance);
	}

	public void deleteAllProcessByProcessId(Integer processId) {
		dao.deleteAllProcessByProcessId(processId);
	}

	public TblProcessPeople findById(java.lang.Integer id) {
		return dao.findById(id);
	}

	public List findByProperty(String propertyName, Object value) {
		return dao.findByProperty(propertyName, value);
	}
}
