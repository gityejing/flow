package com.inbuild.audit.wordflow.enger.application.impl;

import com.maven.flow.editor.extend.interfaces.IFlowPath;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class CheckDepartMeneToReCheckDepartMentFlowPath implements IFlowPath{

	public String getFlowConditionName() {
		// TODO Auto-generated method stub
		return "审核科室到复审科室条件检查";
	}

	public boolean isSynchronizedOk(TblProcess process, TblJobBase jobBase) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
