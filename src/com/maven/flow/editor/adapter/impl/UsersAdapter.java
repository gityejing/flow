/**
 * @(#)UsersAdapter.java 2007-6-7
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.adapter.impl;

import java.util.Iterator;
import java.util.List;

import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.hibernate.dao.TblEmployeeInfo;
import com.maven.flow.service.EmployeeInfoService;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-6-7
 * @since JDK1.5
 */

public class UsersAdapter implements KeyValueObjectAdapter {

	private EmployeeInfoService employeeService =new EmployeeInfoService();
	
	
	public KeyValueObject[] getKeyValueObjects() {
		try {
			List list=employeeService.getAllEmployeeInfo();

			KeyValueObject[] kvos = new KeyValueObject[(list).size()];
			int index = 0;
			for (Iterator it = (list).iterator(); it
					.hasNext();) {
				TblEmployeeInfo info = (TblEmployeeInfo) it.next();
				kvos[index] = new KeyValueObject(
						new Long(info.getFemployeeSn()), info.getFemployeeName());
				index++;
			}

			return kvos;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new KeyValueObject[0];
		}
	}

}
