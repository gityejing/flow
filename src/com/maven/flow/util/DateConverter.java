

package com.maven.flow.util;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


/**
 * ת��һ�� java.util.Date ����һ���ַ����Լ�
 * һ���ַ�����һ�� java.util.Date ����. 
 * ��BaseAction��ע�ᵽapache��BeanUtil��
 */
public class DateConverter implements Converter {
    private static DateFormat df =
        new SimpleDateFormat(DateUtil.getDatePattern());
	/**
	 * �������ڸ�ʽ����
	 * @param format
	 */
	public DateConverter(String format) {
		super();
		df =
	        new SimpleDateFormat(format);		
	}
    public Object convert(Class type, Object value) {
        if (value == null) {
            return null;
        } else if (type == Date.class) {
            return convertToDate(type, value);
        } else if (type == String.class) {
            return convertToString(type, value);
        }

        throw new ConversionException("Could not convert " +
                                      value.getClass().getName() + " to " +
                                      type.getName());
    }

    protected Object convertToDate(Class type, Object value) {
        if (value instanceof String) {
            try {
                if (StringUtils.isEmpty(value.toString())) {
                    return null;
                }

                return df.parse((String) value);
            } catch (Exception pe) {
                throw new ConversionException("Error converting String to Date");
            }
        }

        throw new ConversionException("Could not convert " +
                                      value.getClass().getName() + " to " +
                                      type.getName());
    }

    protected Object convertToString(Class type, Object value) {
        if (value instanceof Date) {
            try {
                return df.format(value);
            } catch (Exception e) {
                throw new ConversionException("Error converting Date to String");
            }
        }

        return value.toString();
    }
}