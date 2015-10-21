package com.inbuild.audit.wordflow.enger.application.impl;

import java.util.Map;

import com.maven.flow.editor.extend.interfaces.IBeforeHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public class BeforeFuZhuRenShenHe  implements IBeforeHandleClass{

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "中心副主任审核之前";
	}
	
	/**
	 * 
	 * 更新复审初审金额，
	 * 
	 */
	public Map handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub

		
		return null;
	}

}
