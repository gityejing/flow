package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author kinz
 *
 */
public interface ResultSetHandler {

	/**
	 * 处理结果集
	 * @param rs
	 * @return 处理结果
	 * @throws SQLException
	 */
	public Object handle(ResultSet rs) throws SQLException;
}
