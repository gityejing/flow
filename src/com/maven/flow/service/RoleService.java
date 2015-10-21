package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblRoleDAO;

public class RoleService {

	private TblRoleDAO roleDAO=new TblRoleDAO();
	
	public  List getRoleList()
	{
		return roleDAO.findAll();
	}
}
