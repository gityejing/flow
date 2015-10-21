package com.inbuild.audit.wordflow.enger.application.impl;


import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;


public class AfterJinDuKuanGuiDang implements IAfterHandleClass{

	public String getHandleClassName() {
		// TODO Auto-generated method stub
		return "进度款归档后置处理";
	}


	
	
	
	/**
	 * 
	 * 归档仅处理进入历史项目库.
	 * 项目归档时，应提醒各评分人“项目已归档，请填写质量考核评分表”
	 * 
	 */
	public void handle(TblProcess process, TblJobBase jobBase) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}

