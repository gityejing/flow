/**
 * @(#)NullSqlObject.java 2007-6-13
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-13
 * @since       JDK1.5
 */

public class NullSqlObject {

	private int sqlType = -1;

	private NullSqlObject(int type) {
		this.sqlType = type;
	}

	/**
	 * @return 返回 sqlType。
	 */
	public int getSqlType() {
		return sqlType;
	}

	/**
	 * @param sqlType 要设置的 sqlType。
	 */
	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	public static Object getNullObject(int sqlType) {
		return new NullSqlObject(sqlType);
	}
}
