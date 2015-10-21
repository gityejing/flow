/*
 * �������� 2007.04.18
 * ����     kinz
 * �ļ���   DatabaseManager.java
 * ��Ȩ     CopyRight (c) 2007
 * ����˵�� ���ݿ������Ϣ
 */
package com.maven.flow.util;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DatabaseManager {

	public static final int DBMS_ORACLE = 0;

	public static final int DBMS_MYSQL = 1;

	public static final int DBMS_SQLSERVER = 2;

	public static final int DBMS_SYBASE = 3;

	//�����ļ�
	private static ResourceConfig config = ResourceConfig.getConfig("jdbc.properties");

	private static ComboPooledDataSource ds =  null ;

	private static boolean isConfiged = false;

	//��־��������
	private static Log log = LogFactory.getLog(DatabaseManager.class);

	public static int getDBMS() {
		//String dbms = config.getProperty("dbms");
		String dbms = config.getStringConfig("dbms", "sqlserver");
		if (dbms == null)
			return DBMS_ORACLE;//Ĭ�����ݿ�ΪORACLE
		if ("mysql".equalsIgnoreCase(dbms.trim())) {
			return DBMS_MYSQL;
		} else if ("sqlserver".equalsIgnoreCase(dbms.trim())) {
			return DBMS_SQLSERVER;
		} else if ("sybase".equalsIgnoreCase(dbms.trim())) {
			return DBMS_SYBASE;
		} else {
			return DBMS_ORACLE;
		}
		//return DBMS_MYSQL;
	}

	/**
	 * ��ȡһ�����ݿ�����
	 *
	 * @return ���ݿ��һ������ʵ��
	 * @throws Exception ��ȡ���ӷ�������
	 */
	public static Connection getConnection() throws Exception {
		//��ȡ��������
		String connectType = config.getStringConfig("connecttype", null);
		if (connectType == null) {
			log.debug("���� [jdbc.properties] ��û��ָ����������[connecttype]����ʹ��JDBC����ֱ������");
			return getDirectConnection();
		}
		if ("datasource".equalsIgnoreCase(connectType)) {
			log.debug("ʹ��DataSource��ʽ�������ݿ�");
			return getDataSourceConnection();
		} else if ("jdbc".equalsIgnoreCase(connectType)) {
			log.debug("ʹ��JDBC��ʽֱ���������ݿ�");
			return getDirectConnection();
		} else {
			log.warn("δ֪���ӷ�ʽ�������������ݿ�");
			throw new Exception("�������� [jdbc.properties] ��ָ�� [connecttype] ����");
		}
	}

	/**
	 * ֱ��ͨ��JDBC�������ݿ�
	 * ����C3P0���ӳ�
	 *
	 * @return
	 * @throws Exception
	 */
	private static Connection getDirectConnection() throws Exception {
		Connection con = null;
		try {
			long st = System.currentTimeMillis();
			if (!isConfiged) {
				String driver = config.getStringConfig("driver_class", null);
				if (StringUtils.isEmpty(driver)) {
					throw new Exception("��������[jdbc.properties]��ָ��[driver_class]����");
				}
				Class.forName(driver);

				String url = config.getStringConfig("url", null);
				if (StringUtils.isEmpty(url)) {
					throw new Exception("��������[jdbc.properties]��ָ��[url]����");
				}
				String username = config.getStringConfig("username", null);
				if (StringUtils.isEmpty(username)) {
					log.warn("û��ָ����¼���ݿ���û��������ܻᱻ���ݿ�ܾ���¼����Ҫָ����������[username]����");
				}
				String password = config.getStringConfig("password", null);
				if (StringUtils.isEmpty(password)) {
					log.warn("û��ָ����¼���ݿ�����룬���ܻᱻ���ݿ�ܾ���¼����Ҫָ����������[password]����");
				}
				log.debug("���ݿ����ò��� [driver:"+driver+"; url:"+url+"; user:"+username+"; password:"+password+"]");
                                if (ds == null){
                                    configPool(driver, username, password, url);
                                }
			}
			con = ds.getConnection();
//			con = DriverManager.getConnection(url, username, password);
			if (con == null) {
				log.error("�������ӵ����ݿ�!");
			}

			log.debug("��ȡ���ݿ������ܹ�����ʱ�䣺"+((System.currentTimeMillis() - st)/1000)+"��");
			return con;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ͨ��ʹ��DataSource��ȡ����
	 * @return
	 * @throws Exception
	 */
	private static Connection getDataSourceConnection() throws Exception {
		try {
			String dsName = config.getStringConfig("datasource", "java:comp/env/jdbc/costagency");
			Context cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup(dsName);
			cxt.close();
			return ds.getConnection();
		} catch (Exception ex) {
			throw new Exception("�������ݿ�ʧ�ܣ�" + ex.getMessage());
		}
	}

	/**
	 * �ر�����
	 * @param con
	 */
	public static void close(Connection con) {
		if (con == null)
			return;
		try {
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void configPool(String driver, String user, String password, String url) {
		try {
			log.debug("��ʼ�������ӳ�");
//			PoolConfig poolConfig = new PoolConfig();
                        ds =  new   ComboPooledDataSource();
			ds.setMinPoolSize(10);
			ds.setMaxPoolSize(500);
			ds.setMaxIdleTime(1800);
			ds.setMaxStatements(200);
			// poolConfig.setAcquireIncrement(1);
			ds.setIdleConnectionTestPeriod(0);
			ds.setInitialPoolSize(300);

                        ds.setDriverClass(driver);
                        ds.setJdbcUrl(url);
                        ds.setUser(user);
                        ds.setPassword(password);



			isConfiged = true;
			log.debug("���ӳ��������");
//			log.debug(ToStringUtil.beaToString(poolConfig));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
