/**
 * @(#)ProcessDocFolderAccessInfo.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public class ProcessDocFolderAccessInfo implements Serializable {

	public static final long serialVersionUID = 2069005687696291457L;
	
	private ProcessElementObject process = null;

	private ProcessDocFolderInfo folder = null;

	private int deleteFlag;

	private int viewFlag;

	private int operateFlag;

	private int addFolderFlag;

	private int updateFolderFlag;

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public ProcessDocFolderInfo getFolder() {
		return folder;
	}

	public void setFolder(ProcessDocFolderInfo folder) {
		this.folder = folder;
	}

	public int getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(int operateFlag) {
		this.operateFlag = operateFlag;
	}

	public ProcessElementObject getProcess() {
		return process;
	}

	public void setProcess(ProcessElementObject process) {
		this.process = process;
	}

	public int getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}

	public int getAddFolderFlag() {
		return addFolderFlag;
	}

	public void setAddFolderFlag(int addFolderFlag) {
		this.addFolderFlag = addFolderFlag;
	}

	public int getUpdateFolderFlag() {
		return updateFolderFlag;
	}

	public void setUpdateFolderFlag(int updateFolderFlag) {
		this.updateFolderFlag = updateFolderFlag;
	}

	public String toString() {
		return this.folder.getFolderName()
				+ (this.deleteFlag == 1 ? "     可删除" : "")
				+ (this.viewFlag == 1 ? "     可查看" : "")
				+ (this.operateFlag == 1 ? "     可操作" : "")
				+ (this.addFolderFlag == 1 ? "     可增加" : "")
				+ (this.updateFolderFlag == 1 ? "      可修改" : "");
	}
}
