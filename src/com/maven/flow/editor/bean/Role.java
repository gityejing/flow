package com.maven.flow.editor.bean;

/**
 * @(#)Role.java 2007-6-12
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 * 
 */

import java.io.Serializable;

/**
 * ���ɫ��Ϣ������
 * 
 * @author jumty
 * @version 1.02007-6-12
 * @since JDK1.4
 */
public class Role implements Serializable {

	private int roleID = 0;// ��ɫ���

	private String roleName = null;// ��ɫ����

	private String roleDesc = null;// ��ɫ����

	private String roleMember = null;// ��ɫ��Ա

	private String roleMemberName = null;// ��ɫ��Ա����

	private int roleLevel = 0;// ��ɫ��Ա����

	private String sumCompanyID = null;// �ֹ�˾����

	private int isGlobalCompany = 0;// �Ƿ����ܹ�˾�ڲ��� 1:�� 0:����

	private String otherUserId;

	public int getIsGlobalCompany() {
		return isGlobalCompany;
	}

	public void setIsGlobalCompany(int isGlobalCompany) {
		this.isGlobalCompany = isGlobalCompany;
	}

	public String getOtherUserId() {
		return otherUserId;
	}

	public void setOtherUserId(String otherUserId) {
		this.otherUserId = otherUserId;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	/**
	 * @return ���� roleMemberName��
	 */
	public String getRoleMemberName() {
		return roleMemberName;
	}

	/**
	 * @param roleMemberName
	 *            Ҫ���õ� roleMemberName��
	 */
	public void setRoleMemberName(String roleMemberName) {
		this.roleMemberName = roleMemberName;
	}

	/**
	 * @return ���� roleMember��
	 */
	public String getRoleMember() {
		return roleMember;
	}

	/**
	 * @param roleMember
	 *            Ҫ���õ� roleMember��
	 */
	public void setRoleMember(String roleMember) {
		this.roleMember = roleMember;
	}

	/**
	 * @return ���� roleDesc��
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc
	 *            Ҫ���õ� roleDesc��
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return ���� roleID��
	 */
	public int getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID
	 *            Ҫ���õ� roleID��
	 */
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	/**
	 * @return ���� roleName��
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            Ҫ���õ� roleName��
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSumCompanyID() {
		return sumCompanyID;
	}

	public void setSumCompanyID(String sumCompanyID) {
		this.sumCompanyID = sumCompanyID;
	}

}
