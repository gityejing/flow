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

	private String memoTitle = "";//��ע����

	private String okTitle = "";//ͨ������

	private String noOkTitle = "";//�������

	{
		this.setName("��������");

		//�������
		this.addProperty("memotitle", "��ע����", FlowProperty.STRING,8,0);
		this.addProperty("oktitle", "ͨ������", FlowProperty.STRING,8,1);
		this.addProperty("nooktitle", "�������", FlowProperty.STRING,8,2);

		//�鿴������ҳ����ʱ������
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
