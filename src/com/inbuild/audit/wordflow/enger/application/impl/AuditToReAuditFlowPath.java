package com.inbuild.audit.wordflow.enger.application.impl;

import com.maven.flow.editor.extend.interfaces.IFlowPath;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class AuditToReAuditFlowPath implements IFlowPath{

	public String getFlowConditionName() {
		// TODO Auto-generated method stub
		return "��˵�����Ҫ��ɹؼ���";
	}

	public boolean isSynchronizedOk(TblProcess process, TblJobBase jobBase) {
		// TODO Auto-generated method stub
		return false;
	}

}
