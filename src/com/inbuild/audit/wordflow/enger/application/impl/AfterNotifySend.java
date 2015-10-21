package com.inbuild.audit.wordflow.enger.application.impl;


import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public class AfterNotifySend implements IAfterHandleClass{

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "发出补充资料通知后处理";
	}

	public void handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub
		
	}



	
}
