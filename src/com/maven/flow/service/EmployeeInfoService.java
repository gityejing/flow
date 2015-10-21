package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblEmployeeInfo;
import com.maven.flow.hibernate.dao.TblEmployeeInfoDAO;

public class EmployeeInfoService {

	private TblEmployeeInfoDAO employeeDAO = new TblEmployeeInfoDAO();

	public TblEmployeeInfo getEmployeeInfo(String id) {
		TblEmployeeInfo employeeInfo = null;
		List list = employeeDAO.findByProperty("floginName", id);
		//System.out.println("list.size()=="+list.size());
		
		if (list.size() > 0) {

			employeeInfo = (TblEmployeeInfo) list.get(0);
		}
		//System.out.println("employeeInfo=="+employeeInfo);
		return employeeInfo;
	}
	
	
	public List findByProperty(String propertyName,Object value){
		return employeeDAO.findByProperty(propertyName, value);
	}
	
	public TblEmployeeInfo findById(Long id) {
		TblEmployeeInfo employeeInfo = employeeDAO.findById(id);
		
		return employeeInfo;
	}

	public List getAllEmployeeInfo() {
		List employeeInfo = employeeDAO.findAll();
		return employeeInfo;
	}
}
