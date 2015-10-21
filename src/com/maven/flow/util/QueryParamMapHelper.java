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
 * 对查询条件的处理提供一些通用的方法
 *
 * @author      kinz
 * @version     1.0 2007-6-12
 * @since       JDK1.5
 */

public class QueryParamMapHelper {

	/**
	 * 使用传入的keys和values创建一个Map以供使用
	 * 创建的Map的大小是keys、values两个数组中小的数组长度
	 * 如果keys、values两者中有一个为null或者长度为0，生成的Map的大小也为0
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
	 * 获取paramsMap中key与keys数组中每个值相同的一系列数据。
	 * 返回的是一个二维数组，如果Object[][]
	 * Object[][] tmp = new Object[][]{{"a","b","c"},{"1","2","TESt"}}；
	 * 结构如下：
	 * a 1
	 * b 2
	 * c TEST
	 * 如上，a、b、c是key，1、2、TEST是value,
	 * tmp[0]存储的是key
	 * tmp[1]存储的是value
	 * tmp[0][i]获取第i个key，tmp[1][i]获取第i个value
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
