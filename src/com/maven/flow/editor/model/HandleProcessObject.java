/**
 * @(#)HandleProcess.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.model;

import com.maven.flow.editor.adapter.impl.PagesAdapter;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-6-6
 * @since JDK1.4
 */

public class HandleProcessObject extends ProcessElementObject {





	private String isAllowSplit;// �ڵ��Ƿ�����ɢ�ӽڵ�.

	private String splitProcessHandle;

	{
		this.setName("��������");

		// �������
		// this.addProperty("viewpageurl", "�鿴ҳ��", FlowProperty.OPTION, 9, 0,
		// true);
		addProperty("viewpageurl", "�鿴ҳ��", FlowProperty.STRING, 0, 0, true);
		// this.addProperty("handlepageurl", "����ҳ��", FlowProperty.OPTION, 9, 0,
		// true);
		addProperty("handlepageurl", "����ҳ��", FlowProperty.STRING, 0, 0, true);
		//this.addProperty("MultiJobHandle", "��������ʽ", FlowProperty.OPTION, 9,0, true);

		// this.addProperty("splitProcessHandle", "��ɢ����ʽ",
		// FlowProperty.OPTION,9,0,true);

		// ����б�ֵ
		// this.addListValueAdapter("viewpageurl", new
		// PagesAdapter(PagesAdapter.TYPE_VIEW));
		// this.addListValueAdapter("handlepageurl", new
		// PagesAdapter(PagesAdapter.TYPE_HANDLE));
		/*
		this.addListValueAdapter("MultiJobHandle", new PagesAdapter(
				PagesAdapter.TYPE_MULTIJOB));
		*/
		// this.addListValueAdapter("splitProcessHandle", new
		// PagesAdapter(PagesAdapter.SPLITPPROCESSHANDLE));

	}

	public Object getValue(String propertyName) {
		if ("viewpageurl".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getViewPageUrl());
			return getViewPageUrl();
		} else if ("handlepageurl".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getHandlePageUrl());
			return getHandlePageUrl();
		} else if ("MultiJobHandle".equals(propertyName)) {
			return this.getKeyValueObjectName(propertyName, new Integer(this.getMultiJobHandle()+""));
		} else if ("splitProcessHandle".equals(propertyName)) {
			return this.getKeyValueObjectName(propertyName, this
					.getSplitProcessHandle());
		} else {
			return super.getValue(propertyName);
		}

	}

	public String getSplitProcessHandle() {
		return splitProcessHandle;
	}

	public void setSplitProcessHandle(String splitProcessHandle) {
		this.splitProcessHandle = splitProcessHandle;
	}

	public void setValue(String propertyName, Object value) {
		if ("viewpageurl".equals(propertyName)) {
			if (value != null) {
				//viewPageUrl = "" + value;
				super.setViewPageUrl(""+value);
			}
		} else if ("handlepageurl".equals(propertyName)) {
			if (value != null) {
				//this.handlePageUrl = "" + value;
				super.setHandlePageUrl(""+value);
			}
		} else if ("splitProcessHandle".equals(propertyName)) {
			if (value != null) {
				this.setSplitProcessHandle((String) ((KeyValueObject) value)
						.getKey());
			}
		}

		else if ("isAllowSplit".equals(propertyName)) {
			if (value != null) {
				this
						.setIsAllowSplit((String) ((KeyValueObject) value)
								.getKey());
			}
		} else {
			super.setValue(propertyName, value);
		}
	}

	public String getIsAllowSplit() {
		return isAllowSplit;
	}

	public void setIsAllowSplit(String isAllowSplit) {
		this.isAllowSplit = isAllowSplit;
	}

	public String getIconResource() {
		return "resources/ps1_small.gif";
	}

	public String getImageResource() {
		return "resources/ps1_large.gif";
	}




}
