/**
 * @(#)ProcessDocFolderInfo.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

/**
 * 封装步骤文档目录的相关信息，包括权限信息
 * 编辑文档目录结构时使用
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
	 * @return 返回 majorId。
	 */
	public long getMajorId() {
		return majorId;
	}

	/**
	 * @param majorId 要设置的 majorId。
	 */
	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}

	/**
	 * @return 返回 subProjectFlag。
	 */
	public int getSubProjectFlag() {
		return subProjectFlag;
	}

	/**
	 * @param subProjectFlag 要设置的 subProjectFlag。
	 */
	public void setSubProjectFlag(int subProjectFlag) {
		this.subProjectFlag = subProjectFlag;
	}

	public String toString() {
		return this.getFolderName();
	}
}
