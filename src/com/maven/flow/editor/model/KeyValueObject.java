/**
 * @(#)KeyValueObject.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

/**
 * 
 *
 * @author      kinz
 * @version     1.0 2007-6-6
 * @since       JDK1.4
 */

public class KeyValueObject implements Serializable{

	private Serializable key = null;

	private String name = null;

	public KeyValueObject(Serializable key, String name) {
		this.key = key;
		this.name = name;
	}

	public Serializable getKey() {
		return key;
	}

	public void setKey(Serializable key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof KeyValueObject) {
			return ((KeyValueObject) obj).getKey().equals(this.getKey());
		} else {
			return false;
		}
	}
}
