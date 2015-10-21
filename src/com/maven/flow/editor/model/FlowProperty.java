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
	 * 属性类型：整数
	 */
	public static final int INT = 0;

	/**
	 * 属性类型：字符串
	 */
	public static final int STRING = 1;

	/**
	 * 属性类型：布尔型
	 */
	public static final int BOOLEAN = 2;

	/**
	 * 属性类型：选项
	 */
	public static final int OPTION = 3;

	/**
	 * 属性类型：浮点型
	 */
	public static final int FLOAT = 4;

	/**
	 * 属性类型：列表
	 */
	public static final int LIST = 5;

	/**
	 * 属性类型：自定义类型
	 * 如果属性是这种类型，需要提供自定义的编辑界面
	 */
	public static final int CUSTOMIZE = 6;
	
	public static final int BIGSTRING = 7;

	private String name = "";//属性名称，通常对应JavaBean的属性名称

	private String description = ""; //属性显示名称

	private int type = -1;//属性类型

	private boolean editAble = true;//该属性是否可以进行编辑
	
	private int group = 0;//组名，属性将分组显示
	
	private int order = 0;//在组内的排序
	
	private boolean required = false;//是否必需的属性
	
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
	 * 获取一个自定义属性的适配器
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
	 * @return 返回 group。
	 */
	public int getGroup() {
		return group;
	}

	/**
	 * @param group 要设置的 group。
	 */
	public void setGroup(int group) {
		this.group = group;
	}

	/**
	 * @return 返回 order。
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order 要设置的 order。
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * @return 返回 required。
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required 要设置的 required。
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

}
