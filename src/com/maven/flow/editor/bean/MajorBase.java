package com.maven.flow.editor.bean;

/**
 * @(#)MajorBase.java 2007-6-12
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 * 
 */

import java.io.Serializable;

/**
 * 封装专业种类基础数据
 * 
 * @author jumty
 * @version 1.02007-6-12
 * @since JDK1.4
 */
public class MajorBase implements Serializable {

	private long majorID = 0;// 专业种类编号

	private String majorName = null;// 专业种类名称

	private String majorCode = null;// 专业种类编码

	private String majorDesc = null;// 专业种类描述

	/**
	 * @return 返回 majorCode。
	 */
	public String getMajorCode() {
		return majorCode;
	}

	/**
	 * @param majorCode
	 *            要设置的 majorCode。
	 */
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	/**
	 * @return 返回 majorDesc。
	 */
	public String getMajorDesc() {
		return majorDesc;
	}

	/**
	 * @param majorDesc
	 *            要设置的 majorDesc。
	 */
	public void setMajorDesc(String majorDesc) {
		this.majorDesc = majorDesc;
	}

	/**
	 * @return 返回 majorID。
	 */
	public long getMajorID() {
		return majorID;
	}

	/**
	 * @param majorID
	 *            要设置的 majorID。
	 */
	public void setMajorID(long majorID) {
		this.majorID = majorID;
	}

	/**
	 * @return 返回 majorName。
	 */
	public String getMajorName() {
		return majorName;
	}

	/**
	 * @param majorName
	 *            要设置的 majorName。
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
