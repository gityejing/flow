/**
 * @(#)SequenceElement.java 2007-5-30
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-30
 * @since       JDK1.4
 */

public class SequenceElement extends FlowElementObject {

	{
		this.setName("Ë³Ðò");
		this.addProperty("role", "½ÇÉ«", FlowProperty.OPTION,1,0);
		this.addProperty("user", "ÓÃ»§", FlowProperty.OPTION,1,0);

		this.addListValue("user", new String[] { "Kinz", "Chris", "Dison",
				"Jumty", "Alphen", "Peter" });
		this.addListValue("role", new String[] { "Administrators", "Managers",
				"SA", "Boss" });
	}

	private String user = null;

	private String role = null;

	public String getIconResource() {
		return "resources/ps2_small.gif";
	}

	public String getImageResource() {
		return "resources/ps2_large.gif";
	}

	public Object getValue(String propertyName) {
		if ("user".equals(propertyName)) {
			return this.user;
		} else if ("role".equals(propertyName)) {
			return this.role;
		} else {
			return super.getValue(propertyName);
		}
	}

	public void setValue(String propertyName, Object value) {
		if ("name".equals(propertyName)) {
			this.setName(String.valueOf(value));
		} else if ("user".equals(propertyName)) {
			this.user = String.valueOf(value);
		} else if ("role".equals(propertyName)) {
			this.role = String.valueOf(value);
		}
		super.setValue(propertyName,value);
	}
}
