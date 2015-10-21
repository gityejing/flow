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
	 * ��ѯ����
	 *
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static int queryCount(String sql) throws Exception {
		return queryCount(sql, null);
	}

	/**
	 * ���ݴ���Ĳ�ѯ���Ͳ������в�ѯ����
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

			log.debug("\n���: [" + countSql + "]\n����: " + ToStringUtil.arrayToString(params));

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
			throw new Exception("��ѯʧ�ܣ���Ϣ��" + e.getMessage());
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
                        log.debug("\n���: [" + sql + "]\n����: " + ToStringUtil.arrayToString(params));
                        con = DatabaseManager.getConnection();
                        stmt = con.prepareStatement(sql);
                        fillStatement(stmt, params);
                        rs = stmt.executeQuery();
                        return rs;
                } catch (SQLException e) {
                        log.error("ִ�в�ѯʧ��", e);
                        throw new Exception("��ѯʧ�ܣ���Ϣ��" + e.getMessage());
                } finally {
                        //close(rs);
                        //close(stmt);
                        //close(con);
                }
	}
	/**
	 * ��ѯ�����
	 *
	 * @param sql
	 *            ��ѯ��Sql���
	 * @return �����
	 * @throws Exception
	 */
	public static Object queryResultSet(String sql, ResultSetHandler handler) throws Exception {
		return queryResultSet(sql, null, handler);
	}

	/**
	 * ���ݴ����sql���Ͳ�����ѯ
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
			log.debug("\n���: [" + sql + "]\n����: " + ToStringUtil.arrayToString(params));
			con = DatabaseManager.getConnection();
			stmt = con.prepareStatement(sql);
			fillStatement(stmt, params);
			rs = stmt.executeQuery();
			return handler.handle(rs);
		} catch (SQLException ex) {
			log.error("ִ�в�ѯʧ��", ex);
			throw new Exception("��ѯʧ�ܣ���Ϣ��" + ex.getMessage());
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
	}

	/**
	 * ִ�����ݿ���²�����������Ӱ��ļ�¼��
	 *
	 * @param sql
	 *            Ҫִ�е�SQL���
	 * @return ��Ӱ��ļ�¼��
	 * @throws Exception
	 *             ִ�����ݿ�������ִ���
	 */
	public static int update(String sql) throws Exception {
		return execute(sql, null);
	}

	/**
	 * ִ�����ݿ���²�����������Ӱ��ļ�¼��
	 *
	 * @param sql
	 *            Ҫִ�е�SQL���
	 * @param params
	 *            SQL���Ĳ���
	 * @return ��Ӱ��ļ�¼��
	 * @throws Exception
	 *             ִ�����ݿ�������ִ���
	 */
	public static int update(String sql, Object[] params) throws Exception {
		return execute(sql, params);
	}

	/**
	 * �ر�һ��Connection���ر�ʱ���Ƿ�Ϊnull�����ж�
	 *
	 * @param con
	 *            Ҫ�رյ�Connection
	 */
	protected static void close(Connection con) {
		if (con == null)
			return;
		try {
			con.close();
			con = null;
		} catch (Exception ex) {
			log.debug("�ر�Connectionʧ��", ex);
		}
	}

	/**
	 * �ر�һ��Statement���ر�ʱ���Ƿ�Ϊnull�����ж�
	 *
	 * @param stmt
	 *            Ҫ�رյ�Statement
	 */
	protected static void close(Statement stmt) {
		if (stmt == null)
			return;
		try {
			stmt.close();
			stmt = null;
		} catch (Exception ex) {
			log.debug("�ر�Statementʧ��", ex);
		}
	}

	/**
	 * �ر�һ��ResultSet���ر�ʱ���Ƿ�Ϊnull�����ж�
	 *
	 * @param rs
	 *            Ҫ�رյ�ResultSet
	 */
	protected static void close(ResultSet rs) {
		if (rs == null)
			return;
		try {
			rs.close();
			rs = null;
		} catch (Exception ex) {
			log.debug("�ر�ResultSetʧ��", ex);
		}
	}

	/**
	 * �ر���ResultSet��ص�Statement��Connection�����ʹ���˷���ResultSet�Ĳ�ѯ��������ʹ������������ر�ResultSet
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
	 * ִ��SQL
	 *
	 * @param sql
	 *            ��ѯ��Sql���
	 * @return �����
	 * @throws Exception
	 */
	public static int execute(String sql) throws Exception {
		return execute(sql, null);
	}

	public static int execute(String sql, Object[] params) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			log.debug("\n���: [" + sql + "]\n����: " + ToStringUtil.arrayToString(params));
			con = DatabaseManager.getConnection();
			stmt = con.prepareStatement(sql);

			fillStatement(stmt, params);

			return stmt.executeUpdate();
		} catch (SQLException ex) {
			//log.debug("ִ��SQLʧ��", ex);
			throw new Exception("ִ��SQLʧ�ܣ���Ϣ��" + ex.getMessage());
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
			//log.debug("���ò�����"+i+"="+params[i]);
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
	 * ��ȡָ�����Ƶ�����ֵ
	 * @param sequenceName
	 * @return
	 * @throws Exception
	 */
	public static long getSequenceNext(String sequenceName) throws Exception {
		return SequenceGenerator.getSequenceNext(sequenceName);
	}

        /**
         * ��ȡָ�����Ƶ�����ֵ,����������ֵ
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
