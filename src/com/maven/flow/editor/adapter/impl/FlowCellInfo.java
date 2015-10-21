/**
 * @(#)FlowElementObjectInfo.java 2007-6-5
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.io.Serializable;

import com.maven.flow.editor.model.FlowElementObject;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-5
 * @since       JDK1.4
 */

public class FlowCellInfo implements Serializable {

	private boolean autoSize = false;

	private double width = 0;

	private double height = 0;

	private double x = 0;

	private double y = 0;

	private boolean editable = false;

	private FlowElementObject flowObject = null;
		
	private int processId;
	
	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public boolean isAutoSize() {
		return autoSize;
	}

	public void setAutoSize(boolean autoSize) {
		this.autoSize = autoSize;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public FlowElementObject getFlowObject() {
		return flowObject;
	}

	public void setFlowObject(FlowElementObject flowObject) {
		this.flowObject = flowObject;
	}

}
