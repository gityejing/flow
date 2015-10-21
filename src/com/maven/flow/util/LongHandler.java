package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author fivedison
 *
 */
public class LongHandler implements ResultSetHandler {

	public Object handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return new Long(rs.getLong(1));
		} else {
			return new Long(-1L);
		}
	}

}
