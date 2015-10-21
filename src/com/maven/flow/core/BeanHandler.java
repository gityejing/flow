package com.maven.flow.core;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.commons.beanutils.PropertyUtils;



public class BeanHandler {

	String beanClass = null;

	public BeanHandler(String beanClass) {
		this.beanClass = beanClass;
	}

	public Object handle(ResultSet rs) throws SQLException {
		if (rs == null) {
			return null;
		}

		Class bean = getBeanClass();
		Object beanObj = null;
		try {
			// 获取元数据信息
			ResultSetMetaData meta = rs.getMetaData();

			if (rs.next()) {
				beanObj = bean.newInstance();
				setBeanProperty(beanObj, rs, meta);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new SQLException(ex.getMessage());
		}
		return beanObj;
	}

	protected Class getBeanClass() throws SQLException {
		if (beanClass == null) {
			throw new SQLException("必需指定要填充的JavaBean的完整类名称");
		}
		Class bean = null;
		try {
			bean = Class.forName(beanClass);
		} catch (ClassNotFoundException ex) {
			throw new SQLException("指定的类: " + beanClass + " 不存在.");
		}
		return bean;
	}

	protected void setBeanProperty(Object bean, ResultSet rs,
			ResultSetMetaData meta) throws Exception {
		// System.out.println("change encoding:"+change+" from "+dbEncoding+" to
		// "+appEncoding);
		int colCount = meta.getColumnCount();
		String colName = null;
		PropertyDescriptor des = null;
		String propertyName = null;
		int colType = -1;
		String currentProperty = null;
		try {
			for (int i = 1; i <= colCount; i++) {
				colName = meta.getColumnName(i);
				colType = meta.getColumnType(i);
				des = getBeanPropertyDes(colName, bean);
				// log.debug("setting property:
				// "+colName+"="+String.valueOf(rs.getObject(i)));
				if (des == null) {
					continue;
				}
				propertyName = des.getName();
				currentProperty = propertyName;

				// log.debug("handling bean["+this.beanClass+"]
				// property["+propertyName+"]");

				switch (colType) {
				case Types.SMALLINT:
				case Types.INTEGER: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Integer(rs.getInt(i)));
					break;
				}
				case Types.VARCHAR: {
					String str = rs.getString(i);

					if (str != null) {
						PropertyUtils
								.setSimpleProperty(bean, propertyName, str);
					}
					break;
				}
				case Types.BIGINT: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Long(rs.getLong(i)));
					break;
				}
				case Types.CHAR: {
					String str = rs.getString(i);

					PropertyUtils.setSimpleProperty(bean, propertyName, str);
					break;
				}
				case Types.BOOLEAN: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Boolean(rs.getBoolean(i)));
					break;
				}
				case Types.REAL:
				case Types.FLOAT: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Float(rs.getFloat(i)));
					break;
				}
				case Types.DOUBLE: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Double(rs.getDouble(i)));
					break;
				}
				case Types.DECIMAL:
				case Types.NUMERIC: {
					if (des.getPropertyType().getName().equals("int")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Integer(rs.getInt(i)));
					} else if (des.getPropertyType().getName().equals("long")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Long(rs.getLong(i)));
					} else if (des.getPropertyType().getName().equals("float")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Float(rs.getFloat(i)));
					} else if (des.getPropertyType().getName().equals("short")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Short(rs.getShort(i)));
					} else if (des.getPropertyType().getName().equals("byte")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Byte(rs.getByte(i)));
					} else if (des.getPropertyType().getName().equals("double")) {
						PropertyUtils.setSimpleProperty(bean, propertyName,
								new Double(rs.getDouble(i)));
					} else {
						PropertyUtils.setSimpleProperty(bean, propertyName, rs
								.getObject(i));
					}
					break;
				}
				case Types.DATE:
				case Types.TIME:
				case Types.TIMESTAMP: {
					/*
					 * PropertyUtils.setSimpleProperty(bean, propertyName, rs
					 * .getTimestamp(i));
					 */
					Timestamp tmp = rs.getTimestamp(i);
					if (tmp != null) {
						if (des.getPropertyType().getName().equals("long")) {
							PropertyUtils.setSimpleProperty(bean, propertyName,
									new Long(tmp.getTime()));
						} else {
							PropertyUtils.setSimpleProperty(bean, propertyName,
									tmp);
						}
					}
					break;
				}
				case Types.TINYINT: {
					PropertyUtils.setSimpleProperty(bean, propertyName,
							new Short(rs.getShort(i)));
					break;
				}
				case Types.BLOB:
				case Types.BINARY:
				case Types.LONGVARBINARY: {
					PropertyUtils.setSimpleProperty(bean, propertyName, this
							.getBinaryData(rs.getBinaryStream(i)));
					break;
				}
				default: {
					String str = String.valueOf(rs.getObject(i));

					if (str != null) {
						PropertyUtils
								.setSimpleProperty(bean, propertyName, str);
					}
				}
				}
			}
		} catch (Exception ex) {
			throw new Exception("设置JavaBean [" + this.beanClass + "] 的属性 ["
					+ currentProperty + "] 失败:" + ex.getMessage());
		}
	}

	protected PropertyDescriptor getBeanPropertyDes(String name, Object bean)
			throws Exception {
		PropertyDescriptor[] des = PropertyUtils.getPropertyDescriptors(bean);
		for (int i = 0; i < des.length; i++) {
			if (des[i].getName().equalsIgnoreCase(name)) {
				return des[i];
			}
		}
		return null;
	}

	protected byte[] getBinaryData(InputStream in) throws Exception {
		if (in == null)
			return null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int tmp = -1;
		while ((tmp = in.read()) != -1) {
			bos.write(tmp);
		}
		return bos.toByteArray();
	}

	public boolean isAutoClose() {
		return true;
	}
}