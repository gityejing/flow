/**
 * @(#)CustomizePropertyAdapter.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

/**
 * 该接口负责对自定义类型的FlowProperty进行编辑
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public interface CustomizePropertyAdapter {

	/**
	 * 获取自定义属性的值
	 * @return
	 */
	Object getCustomizePropertyValue();
}
