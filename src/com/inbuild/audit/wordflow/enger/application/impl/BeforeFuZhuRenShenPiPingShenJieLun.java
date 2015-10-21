package com.inbuild.audit.wordflow.enger.application.impl;


import java.util.Map;

import com.maven.flow.editor.extend.interfaces.IBeforeHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class BeforeFuZhuRenShenPiPingShenJieLun  implements IBeforeHandleClass  {

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "副主任审批评审结论之前";
	}

	public Map handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	
}
