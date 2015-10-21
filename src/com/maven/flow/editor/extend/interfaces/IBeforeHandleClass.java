package com.maven.flow.editor.extend.interfaces;

import java.util.Map;

import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public interface IBeforeHandleClass {
	
	String getHandleClassName();
	
	public Map handle(TblProcess process, TblJobBase jobBase) throws Exception;
	

}
