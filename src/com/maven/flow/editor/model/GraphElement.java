/**
 * @(#)GraphElement.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maven.flow.editor.adapter.impl.RolesAdapter;
import com.maven.flow.editor.adapter.impl.UsersAdapter;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-6
 * @since       JDK1.4
 */

public class GraphElement extends FlowElementObject {

	public static final long serialVersionUID = -5552132176855492105L;

	private long appId = -1;

	/**@deprecated */
	private boolean deployed = false;//发布情况标记

	private int validStatus = 0;//有效标记，只要发布了就是有效的

	private int releaseStatus = 0;//发布标记

	private String name = "";

	private String description = "";

	private Date createTime = null;

	private String creator = "";

	private String creatorName = "";

	private Date publishTime = null;

	private String publisher = "";

	private String publisherName = "";

	private String applicationCode="";
	private int monitorRule = 0;

	private String filePath = "";//流程图对应文件信息

	private List monitorAccessRoles = new ArrayList();

	private List monitorAccessUsers = new ArrayList();

	{
		//清空所有属性
		this.removeAllProperty();

		//添加属性
		this.addProperty("name", "流程名称", FlowProperty.STRING, 0, 0, true);
		this.addProperty("description", "描述", FlowProperty.STRING, 0, 1);
		this.addProperty("applicationCode", "流程编码", FlowProperty.STRING, 0, 1);
		this.addProperty("createtime", "创建时间", FlowProperty.STRING, false, 0, 2);
		this.addProperty("creatorName", "创建人", FlowProperty.STRING, false, 0, 3);
		this.addProperty("publishtime", "发布时间", FlowProperty.STRING, false, 0, 4);
		this.addProperty("publisherName", "发布人", FlowProperty.STRING, false, 0, 5);
		this.addProperty("appId", "流程编号", FlowProperty.INT, false, 0, 6, false);
		//this.addProperty("status", "流程状态", FlowProperty.STRING, false, 0, 7, false);
		this.addProperty("validStatus", "有效状态", FlowProperty.STRING, false, 0, 7, false);
		this.addProperty("releaseStatus", "发布状态", FlowProperty.STRING, false, 0, 8, false);
		this.addProperty("monitorrule", "流程监控授权", FlowProperty.OPTION, 1, 0);
		//this.addProperty("accessroles", "监控授权角色", FlowProperty.LIST, 1, 1);
		//this.addProperty("accessusers", "监控授权员工", FlowProperty.LIST, 1, 2);
		
		//添加列表值
		this.addListValue("monitorrule", new KeyValueObject[] { new KeyValueObject(new Integer(0), "参与者+自定义"),
				new KeyValueObject(new Integer(1), "只允许参与者"), new KeyValueObject(new Integer(2), "只允许自定义") });
		//		this.addListValue("accessroles", new RolesAdapter()
		//				.getKeyValueObjects());
		//		this.addListValue("accessusers", new UsersAdapter()
		//				.getKeyValueObjects());
	}

	public String getIconResource() {
		return null;
	}

	public String getImageResource() {
		return null;
	}

	public Object getValue(String propertyName) {
		if ("name".equals(propertyName)) {
			return this.getName();
		} else if ("description".equals(propertyName)) {
			return this.getDescription();
		}else if ("applicationCode".equals(propertyName)) {
			return this.getApplicationCode();
		}
		
		else if ("createtime".equals(propertyName)) {
			if (this.getCreateTime() == null)
				return "";
			else
				return new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss").format(this.getCreateTime());
		} else if ("creatorName".equals(propertyName)) {
			return this.getCreatorName();
		} else if ("publishtime".equals(propertyName)) {
			if (this.getPublishTime() == null)
				return "";
			else
				return new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss").format(this.getPublishTime());
		} else if ("publisherName".equals(propertyName)) {
			return this.getPublisherName();
		} else if ("monitorrule".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, new Integer(this.getMonitorRule()));
			return this.getKeyValueObject(propertyName, new Integer(this.getMonitorRule()));
		} else if ("accessroles".equals(propertyName)) {
			return this.monitorAccessRoles;
		} else if ("accessusers".equals(propertyName)) {
			return this.monitorAccessUsers;
		} else if ("appId".equals(propertyName)) {
			if (this.appId <= 0)
				return "无";
			else
				return String.valueOf(this.appId);
		} else if ("status".equals(propertyName)) {
			if (this.getValidStatus() == 0) {
				return "暂存";
			} else {
				if (this.getReleaseStatus() == 0) {
					return "挂起";
				} else {
					return "发布";
				}
			}
			/*
			 if (this.isDeployed()) {
			 return "已发布";
			 } else {
			 return "未发布";
			 }
			 */
		} else if ("validStatus".equals(propertyName)) {
			switch (this.getValidStatus()) {
				case 1:
					return "有效";
				case 0:
				default:
					return "暂存";
			}
		} else if ("releaseStatus".equals(propertyName)) {
			switch (this.getReleaseStatus()) {
				case 1:
					return "发布";
				case 0:
				default:
					return "挂起";
			}
		} else {
			return super.getValue(propertyName);
		}
	}

	public void setValue(String propertyName, Object value) {
		if ("name".equals(propertyName)) {
			this.setName(String.valueOf(value));
		} else if ("description".equals(propertyName)) {
			this.setDescription(String.valueOf(value));
		}
		else if ("applicationCode".equals(propertyName)) {
			this.setApplicationCode(String.valueOf(value));
		}
		
		else if ("createtime".equals(propertyName)) {
			//
		} else if ("creator".equals(propertyName)) {
			//
		} else if ("publishtime".equals(propertyName)) {
			//
		} else if ("publisher".equals(propertyName)) {
			//
		} else if ("monitorrule".equals(propertyName)) {
			this.setMonitorRule(((Integer) ((KeyValueObject) value).getKey()).intValue());
			this.updatePropertyEditAble("accessroles", this.monitorRule != 1);
			this.updatePropertyEditAble("accessusers", this.monitorRule != 1);
		} else if ("accessroles".equals(propertyName)) {
			if (value != null && (value instanceof List))
				this.monitorAccessRoles = (List) value;
		} else if ("accessusers".equals(propertyName)) {
			if (value != null && (value instanceof List))
				this.monitorAccessUsers = (List) value;
		} else {
			super.setValue(propertyName, value);
		}
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public int getMonitorRule() {
		return monitorRule;
	}

	public void setMonitorRule(int monitorRule) {
		this.monitorRule = monitorRule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public List getMonitorAccessRoles() {
		return monitorAccessRoles;
	}

	public void setMonitorAccessRoles(List monitorAccessRoles) {
		this.monitorAccessRoles = monitorAccessRoles;
	}

	public List getMonitorAccessUsers() {
		return monitorAccessUsers;
	}

	public void setMonitorAccessUsers(List monitorAccessUsers) {
		this.monitorAccessUsers = monitorAccessUsers;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	/**
	 * @return 返回 deployed。
	 */
	public boolean isDeployed() {
		return deployed;
	}

	/**
	 * @param deployed 要设置的 deployed。
	 */
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	public int getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(int releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public int getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(int validStatus) {
		this.validStatus = validStatus;
	}

	public Object[] getListValues(String propertyName) {
		if ("accessroles".equals(propertyName)) {
			return new RolesAdapter().getKeyValueObjects();
		} else if ("accessusers".equals(propertyName)) {
			return new UsersAdapter().getKeyValueObjects();
		} else {
			return super.getListValues(propertyName);
		}
	}
}
