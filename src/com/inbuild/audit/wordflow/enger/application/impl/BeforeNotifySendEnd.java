package com.inbuild.audit.wordflow.enger.application.impl;

import java.util.HashMap;
import java.util.Map;

import com.maven.flow.editor.extend.interfaces.IBeforeHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public class BeforeNotifySendEnd  implements IBeforeHandleClass{

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "发出补充资料通知书流程结束前";
	}

	public Map handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub

		
		
		
		
		
		return new HashMap();
	}
	
}
