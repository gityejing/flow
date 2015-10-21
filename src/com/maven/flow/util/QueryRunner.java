package com.maven.flow.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryRunner {

	private static Log log = LogFactory.getLog(QueryRunner.class);

	/**
	 * 查询数量
	 *
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int queryCount(String sql) throws Exception {
		return queryCount(sql, null);
	}

	/**
	 * 根据传入的查询语句和参数进行查询数量
	 *
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static int queryCount(String sql, Object[] params) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DatabaseManager.getConnection();
			int index1 = sql.toLowerCase().indexOf("order by");

			String countSql = "SELECT COUNT(1) FROM (" + (index1 == -1 ? sql : sql.substring(0, index1)) + ") AS _count";

			log.debug("\n语句: [" + countSql + "]\n参数: " + ToStringUtil.arrayToString(params));

			/*
			 int index = sql.toLowerCase().indexOf("from");
			 int index1 = sql.toLowerCase().indexOf("order by");
			 String countSql = "SELECT COUNT(1) "
			 + (index1 != -1 ? sql.substring(index, index1) : sql
			 .substring(index));
			 */
			stmt = con.prepareStatement(countSql);
			fillStatement(stmt, params);

			rs = stmt.executeQuery();

			int count = 0;

			while (rs.next()) {
				count += rs.getInt(1);
			}

			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("查询失败，信息：" + e.getMessage());
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
	}
        public static ResultSet queryResult(String sql, Object[] params) throws Exception {
                Connection con = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                        log.debug("\n语句: [" + sql + "]\n参数: " + ToStringUtil.arrayToString(params));
                        con = DatabaseManager.getConnection();
                        stmt = con.prepareStatement(sql);
                        fillStatement(stmt, params);
                        rs = stmt.executeQuery();
                        return rs;
                } catch (SQLException e) {
                        log.error("执行查询失败", e);
                        throw new Exception("查询失败，信息：" + e.getMessage());
                } finally {
                        //close(rs);
                        //close(stmt);
                        //close(con);
                }
	}
	/**
	 * 查询结果集
	 *
	 * @param sql
	 *            查询的Sql语句
	 * @return 结果集
	 * @throws Exception
	 */
	public static Object queryResultSet(String sql, ResultSetHandler handler) throws Exception {
		return queryResultSet(sql, null, handler);
	}

	/**
	 * 根据传入的sql语句和参数查询
	 *
	 * @param sql
	 * @param params
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	public static Object queryResultSet(String sql, Object[] params, ResultSetHandler handler) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			log.debug("\n语句: [" + sql + "]\n参数: " + ToStringUtil.arrayToString(params));
			con = DatabaseManager.getConnection();
			stmt = con.prepareStatement(sql);
			fillStatement(stmt, params);
			rs = stmt.executeQuery();
			return handler.handle(rs);
		} catch (SQLException ex) {
			log.error("执行查询失败", ex);
			throw new Exception("查询失败，信息：" + ex.getMessage());
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
	}

	/**
	 * 执行数据库更新操作，返回受影响的记录数
	 *
	 * @param sql
	 *            要执行的SQL语句
	 * @return 受影响的记录数
	 * @throws Exception
	 *             执行数据库操作出现错误
	 */
	public static int update(String sql) throws Exception {
		return execute(sql, null);
	}

	/**
	 * 执行数据库更新操作，返回受影响的记录数
	 *
	 * @param sql
	 *            要执行的SQL语句
	 * @param params
	 *            SQL语句的参数
	 * @return 受影响的记录数
	 * @throws Exception
	 *             执行数据库操作出现错误
	 */
	public static int update(String sql, Object[] params) throws Exception {
		return execute(sql, params);
	}

	/**
	 * 关闭一个Connection，关闭时对是否为null进行判断
	 *
	 * @param con
	 *            要关闭的Connection
	 */
	protected static void close(Connection con) {
		if (con == null)
			return;
		try {
			con.close();
			con = null;
		} catch (Exception ex) {
			log.debug("关闭Connection失败", ex);
		}
	}

	/**
	 * 关闭一个Statement，关闭时对是否为null进行判断
	 *
	 * @param stmt
	 *            要关闭的Statement
	 */
	protected static void close(Statement stmt) {
		if (stmt == null)
			return;
		try {
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			log.debug("关闭Statement失败", ex);
		}
	}

	/**
	 * 关闭一个ResultSet，关闭时对是否为null进行判断
	 *
	 * @param rs
	 *            要关闭的ResultSet
	 */
	protected static void close(ResultSet rs) {
		if (rs == null)
			return;
		try {
			rs.close();
			rs = null;
		} catch (Exception ex) {
			log.debug("关闭ResultSet失败", ex);
		}
	}

	/**
	 * 关闭与ResultSet相关的Statement和Connection。如果使用了返回ResultSet的查询方法，请使用这个方法来关闭ResultSet
	 *
	 * @param rs
	 */
	public static void closeAll(ResultSet rs) {
		if (rs == null)
			return;
		Statement stmt = null;
		try {
			stmt = rs.getStatement();
		} catch (Exception ex) {

		} finally {
			close(rs);
			closeAll(stmt);
		}
	}

	public static void closeAll(Statement stmt) {
		if (stmt == null)
			return;
		Connection con = null;
		try {
			con = stmt.getConnection();
		} catch (Exception e) {
		} finally {
			close(stmt);
			close(con);
		}
	}

	/**
	 * 执行SQL
	 *
	 * @param sql
	 *            查询的Sql语句
	 * @return 结果集
	 * @throws Exception
	 */
	public static int execute(String sql) throws Exception {
		return execute(sql, null);
	}

	public static int execute(String sql, Object[] params) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			log.debug("\n语句: [" + sql + "]\n参数: " + ToStringUtil.arrayToString(params));
			con = DatabaseManager.getConnection();
			stmt = con.prepareStatement(sql);

			fillStatement(stmt, params);

			return stmt.executeUpdate();
		} catch (SQLException ex) {
			//log.debug("执行SQL失败", ex);
			throw new Exception("执行SQL失败，信息：" + ex.getMessage());
		} finally {
			close(stmt);
			close(con);
		}
	}

	

	

	protected static void fillStatement(PreparedStatement stmt, Object params[]) throws SQLException {
		if (params == null) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			//log.debug("设置参数："+i+"="+params[i]);
			if (params[i] == null) {
				stmt.setNull(i + 1, Types.VARCHAR);
			} else if (params[i] instanceof NullSqlObject) {
				stmt.setNull(i + 1, ((NullSqlObject) params[i]).getSqlType());
                        }  else if (params[i] instanceof Long) {
                            stmt.setLong(i+1,((Long)params[i]).longValue());
                        } else if (params[i] instanceof Integer) {
                            stmt.setInt(i+1,((Integer)params[i]).intValue());
                        } else if (params[i] instanceof String) {
                            stmt.setString(i+1,(String)params[i]);
                        } else if (params[i] instanceof Double) {
                            stmt.setDouble(i+1,((Double)params[i]).doubleValue());
                        } else if (params[i] instanceof Boolean) {
                            stmt.setBoolean(i+1,((Boolean)params[i]).booleanValue());
                        } else {
				stmt.setObject(i + 1, params[i]);
			}
		}

	}

	/**
	 * 获取指定名称的序列值
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public static long getSequenceNext(String sequenceName) throws Exception {
		return SequenceGenerator.getSequenceNext(sequenceName);
	}

        /**
         * 获取指定名称的序列值,但不更新列值
         * @param sequenceName
         * @return
         * @throws Exception
         */
        public static long getSequenceOnlyNext(String sequenceName) throws Exception {
                return SequenceGenerator.getSequenceOnlyNext(sequenceName);
        }

	// test
	public static void main(String[] args) throws Exception {
		/*try {
		 QueryRunner.queryResultSet("SELECT * FROM memberinfo", new ResultSetHandler() {

		 public Object handle(ResultSet rs) throws SQLException {
		 while (rs.next()) {
		 System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4));
		 }
		 return null;
		 }});
		 } catch (Exception e) {
		 e.printStackTrace();
		 }*/
	}
}
