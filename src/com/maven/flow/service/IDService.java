package com.maven.flow.service;

import com.maven.flow.hibernate.dao.TblId;
import com.maven.flow.hibernate.dao.TblIdDAO;

public class IDService {

	private TblIdDAO idDao =new TblIdDAO();
	
	public  int getApplicationID()
	{
		return  getAutoIncValue("TblApplication","AppID");
	}
	public  int getProcessID()
	{
		return  getAutoIncValue("tblProcess","ProcessID");
	}
	public  int getRouteID()
	{
		return  getAutoIncValue("tblAppRoute","RouteID");
	}
	
	public  int getProcessPeopleId()
	{
		return  getAutoIncValue("tblProcessPeople","fsn");
	}
	
	public  int getProcessAccessID()
	{
		return  getAutoIncValue("tblProcessAccess","ProcessAccessRecordID");
	}
	
	
	public  int getTaskBaseId()
	{
		return  getAutoIncValue("tblTaskBase","Fsn");
	}
	
	public  int getMiniJobId()
	{
		return  getAutoIncValue("tblMiniJob","Fsn");
	}
	
	public  int getJobBaseId()
	{
		return  getAutoIncValue("tblJobBase","Fsn");
	}
	
	
	private int  getAutoIncValue(String tableName,String fieldName)
	{
		
		Integer fvalue=new Integer(1);
		TblId tblid=idDao.findByTableField(tableName, fieldName);
		if (tblid==null)
		{
			//若没有则写入一个值为1
			tblid=new TblId();
			tblid.setTableName(tableName);
			tblid.setFieldName(fieldName);
			tblid.setFvalue(new Integer(1));
			idDao.attachDirty(tblid);
		}
		else
		{
			//若有，则值加1，再回写进数据库
			fvalue=tblid.getFvalue();
			fvalue=new Integer(fvalue.intValue()+1);
			tblid.setFvalue(fvalue);
			idDao.attachDirty(tblid);
		}
		
		return fvalue.intValue();
	}
	
	
	
	
	public static void main(String[] args)
	{
		IDService s=new IDService();
		int i=s.getApplicationID();
		
		
	}
	
}
