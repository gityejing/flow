/**
 * @(#)PropertyCopier.java 2007-6-5
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-5
 * @since       JDK1.4
 */

public class PropertyCopier {

	/**
	 * ����Դ��������Ե�Ŀ���������ԡ�������������ͣ�������Դ����ĸ�ʽ��ת����String
	 * 
	 * @param target
	 * @param source
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */

	public static void copyProperties(Object target, Object source) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {

		// HashMap datePropertiesMap = new HashMap();
		PropertyDescriptor[] properties = PropertyUtils.getPropertyDescriptors(source);
		for (int i = 0; i < properties.length; i++) {
			Class cls = properties[i].getPropertyType();
			String propertyName = properties[i].getName();
			PropertyDescriptor targetProperty = null;

			targetProperty = PropertyUtils.getPropertyDescriptor(target, propertyName);

			if (targetProperty != null && targetProperty.getPropertyType().equals(String.class) && cls.equals(Date.class)) {
				// ����תΪ�ַ���
				BeanUtils.setProperty(target, propertyName, new DateConverter("yyyy-MM-dd").convert(String.class, PropertyUtils
						.getSimpleProperty(source, propertyName)));
				/*
				 * datePropertiesMap.put(propertyName,
				 * PropertyUtils.getProperty(source, propertyName));
				 */

			} else if (targetProperty != null && targetProperty.getPropertyType().equals(Date.class) && cls.equals(String.class)) {
				// �ַ���תΪ����
				BeanUtils.setProperty(target, propertyName, new DateConverter("yyyy-MM-dd").convert(Date.class, BeanUtils.getProperty(
						source, propertyName)));
			} else if (targetProperty != null) {
				// ������ͨ������
				try{
					BeanUtils.setProperty(target, propertyName, BeanUtils.getProperty(source, propertyName));
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
