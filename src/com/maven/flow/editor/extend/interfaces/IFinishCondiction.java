package com.maven.flow.editor.extend.interfaces;

import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public interface IFinishCondiction {
	public String getFlowConditionName();
	
	public boolean isSynchronizedOk(TblProcess process, TblJobBase jobBase);
}
