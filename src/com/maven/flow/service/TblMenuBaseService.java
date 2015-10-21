package com.maven.flow.service;

import java.util.List;

import com.maven.flow.hibernate.dao.TblMenuBaseDAO;
import com.maven.flow.hibernate.dao.TblRoleDAO;

public class TblMenuBaseService {
	private TblMenuBaseDAO tblMenuBaseDAO=new TblMenuBaseDAO();
	
	public  List findAll()
	{
		return tblMenuBaseDAO.findAll();
	}
}
