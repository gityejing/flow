package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author fivedison
 *
 */
public class StringHandler implements ResultSetHandler {

	public Object handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return rs.getString(1);
		} else {
			return null;
		}
	}

}
