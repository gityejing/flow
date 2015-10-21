/**
 * @(#)DoubleHandler.java 2007-9-24
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-9-24
 * @since       JDK1.5
 */

public class DoubleHandler implements ResultSetHandler {

	public Object handle(ResultSet rs) throws SQLException {
		if (rs.next())
			return new Double(rs.getDouble(1));
		else
			return new Double(0);
	}

}
