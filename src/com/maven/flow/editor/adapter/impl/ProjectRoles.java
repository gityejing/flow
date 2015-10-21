package com.maven.flow.editor.adapter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.hibernate.dao.TblEmployeeInfo;

public class ProjectRoles  implements KeyValueObjectAdapter {
	
	public static final String RECEVIER="1";//�ռ�Ա
	public static final String RERECHECK_MANAGER="2";//����Ƹ�����
	public static final String CNTER_ZHUREN="3";//��������
	public static final String CNTER_FUZHUREN="4";//���ĸ�����
	public static final String CHECK_MANAGER="5";//ҵ��Ƹ�����
	public static final String CHECKERS="6";//�����
	public static final String RECHECKERS="7";//������
	public static final String RERECHECKERS="8";//������
	public static final String ZONGHE_MANAGER="9";//�ۺϿƸ�����
	public static final String GUIDANGREN="12";//��Ŀ�鵵Ա
	public static final String ZHONGJIE="10";//�н����
	public static final String OBJECT_CREATER="11";//���̷�����
	public static final String CHECKANDHUIBANMANAGERS="13";//ҵ��ƺͻ��Ƹ�����
	public static final String HUIBAN_MANAGER="14";//���Ƹ�����
	public static final String ONEOF_CENTER_FUZHUREN="15";//���зֹ��쵼�е�һ��
	public static final String ONEOF_CENTER_ZHUREN="16";//�������������е�һ��
	public static final String SHEN_HE_ZHUAN_GUAN_YUAN="17";//���ר��Ա
	public static final String COOPERATORNAME="18";//�н������λ
	
	public KeyValueObject[] getKeyValueObjects() {
		try {
			
			List list=new ArrayList();
			list.add(new KeyValueObjectUtil(RECEVIER, "�ռ�Ա"));
			list.add(new KeyValueObjectUtil(RERECHECK_MANAGER, "����Ƹ�����"));
			list.add(new KeyValueObjectUtil(CNTER_ZHUREN, "��������"));
			list.add(new KeyValueObjectUtil(CNTER_FUZHUREN, "���ĸ�����"));
			list.add(new KeyValueObjectUtil(CHECK_MANAGER, "ҵ��Ƹ�����"));
			list.add(new KeyValueObjectUtil(CHECKERS, "�����"));
			list.add(new KeyValueObjectUtil(RECHECKERS, "������"));
			list.add(new KeyValueObjectUtil(RERECHECKERS, "������"));
			list.add(new KeyValueObjectUtil(ZONGHE_MANAGER, "�ۺϿƸ�����"));
			list.add(new KeyValueObjectUtil(GUIDANGREN, "��Ŀ�鵵Ա"));
			list.add(new KeyValueObjectUtil(ZHONGJIE, "�н����"));
			list.add(new KeyValueObjectUtil(OBJECT_CREATER, "���̷�����"));
			list.add(new KeyValueObjectUtil(CHECKANDHUIBANMANAGERS, "ҵ��ƺͻ��Ƹ�����"));
			list.add(new KeyValueObjectUtil(HUIBAN_MANAGER, "���Ƹ�����"));
			list.add(new KeyValueObjectUtil(ONEOF_CENTER_FUZHUREN, "���зֹ��쵼�е�һ��"));
			list.add(new KeyValueObjectUtil(ONEOF_CENTER_ZHUREN, "�������������е�һ��"));
			list.add(new KeyValueObjectUtil(SHEN_HE_ZHUAN_GUAN_YUAN, "���ר��Ա"));
			list.add(new KeyValueObjectUtil(COOPERATORNAME, "�н������λ"));
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
