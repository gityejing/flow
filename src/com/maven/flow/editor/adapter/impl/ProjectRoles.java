package com.maven.flow.editor.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.hibernate.dao.TblEmployeeInfo;

public class ProjectRoles  implements KeyValueObjectAdapter {
	
	public static final String RECEVIER="1";//收件员
	public static final String RERECHECK_MANAGER="2";//复审科负责人
	public static final String CNTER_ZHUREN="3";//中心主任
	public static final String CNTER_FUZHUREN="4";//中心副主任
	public static final String CHECK_MANAGER="5";//业务科负责人
	public static final String CHECKERS="6";//审核人
	public static final String RECHECKERS="7";//复核人
	public static final String RERECHECKERS="8";//复审人
	public static final String ZONGHE_MANAGER="9";//综合科负责人
	public static final String GUIDANGREN="12";//项目归档员
	public static final String ZHONGJIE="10";//中介机构
	public static final String OBJECT_CREATER="11";//流程发起人
	public static final String CHECKANDHUIBANMANAGERS="13";//业务科和会办科负责人
	public static final String HUIBAN_MANAGER="14";//会办科负责人
	public static final String ONEOF_CENTER_FUZHUREN="15";//所有分管领导中的一个
	public static final String ONEOF_CENTER_ZHUREN="16";//所有中心主任中的一个
	public static final String SHEN_HE_ZHUAN_GUAN_YUAN="17";//审核专管员
	public static final String COOPERATORNAME="18";//中介合作单位
	
	public KeyValueObject[] getKeyValueObjects() {
		try {
			
			List list=new ArrayList();
			list.add(new KeyValueObjectUtil(RECEVIER, "收件员"));
			list.add(new KeyValueObjectUtil(RERECHECK_MANAGER, "复审科负责人"));
			list.add(new KeyValueObjectUtil(CNTER_ZHUREN, "中心主任"));
			list.add(new KeyValueObjectUtil(CNTER_FUZHUREN, "中心副主任"));
			list.add(new KeyValueObjectUtil(CHECK_MANAGER, "业务科负责人"));
			list.add(new KeyValueObjectUtil(CHECKERS, "审核人"));
			list.add(new KeyValueObjectUtil(RECHECKERS, "复核人"));
			list.add(new KeyValueObjectUtil(RERECHECKERS, "复审人"));
			list.add(new KeyValueObjectUtil(ZONGHE_MANAGER, "综合科负责人"));
			list.add(new KeyValueObjectUtil(GUIDANGREN, "项目归档员"));
			list.add(new KeyValueObjectUtil(ZHONGJIE, "中介机构"));
			list.add(new KeyValueObjectUtil(OBJECT_CREATER, "流程发起人"));
			list.add(new KeyValueObjectUtil(CHECKANDHUIBANMANAGERS, "业务科和会办科负责人"));
			list.add(new KeyValueObjectUtil(HUIBAN_MANAGER, "会办科负责人"));
			list.add(new KeyValueObjectUtil(ONEOF_CENTER_FUZHUREN, "所有分管领导中的一个"));
			list.add(new KeyValueObjectUtil(ONEOF_CENTER_ZHUREN, "所有中心主任中的一个"));
			list.add(new KeyValueObjectUtil(SHEN_HE_ZHUAN_GUAN_YUAN, "审核专管员"));
			list.add(new KeyValueObjectUtil(COOPERATORNAME, "中介合作单位"));
			KeyValueObject[] kvos = new KeyValueObject[(list).size()];
			int index = 0;
			for (Iterator it = (list).iterator(); it
					.hasNext();) {
				KeyValueObjectUtil obj=(KeyValueObjectUtil)it.next();
				kvos[index] = new KeyValueObject(
						new Long(obj.getKey()),obj.getValue());
				index++;
			}
			
			return kvos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new KeyValueObject[0];
		}
	}
	
	class KeyValueObjectUtil{

		String key;
		String value;
		public KeyValueObjectUtil(){
			
		} 
		
		public KeyValueObjectUtil(String key,String value){
			this.key=key;
			this.value=value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		
		
	}
}
