package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author kinz
 *
 */
public interface ResultSetHandler {

	/**
	 * ��������
	 * @param rs
	 * @return ������
	 * @throws SQLException
	 */
	public Object handle(ResultSet rs) throws SQLException;
}
