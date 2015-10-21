/**
 * @(#)FlowObject.java 2007-5-30
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.maven.flow.editor.adapter.CustomizePropertyAdapter;
import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.service.IDService;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-30
 * @since JDK1.4
 */

public abstract class FlowElementObject implements Serializable,
		PropertyObserver {

	public static final long serialVersionUID = 6766186124614064801L;
	
	private String name = "����Ԫ��";
	
	private int    id=0;
	//��ʼ��λ�ã�����
	private BigDecimal Left=new BigDecimal(10);

	private BigDecimal Top=new BigDecimal(10);

	private BigDecimal Width=new BigDecimal(80);

	private BigDecimal Height=new BigDecimal(40);

	protected List properties = new ArrayList();

	protected HashMap listValues = new HashMap();// �������е��б�ֵ
	
	

	public FlowElementObject()
	{
		//System.out.println("===="+this.getClass().getName());
	}
	{
		this.addProperty("name", "����", FlowProperty.STRING, 0, 0, true);
		//		
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ��ȡ���е�����
	 * 
	 * @return
	 */
	public FlowProperty[] getProperties() {
		return (FlowProperty[]) this.properties
				.toArray(new FlowProperty[this.properties.size()]);
	}

	/**
	 * �������
	 * 
	 * @param property
	 */
	public void addProperty(FlowProperty property) {
		this.properties.add(property);
	}

	/**
	 * �����������
	 * 
	 */
	public void removeAllProperty() {
		this.properties.clear();
	}

	/**
	 * ɾ��ָ������
	 * 
	 * @param propertyName
	 */
	public void removeProperty(String propertyName) {
		FlowProperty p = this.getProperty(propertyName);
		if (p != null) {
			this.properties.remove(p);
		}
	}

	/**
	 * �������
	 * 
	 * @param name
	 * @param desc
	 * @param type
	 */
	public void addProperty(String name, String desc, int type, int group,
			int order) {
		this.addProperty(name, desc, type, true, group, order);
	}

	public void addProperty(String name, String desc, int type,
			boolean editAble, int group, int order) {
		this.addProperty(name, desc, type, editAble, group, order, false);
	}

	/**
	 * �������
	 * 
	 * @param name
	 * @param desc
	 * @param type
	 * @param editAble
	 * @param required
	 * @param group
	 * @param order
	 */
	public void addProperty(String name, String desc, int type,
			boolean editAble, int group, int order, boolean required) {
		FlowProperty p = new FlowProperty(this, name, desc, type, editAble,
				group, order);
		p.setRequired(required);
		this.addProperty(p);
	}

	/**
	 * �������
	 * 
	 * @param name
	 * @param desc
	 * @param type
	 * @param group
	 * @param order
	 * @param required
	 */
	public void addProperty(String name, String desc, int type, int group,
			int order, boolean required) {
		this.addProperty(name, desc, type, true, group, order, required);
	}

	/**
	 * ��ȡָ�����Ƶ�����
	 * 
	 * @param propertyName
	 * @return
	 */
	public FlowProperty getProperty(String propertyName) {
		for (Iterator it = this.properties.iterator(); it.hasNext();) {
			FlowProperty fp = (FlowProperty) it.next();
			if (propertyName.equals(fp.getName())) {
				return fp;
			}
		}
		return null;
	}

	/**
	 * �������ԵĿɱ༭״̬
	 * 
	 * @param propertyName
	 * @param editAble
	 */
	public void updatePropertyEditAble(String propertyName, boolean editAble) {
		FlowProperty property = this.getProperty(propertyName);
		if (property == null)
			return;
		property.setEditAble(editAble);
	}

	/**
	 * ���������Ƿ����
	 * 
	 * @param propertyName
	 * @param required
	 */
	public void updatePropertyRequired(String propertyName, boolean required) {
		FlowProperty property = this.getProperty(propertyName);
		if (property == null)
			return;
		property.setRequired(required);
	}

	/**
	 * �������Ե���ʾ����
	 * 
	 * @param propertyName
	 *            ��������
	 * @param newName
	 *            ���Ե�����ʾ����
	 */
	protected void updatePropertyDescription(String propertyName, String newDesc) {
		FlowProperty property = this.getProperty(propertyName);
		if (property == null)
			return;
		property.setDescription(newDesc);
	}

	/**
	 * ��ȡͼ����Դ·��
	 * 
	 * @return
	 */
	public abstract String getIconResource();

	/**
	 * ��ȡ����ͼ��ͼƬ��Դ·��
	 * 
	 * @return
	 */
	public abstract String getImageResource();

	public Object getValue(String propertyName) {
		if ("name".equals(propertyName)) {
			return this.getName();
		} else {
			return null;
		}
	}

	public void setValue(String propertyName, Object value) {
		if ("name".equals(propertyName)) {
			this.setName(String.valueOf(value));
		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @param propertyName
	 * @param key
	 * @return
	 */
	protected String getKeyValueObjectName(String propertyName, Object key) {
		Object[] los = this.getListValues(propertyName);
		if (!(los instanceof KeyValueObject[])) {
			return null;
		}
		KeyValueObject[] kos = (KeyValueObject[]) los;
		for (int i = 0; i < kos.length; i++) {
			if (kos[i].getKey().equals(key)) {
				return kos[i].getName();
			}
		}
		return null;
	}

	protected KeyValueObject getKeyValueObject(String propertyName, Object key) {
		Object[] los = this.getListValues(propertyName);
		if (!(los instanceof KeyValueObject[])) {
			return null;
		}
		KeyValueObject[] kos = (KeyValueObject[]) los;
		for (int i = 0; i < kos.length; i++) {
			if (kos[i].getKey().equals(key)) {
				return kos[i];
			}
		}
		return null;
	}

	/**
	 * ��������ѡ���б�ֵ
	 * 
	 * @param propertyName
	 * @param values
	 */
	public void addListValue(String propertyName, Object[] values) {
		this.listValues.put(propertyName, values);
	}

	public void addListValueAdapter(String propertyName,
			KeyValueObjectAdapter adapter) {
		this.listValues.put(propertyName, adapter);
	}

	public String toString() {
		return this.getName();
	}

	public Object[] getListValues(String propertyName) {
		if (!this.listValues.containsKey(propertyName)) {
			return new Object[0];
		} else {
			Object obj = this.listValues.get(propertyName);
			if (obj instanceof KeyValueObjectAdapter)
				return ((KeyValueObjectAdapter) obj).getKeyValueObjects();
			else
				return (Object[]) this.listValues.get(propertyName);
		}
	}

	public CustomizePropertyAdapter getCustomizeAdapter(String propertyName) {
		return null;
	}

	public BigDecimal getHeight() {
		return Height;
	}

	public void setHeight(BigDecimal height) {
		Height = height;
	}

	public BigDecimal getLeft() {
		return Left;
	}

	public void setLeft(BigDecimal left) {
		Left = left;
	}

	public BigDecimal getTop() {
		return Top;
	}

	public void setTop(BigDecimal top) {
		Top = top;
	}

	public BigDecimal getWidth() {
		return Width;
	}

	public void setWidth(BigDecimal width) {
		Width = width;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}