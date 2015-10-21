package com.inbuild.audit.wordflow.enger.application.impl;

import com.maven.flow.editor.extend.interfaces.IFinishCondiction;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class OnlyPinShenZhongXinFinish implements IFinishCondiction{

	public String getFlowConditionName() {
		// TODO Auto-generated method stub
		return "评审中心的人完成即算完成";
	}

	public boolean isSynchronizedOk(TblProcess process, TblJobBase jobBase) {
		// TODO Auto-generated method stub
		return false;
	}

}
