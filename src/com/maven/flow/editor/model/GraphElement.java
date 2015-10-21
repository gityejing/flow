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
	private boolean deployed = false;//����������

	private int validStatus = 0;//��Ч��ǣ�ֻҪ�����˾�����Ч��

	private int releaseStatus = 0;//�������

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

	private String filePath = "";//����ͼ��Ӧ�ļ���Ϣ

	private List monitorAccessRoles = new ArrayList();

	private List monitorAccessUsers = new ArrayList();

	{
		//�����������
		this.removeAllProperty();

		//�������
		this.addProperty("name", "��������", FlowProperty.STRING, 0, 0, true);
		this.addProperty("description", "����", FlowProperty.STRING, 0, 1);
		this.addProperty("applicationCode", "���̱���", FlowProperty.STRING, 0, 1);
		this.addProperty("createtime", "����ʱ��", FlowProperty.STRING, false, 0, 2);
		this.addProperty("creatorName", "������", FlowProperty.STRING, false, 0, 3);
		this.addProperty("publishtime", "����ʱ��", FlowProperty.STRING, false, 0, 4);
		this.addProperty("publisherName", "������", FlowProperty.STRING, false, 0, 5);
		this.addProperty("appId", "���̱��", FlowProperty.INT, false, 0, 6, false);
		//this.addProperty("status", "����״̬", FlowProperty.STRING, false, 0, 7, false);
		this.addProperty("validStatus", "��Ч״̬", FlowProperty.STRING, false, 0, 7, false);
		this.addProperty("releaseStatus", "����״̬", FlowProperty.STRING, false, 0, 8, false);
		this.addProperty("monitorrule", "���̼����Ȩ", FlowProperty.OPTION, 1, 0);
		//this.addProperty("accessroles", "�����Ȩ��ɫ", FlowProperty.LIST, 1, 1);
		//this.addProperty("accessusers", "�����ȨԱ��", FlowProperty.LIST, 1, 2);
		
		//����б�ֵ
		this.addListValue("monitorrule", new KeyValueObject[] { new KeyValueObject(new Integer(0), "������+�Զ���"),
				new KeyValueObject(new Integer(1), "ֻ���������"), new KeyValueObject(new Integer(2), "ֻ�����Զ���") });
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
				return new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss").format(this.getCreateTime());
		} else if ("creatorName".equals(propertyName)) {
			return this.getCreatorName();
		} else if ("publishtime".equals(propertyName)) {
			if (this.getPublishTime() == null)
				return "";
			else
				return new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss").format(this.getPublishTime());
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
				return "��";
			else
				return String.valueOf(this.appId);
		} else if ("status".equals(propertyName)) {
			if (this.getValidStatus() == 0) {
				return "�ݴ�";
			} else {
				if (this.getReleaseStatus() == 0) {
					return "����";
				} else {
					return "����";
				}
			}
			/*
			 if (this.isDeployed()) {
			 return "�ѷ���";
			 } else {
			 return "δ����";
			 }
			 */
		} else if ("validStatus".equals(propertyName)) {
			switch (this.getValidStatus()) {
				case 1:
					return "��Ч";
				case 0:
				default:
					return "�ݴ�";
			}
		} else if ("releaseStatus".equals(propertyName)) {
			switch (this.getReleaseStatus()) {
				case 1:
					return "����";
				case 0:
				default:
					return "����";
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
	 * @return ���� deployed��
	 */
	public boolean isDeployed() {
		return deployed;
	}

	/**
	 * @param deployed Ҫ���õ� deployed��
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
