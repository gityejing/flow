/**
 * @(#)ElementProperty.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

import com.maven.flow.editor.adapter.CustomizePropertyAdapter;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class FlowProperty implements Serializable {

	/**
	 * �������ͣ�����
	 */
	public static final int INT = 0;

	/**
	 * �������ͣ��ַ���
	 */
	public static final int STRING = 1;

	/**
	 * �������ͣ�������
	 */
	public static final int BOOLEAN = 2;

	/**
	 * �������ͣ�ѡ��
	 */
	public static final int OPTION = 3;

	/**
	 * �������ͣ�������
	 */
	public static final int FLOAT = 4;

	/**
	 * �������ͣ��б�
	 */
	public static final int LIST = 5;

	/**
	 * �������ͣ��Զ�������
	 * ����������������ͣ���Ҫ�ṩ�Զ���ı༭����
	 */
	public static final int CUSTOMIZE = 6;
	
	public static final int BIGSTRING = 7;

	private String name = "";//�������ƣ�ͨ����ӦJavaBean����������

	private String description = ""; //������ʾ����

	private int type = -1;//��������

	private boolean editAble = true;//�������Ƿ���Խ��б༭
	
	private int group = 0;//���������Խ�������ʾ
	
	private int order = 0;//�����ڵ�����
	
	private boolean required = false;//�Ƿ���������
	
	private PropertyObserver observer = null;
	
	public FlowProperty(PropertyObserver observer) {
		this.observer = observer;
	}

	public FlowProperty(PropertyObserver observer, String name,
			String description, int type, boolean editAble) {
		this(observer, name, description, type, editAble, 0, 0);
	}

	public FlowProperty(PropertyObserver observer, String name,
			String description, int type, boolean editAble, int group, int order) {
		this.observer = observer;
		this.name = name;
		this.description = description;
		this.type = type;
		this.editAble = editAble;
		this.group = group;
		this.order = order;
	}

	public FlowProperty(PropertyObserver observer, String name,
			String description, int type) {
		this(observer, name, description, type, true);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Object getValue() {
		return this.observer.getValue(this.name);
	}

	public void setValue(Object value) {
		this.observer.setValue(this.name, value);
	}

	public Object[] getListValues() {
		return this.observer.getListValues(this.name);
	}

	/**
	 * ��ȡһ���Զ������Ե�������
	 * @return
	 */
	public CustomizePropertyAdapter getCustomizeAdapter() {
		return this.observer.getCustomizeAdapter(this.getName());
	}

	public boolean isEditAble() {
		return editAble;
	}

	public void setEditAble(boolean editAble) {
		this.editAble = editAble;
	}

	/**
	 * @return ���� group��
	 */
	public int getGroup() {
		return group;
	}

	/**
	 * @param group Ҫ���õ� group��
	 */
	public void setGroup(int group) {
		this.group = group;
	}

	/**
	 * @return ���� order��
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order Ҫ���õ� order��
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return ���� required��
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required Ҫ���õ� required��
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

}
