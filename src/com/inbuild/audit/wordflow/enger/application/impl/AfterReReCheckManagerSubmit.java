package com.inbuild.audit.wordflow.enger.application.impl;

import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;







public class AfterReReCheckManagerSubmit implements IAfterHandleClass{

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "复审科负责人审核完成后";
	}

	public void handle(TblProcess process, TblJobBase jobBase) throws Exception {


	}

}
