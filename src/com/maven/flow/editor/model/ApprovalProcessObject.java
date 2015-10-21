/**
 * @(#)ApprovalProcess.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.model;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-6
 * @since       JDK1.4
 */

public class ApprovalProcessObject extends HandleProcessObject {

	private String memoTitle = "";//备注标题

	private String okTitle = "";//通过标题

	private String noOkTitle = "";//否决标题

	{
		this.setName("审批步骤");

		//添加属性
		this.addProperty("memotitle", "备注标题", FlowProperty.STRING,8,0);
		this.addProperty("oktitle", "通过标题", FlowProperty.STRING,8,1);
		this.addProperty("nooktitle", "否决标题", FlowProperty.STRING,8,2);

		//查看、处理页面暂时不开放
		this.removeProperty("viewpageurl");
		this.removeProperty("handlepageurl");
                this.removeProperty("MultiJobHandle");
	}

	public Object getValue(String propertyName) {
		if ("memotitle".equals(propertyName)) {
			return this.getMemoTitle();
		} else if ("oktitle".equals(propertyName)) {
			return this.getOkTitle();
		} else if ("nooktitle".equals(propertyName)) {
			return this.getNoOkTitle();
		} else {
			return super.getValue(propertyName);
		}
	}

	public void setValue(String propertyName, Object value) {
		if ("memotitle".equals(propertyName)) {
			this.setMemoTitle(String.valueOf(value));
		} else if ("oktitle".equals(propertyName)) {
			this.setOkTitle(String.valueOf(value));
		} else if ("nooktitle".equals(propertyName)) {
			this.setNoOkTitle(String.valueOf(value));
		} else {
			super.setValue(propertyName, value);
		}
	}

	public String getIconResource() {
		return "resources/ps2_small.gif";
	}

	public String getImageResource() {
		return "resources/ps2_large.gif";
	}

	public String getMemoTitle() {
		return memoTitle;
	}

	public void setMemoTitle(String memoTitle) {
		this.memoTitle = memoTitle;
	}

	public String getNoOkTitle() {
		return noOkTitle;
	}

	public void setNoOkTitle(String noOkTitle) {
		this.noOkTitle = noOkTitle;
	}

	public String getOkTitle() {
		return okTitle;
	}

	public void setOkTitle(String okTitle) {
		this.okTitle = okTitle;
	}
}
