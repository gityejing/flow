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
		this.setName("开始");
		
		// 添加属性
		// this.addProperty("startpageurl", "启动页面", FlowProperty.STRING, 9, 0,
		// true);
		this.addProperty("handlePageUrl", "启动页面", FlowProperty.STRING, 0, 0,
				true);
		// 添加列表值
		// this.addListValueAdapter("startpageurl", new
		// PagesAdapter(PagesAdapter.TYPE_START));
		
		// 不允许修改任务分解规则
		this.removeProperty("taskrule");
		this.removeProperty("customizetaskrule");
		
		// 设置任务分解规则为自定义
		this.setTaskRule(1);
		
		this.setCustomizeTaskRule("cn.com.gczj.costagency.business.project.logic.StartProcessJobSpliter");
		
	}

	public String getIconResource() {
		return "resources/start_small.gif";
	}

	public String getImageResource() {
		return "resources/start_large.gif";
	}
	
	//重写了父类的方法
	public Object getValue(String propertyName) {

		if ("handlePageUrl".equals(propertyName)) {
			return this.handlePageUrl;
		} else {
			return super.getValue(propertyName);
		}
	}
	
	//重写了父类的方法。
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