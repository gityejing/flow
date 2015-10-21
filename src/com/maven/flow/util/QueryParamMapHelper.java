/**
 * @(#)QueryParamMapHelper.java 2007-6-12
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �Բ�ѯ�����Ĵ����ṩһЩͨ�õķ���
 *
 * @author      kinz
 * @version     1.0 2007-6-12
 * @since       JDK1.5
 */

public class QueryParamMapHelper {

	/**
	 * ʹ�ô����keys��values����һ��Map�Թ�ʹ��
	 * ������Map�Ĵ�С��keys��values����������С�����鳤��
	 * ���keys��values��������һ��Ϊnull���߳���Ϊ0�����ɵ�Map�Ĵ�СҲΪ0
	 * @param keys
	 * @param values
	 * @return
	 */
	public static Map createParamMap(String[] keys, Object[] values) {
		Map paramMap = new HashMap();

		if (keys != null && values != null) {
			for (int i = 0; i < keys.length && i < values.length; i++) {
				paramMap.put(keys[i], values[i]);
			}
		}

		return paramMap;
	}

	/**
	 * ��ȡparamsMap��key��keys������ÿ��ֵ��ͬ��һϵ�����ݡ�
	 * ���ص���һ����ά���飬���Object[][]
	 * Object[][] tmp = new Object[][]{{"a","b","c"},{"1","2","TESt"}}��
	 * �ṹ���£�
	 * a 1
	 * b 2
	 * c TEST
	 * ���ϣ�a��b��c��key��1��2��TEST��value,
	 * tmp[0]�洢����key
	 * tmp[1]�洢����value
	 * tmp[0][i]��ȡ��i��key��tmp[1][i]��ȡ��i��value
	 * @param keys
	 * @param paramsMap
	 * @return
	 */
	public static Object[][] getMatchParams(String[] keys, Map paramsMap) {
		Object[][] result = new Object[2][];

		List tmpKeys = new ArrayList();
		List tmpValues = new ArrayList();

		for (int i = 0; i < keys.length; i++) {
			if (paramsMap.containsKey(keys[i])) {
				tmpKeys.add(keys[i]);
				tmpValues.add(paramsMap.get(keys[i]));
			}
		}

		result[0] = (String[]) tmpKeys.toArray(new String[tmpKeys.size()]);
		result[1] = tmpValues.toArray();

		return result;
	}
}
