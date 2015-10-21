/**
 * @(#)RolesAdapter.java 2007-6-7
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.adapter.impl;

import java.util.Iterator;
import java.util.List;

import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.hibernate.dao.TblRole;
import com.maven.flow.service.RoleService;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-7
 * @since       JDK1.5
 */

public class RolesAdapter implements KeyValueObjectAdapter {
    private RoleService roleService=new RoleService();
	 
	public KeyValueObject[] getKeyValueObjects() {
		try {
			//取得角色列表
			List  roleList=roleService.getRoleList();
			
			KeyValueObject[] kvos = new KeyValueObject[roleList.size()];
			int index = 0;
			for (Iterator it = (roleList).iterator(); it.hasNext();) {
				TblRole role = (TblRole) it.next();
				kvos[index] = new KeyValueObject(role.getFroleSn(), role.getFroleName());
				index++;
			}

			return kvos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new KeyValueObject[0];
		}
	}

}
