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
	public static final String BUILD_UNIT="23";//建设单位
    public static final String ENTRUST_UNIT="24";//委托处室(业务处室)
    public static final String MANAGER_DEP="25";//主管部门
	
	// 以下用来判断建设单位，主管部门和业务处室的经办人和复核人
	public static final String BUILD_UNIT_JBR = "100";// 建设单位
	public static final String BUILD_UNIT_FUHE = "101";
	public static final String MANAGER_DEP_JBR = "102";// 主管部门
	public static final String MANAGER_DEP_FUHE = "103";// 主管部门
	public static final String ENTRUST_UNIT_JBR = "104";// 业务处室
	public static final String ENTRUST_UNIT_FUHE = "105";// 业务处室
	
	// 应急流程步骤，处理人用到的判断
	// 工程结算
	public static final String JIESUAN_3000 = "JIESUAN_3000"; // 分配审核复核复审人
	public static final String JIESUAN_3001 = "JIESUAN_3001"; // 审核
	public static final String JIESUAN_3002 = "JIESUAN_3002"; // 稽核复审
	public static final String JIESUAN_3003 = "JIESUAN_3003"; // 评审结论
	public static final String JIESUAN_3004 = "JIESUAN_3004"; // 稽核复审修正报告及核高
	
	// 竣工财务决算
	public static final String JUESUAN_4000 = "JUESUAN_4000";
	public static final String JUESUAN_4001 = "JUESUAN_4001";
	public static final String JUESUAN_4002 = "JUESUAN_4002";
	public static final String JUESUAN_4003 = "JUESUAN_4003";
	public static final String JUESUAN_4004 = "JUESUAN_4004";
	public static final String JUESUAN_4005 = "JUESUAN_4005";
	public static final String JUESUAN_4006 = "JUESUAN_4006";
	
	// 概预估
	public static final String GAIYUGU_5000 = "GAIYUGU_5000";	
	public static final String GAIYUGU_5001 = "GAIYUGU_5001";
	public static final String GAIYUGU_5002 = "GAIYUGU_5002";
	public static final String GAIYUGU_5003 = "GAIYUGU_5003";
	public static final String GAIYUGU_5004 = "GAIYUGU_5004";

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
			
			list.add(new KeyValueObjectUtil(BUILD_UNIT, "建设单位"));
			list.add(new KeyValueObjectUtil(ENTRUST_UNIT, "业务处室"));
		    list.add(new KeyValueObjectUtil(MANAGER_DEP, "主管部门"));
			
			// 以下用来判断建设单位，主管部门和业务处室的经办人和复核人
			list.add(new KeyValueObjectUtil(BUILD_UNIT_JBR, "建设单位经办人"));
			list.add(new KeyValueObjectUtil(BUILD_UNIT_FUHE, "建设单位复核人"));
			list.add(new KeyValueObjectUtil(MANAGER_DEP_JBR, "主管部门经办人"));
			list.add(new KeyValueObjectUtil(ENTRUST_UNIT_JBR, "主管部门复核人"));		
			list.add(new KeyValueObjectUtil(BUILD_UNIT_JBR, "业务处室经办人"));
			list.add(new KeyValueObjectUtil(ENTRUST_UNIT_FUHE, "业务处室复核人"));
			
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
