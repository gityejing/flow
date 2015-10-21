/**
 * @(#)StartElement.java 2007-5-30
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-30
 * @since JDK1.4
 */

public class StartElement extends ProcessElementObject {

	private String handlePageUrl = "";

	{
		this.setName("��ʼ");
		
		// �������
		// this.addProperty("startpageurl", "����ҳ��", FlowProperty.STRING, 9, 0,
		// true);
		this.addProperty("handlePageUrl", "����ҳ��", FlowProperty.STRING, 0, 0,
				true);
		// ����б�ֵ
		// this.addListValueAdapter("startpageurl", new
		// PagesAdapter(PagesAdapter.TYPE_START));
		
		// �������޸�����ֽ����
		this.removeProperty("taskrule");
		this.removeProperty("customizetaskrule");
		
		// ��������ֽ����Ϊ�Զ���
		this.setTaskRule(1);
		
		this.setCustomizeTaskRule("cn.com.gczj.costagency.business.project.logic.StartProcessJobSpliter");
		
	}

	public String getIconResource() {
		return "resources/start_small.gif";
	}

	public String getImageResource() {
		return "resources/start_large.gif";
	}
	
	//��д�˸���ķ���
	public Object getValue(String propertyName) {

		if ("handlePageUrl".equals(propertyName)) {
			return this.handlePageUrl;
		} else {
			return super.getValue(propertyName);
		}
	}
	
	//��д�˸���ķ�����
	public void setValue(String propertyName, Object value) {
		if ("handlePageUrl".equals(propertyName)) {
			if (value != null) {
				this.handlePageUrl = "" + value;
			}
		} else {
			super.setValue(propertyName, value);
		}
		// super.setValue(propertyName, value);
	}

	public String getHandlePageUrl() {
		return handlePageUrl;
	}

	public void setHandlePageUrl(String handlePageUrl) {
		this.handlePageUrl = handlePageUrl;
	}

}