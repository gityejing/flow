package com.inbuild.audit.wordflow.enger.application.impl;

import com.maven.flow.editor.extend.interfaces.IFinishCondiction;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class OnlyCenterShenHeFinish implements IFinishCondiction {

	public String getFlowConditionName() {
		// TODO Auto-generated method stub
		return "中心审核不需要稽核人评分";
	}

	public boolean isSynchronizedOk(TblProcess process, TblJobBase jobBase) {
		// TODO Auto-generated method stub
		return false;
	}
}
