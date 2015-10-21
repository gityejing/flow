/**
 * 
 */
package com.maven.flow.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author fivedison
 *
 */
public class BeanListHandler extends BeanHandler {

	private int startIndex = 0;
	
	
	public BeanListHandler(String beanClass) {
		super(beanClass);
	}

	public Object handle(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}
		rs.setFetchSize(1);
		ArrayList beans = new ArrayList();
		// 装载类
		Class bean = getBeanClass();
		try {
			// 获取元数据信息
			ResultSetMetaData meta = rs.getMetaData();

			while (rs.next()) {
				// 创建实体
				Object obj = bean.newInstance();
				setBeanProperty(obj, rs, meta);
				beans.add(obj);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException("set bean's property fail");
		}
		return beans;
	}
}