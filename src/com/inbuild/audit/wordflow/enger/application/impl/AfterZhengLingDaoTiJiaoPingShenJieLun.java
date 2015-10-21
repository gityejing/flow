package com.inbuild.audit.wordflow.enger.application.impl;


import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;

public class AfterZhengLingDaoTiJiaoPingShenJieLun implements IAfterHandleClass {

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "正领导提交评审结论之后";
	}

	/**
	 * 
	 * 正领导提交评审结论，无论向前，还是向后，都产生一条历史记录。
	 * 
	 * 
	 */
	public void handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
