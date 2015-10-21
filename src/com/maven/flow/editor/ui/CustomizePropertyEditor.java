/**
 * @(#)CustomizePropertyEditor.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import com.maven.flow.editor.model.FlowProperty;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public class CustomizePropertyEditor extends ListPropertyEditor {

	public CustomizePropertyEditor(FlowProperty property) {
		super(property);
	}

	protected void setValue() {
		Object value = this.property.getCustomizeAdapter()
				.getCustomizePropertyValue();
		this.property.setValue(value);
	}

}
