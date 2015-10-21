package com.maven.flow.editor.extend.interfaces;


import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public interface IAfterHandleClass {

	String getHandleClassName();

	public void handle(TblProcess process, TblJobBase jobBase) throws Exception;
	
}
