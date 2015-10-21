/*
 * 创建日期 2007.04.18
 * 作者     kinz
 * 文件名   DatabaseManager.java
 * 版权     CopyRight (c) 2007
 * 功能说明 数据库相关信息
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

	//配置文件
	private static ResourceConfig config = ResourceConfig.getConfig("jdbc.properties");

	private static ComboPooledDataSource ds =  null ;

	private static boolean isConfiged = false;

	//日志操作对象
	private static Log log = LogFactory.getLog(DatabaseManager.class);

	public static int getDBMS() {
		//String dbms = config.getProperty("dbms");
		String dbms = config.getStringConfig("dbms", "sqlserver");
		if (dbms == null)
			return DBMS_ORACLE;//默认数据库为ORACLE
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
	 * 获取一个数据库连接
	 *
	 * @return 数据库的一个连接实例
	 * @throws Exception 获取连接发生错误
	 */
	public static Connection getConnection() throws Exception {
		//获取连接类型
		String connectType = config.getStringConfig("connecttype", null);
		if (connectType == null) {
			log.debug("配置 [jdbc.properties] 中没有指定连接类型[connecttype]，将使用JDBC进行直接连接");
			return getDirectConnection();
		}
		if ("datasource".equalsIgnoreCase(connectType)) {
			log.debug("使用DataSource方式连接数据库");
			return getDataSourceConnection();
		} else if ("jdbc".equalsIgnoreCase(connectType)) {
			log.debug("使用JDBC方式直接连接数据库");
			return getDirectConnection();
		} else {
			log.warn("未知连接方式，不能连接数据库");
			throw new Exception("请在配置 [jdbc.properties] 中指定 [connecttype] 参数");
		}
	}

	/**
	 * 直接通过JDBC连接数据库
	 * 采用C3P0连接池
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
					throw new Exception("请在配置[jdbc.properties]中指定[driver_class]参数");
				}
				Class.forName(driver);

				String url = config.getStringConfig("url", null);
				if (StringUtils.isEmpty(url)) {
					throw new Exception("请在配置[jdbc.properties]中指定[url]参数");
				}
				String username = config.getStringConfig("username", null);
				if (StringUtils.isEmpty(username)) {
					log.warn("没有指定登录数据库的用户名，可能会被数据库拒绝登录，如要指定，请设置[username]参数");
				}
				String password = config.getStringConfig("password", null);
				if (StringUtils.isEmpty(password)) {
					log.warn("没有指定登录数据库的密码，可能会被数据库拒绝登录，如要指定，请设置[password]参数");
				}
				log.debug("数据库配置参数 [driver:"+driver+"; url:"+url+"; user:"+username+"; password:"+password+"]");
                                if (ds == null){
                                    configPool(driver, username, password, url);
                                }
			}
			con = ds.getConnection();
//			con = DriverManager.getConnection(url, username, password);
			if (con == null) {
				log.error("不能连接到数据库!");
			}

			log.debug("获取数据库连接总共花费时间："+((System.currentTimeMillis() - st)/1000)+"秒");
			return con;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 通过使用DataSource获取连接
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
			throw new Exception("连接数据库失败：" + ex.getMessage());
		}
	}

	/**
	 * 关闭连接
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
			log.debug("开始配置连接池");
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
			log.debug("连接池配置完成");
//			log.debug(ToStringUtil.beaToString(poolConfig));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
