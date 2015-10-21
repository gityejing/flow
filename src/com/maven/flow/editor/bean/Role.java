package com.maven.flow.editor.bean;

/**
 * @(#)Role.java 2007-6-12
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 * 
 */

import java.io.Serializable;

/**
 * 封角色信息的数据
 * 
 * @author jumty
 * @version 1.02007-6-12
 * @since JDK1.4
 */
public class Role implements Serializable {

	private int roleID = 0;// 角色编号

	private String roleName = null;// 角色名称

	private String roleDesc = null;// 角色描述

	private String roleMember = null;// 角色成员

	private String roleMemberName = null;// 角色成员名称

	private int roleLevel = 0;// 角色成员名称

	private String sumCompanyID = null;// 分公司属性

	private int isGlobalCompany = 0;// 是否是总公司在操作 1:是 0:不是

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
	 * @return 返回 roleMemberName。
	 */
	public String getRoleMemberName() {
		return roleMemberName;
	}

	/**
	 * @param roleMemberName
	 *            要设置的 roleMemberName。
	 */
	public void setRoleMemberName(String roleMemberName) {
		this.roleMemberName = roleMemberName;
	}

	/**
	 * @return 返回 roleMember。
	 */
	public String getRoleMember() {
		return roleMember;
	}

	/**
	 * @param roleMember
	 *            要设置的 roleMember。
	 */
	public void setRoleMember(String roleMember) {
		this.roleMember = roleMember;
	}

	/**
	 * @return 返回 roleDesc。
	 */
	public String getRoleDesc() {
		return roleDesc;
	}

	/**
	 * @param roleDesc
	 *            要设置的 roleDesc。
	 */
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return 返回 roleID。
	 */
	public int getRoleID() {
		return roleID;
	}

	/**
	 * @param roleID
	 *            要设置的 roleID。
	 */
	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	/**
	 * @return 返回 roleName。
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            要设置的 roleName。
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
