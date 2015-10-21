/**
 * @(#)ProcessDocFolderInfo.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

/**
 * ��װ�����ĵ�Ŀ¼�������Ϣ������Ȩ����Ϣ
 * �༭�ĵ�Ŀ¼�ṹʱʹ��
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public class ProcessDocFolderInfo implements Serializable {

	private String folderName;

	private String folderDesc;

	private int subProjectFlag;

	private long majorId;

	public String getFolderDesc() {
		return folderDesc;
	}

	public void setFolderDesc(String folderDesc) {
		this.folderDesc = folderDesc;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return ���� majorId��
	 */
	public long getMajorId() {
		return majorId;
	}

	/**
	 * @param majorId Ҫ���õ� majorId��
	 */
	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}

	/**
	 * @return ���� subProjectFlag��
	 */
	public int getSubProjectFlag() {
		return subProjectFlag;
	}

	/**
	 * @param subProjectFlag Ҫ���õ� subProjectFlag��
	 */
	public void setSubProjectFlag(int subProjectFlag) {
		this.subProjectFlag = subProjectFlag;
	}

	public String toString() {
		return this.getFolderName();
	}
}
