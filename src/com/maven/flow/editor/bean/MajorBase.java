package com.maven.flow.editor.bean;

/**
 * @(#)MajorBase.java 2007-6-12
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 * 
 */

import java.io.Serializable;

/**
 * ��װרҵ�����������
 * 
 * @author jumty
 * @version 1.02007-6-12
 * @since JDK1.4
 */
public class MajorBase implements Serializable {

	private long majorID = 0;// רҵ������

	private String majorName = null;// רҵ��������

	private String majorCode = null;// רҵ�������

	private String majorDesc = null;// רҵ��������

	/**
	 * @return ���� majorCode��
	 */
	public String getMajorCode() {
		return majorCode;
	}

	/**
	 * @param majorCode
	 *            Ҫ���õ� majorCode��
	 */
	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	/**
	 * @return ���� majorDesc��
	 */
	public String getMajorDesc() {
		return majorDesc;
	}

	/**
	 * @param majorDesc
	 *            Ҫ���õ� majorDesc��
	 */
	public void setMajorDesc(String majorDesc) {
		this.majorDesc = majorDesc;
	}

	/**
	 * @return ���� majorID��
	 */
	public long getMajorID() {
		return majorID;
	}

	/**
	 * @param majorID
	 *            Ҫ���õ� majorID��
	 */
	public void setMajorID(long majorID) {
		this.majorID = majorID;
	}

	/**
	 * @return ���� majorName��
	 */
	public String getMajorName() {
		return majorName;
	}

	/**
	 * @param majorName
	 *            Ҫ���õ� majorName��
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
}
