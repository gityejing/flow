/**
 * @(#)PropertyObserver.java 2007-5-30
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import com.maven.flow.editor.adapter.CustomizePropertyAdapter;


/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-30
 * @since       JDK1.4
 */

public interface PropertyObserver {

	public void setValue(String propertyName, Object value);

	public Object getValue(String propertyName);
	
	public Object[] getListValues(String propertyName);
	
	public CustomizePropertyAdapter getCustomizeAdapter(String propertyName);
}
