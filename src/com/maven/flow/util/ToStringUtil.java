/**
 * @(#)ToStringUtil.java 2007-8-3
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 封装对一些对象转换成String的方法
 *
 * @author      kinz
 * @version     1.0 2007-8-3
 * @since       JDK1.5
 */

public class ToStringUtil {

	/**
	 * 将一个JavaBean转换成一个String
	 * 转换规则：
	 * [class=类名, 属性名称1=属性值1, 属性名称2=属性值2 ... ]
	 * @param bean
	 * @return
	 */
	public static String beaToString(Object bean) throws Exception {
		if (bean == null)
			return "";

		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
		StringBuffer sb = new StringBuffer();
		Class clz = bean.getClass();

		if (isSimpleType(clz)) {
			sb.append(String.valueOf(bean));
		} else if (!isIgnoreType(clz)) {
			sb.append("[");
			sb.append("Class=");
			sb.append(clz.getName());
			for (int i = 0; i < descriptors.length; i++) {
				PropertyDescriptor des = descriptors[i];
				if (PropertyUtils.isReadable(bean, des.getName())) {
					sb.append(", ");
					sb.append(des.getName());
					sb.append("=");
					Object proValue = PropertyUtils.getSimpleProperty(bean, des.getName());
					if (isSimpleType(des.getPropertyType())) {
						sb.append(String.valueOf(proValue));
					} else if (proValue instanceof Map) {
						sb.append(mapToString((Map) proValue));
					} else if (proValue instanceof Collection) {
						sb.append(collectionToString((Collection) proValue));
					} else if (proValue instanceof Object[]) {
						sb.append(arrayToString((Object[]) proValue));
					} else {
						sb.append(beaToString(proValue));
					}
				}
			}
			sb.append("]");
		}

		return sb.toString();
	}

	/**
	 * 将一个数组转换成一个String
	 * 转换规则：
	 * @param array
	 * @return
	 */
	public static String arrayToString(Object[] array) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if(i > 0)
					sb.append(", ");
				sb.append(beaToString(array[i]));
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String collectionToString(Collection collection) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (collection != null) {
			int index = 0;
			for (Iterator iter = collection.iterator(); iter.hasNext();) {
				if(index > 0)
					sb.append(", ");
				sb.append(beaToString(iter.next()));
				
				index++;
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String mapToString(Map map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		if (map != null) {
			int index = 0;
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				if (index > 0)
					sb.append(", ");
				sb.append(beaToString(key));
				sb.append("=");
				sb.append(beaToString(map.get(key)));

				index++;
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 判断指定的Class是否简单类型，简单类型是指Java中的基本类型
	 * @param clz
	 * @return
	 */
	public static boolean isSimpleType(Class clz) {
		if (clz.equals(int.class))
			return true;
		if (clz.equals(Integer.class))
			return true;
		if (clz.equals(long.class))
			return true;
		if (clz.equals(Long.class))
			return true;
		if (clz.equals(byte.class))
			return true;
		if (clz.equals(Byte.class))
			return true;
		if (clz.equals(short.class))
			return true;
		if (clz.equals(Short.class))
			return true;
		if (clz.equals(boolean.class))
			return true;
		if (clz.equals(Boolean.class))
			return true;
		if (clz.equals(float.class))
			return true;
		if (clz.equals(Float.class))
			return true;
		if (clz.equals(double.class))
			return true;
		if (clz.equals(Double.class))
			return true;
		if (clz.equals(String.class))
			return true;

		return false;
	}

	/**
	 * 判断是否忽略类型
	 * @param clz
	 * @return
	 */
	public static boolean isIgnoreType(Class clz) {
		if (clz.equals(Class.class))
			return true;

		return false;
	}

	public static void main(String[] args) throws Exception {
		Map tmp = new HashMap();

		tmp.put("hello", new String("kinz"));
		tmp.put("you", "are");
		tmp.put("look this", new ArrayList());

		System.out.println(mapToString(tmp));
	}
}
