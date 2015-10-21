/**
 * @(#)FlowEdgeInfo.java 2007-6-5
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.io.Serializable;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultPort;

import com.maven.flow.editor.model.FlowEdgeObject;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-5
 * @since       JDK1.4
 */

public class FlowEdgeInfo implements Serializable {

	private int lineEnd = 0;

	private boolean labelAlongEdge = false;

	private boolean endFill = false;

	private float lineWidth = 0;

	private int colorRGB = 0;

	private FlowCellInfo source = null;

	private FlowCellInfo target = null;

	private double[][] points = null;

	private boolean editable = false;

	private FlowEdgeObject edgeObject = null;
	
	private byte[] bytes;
	
	private DefaultEdge psedge;
	
	private Object sourceObject=null;
	
	private Object targetObject=null;
	
	
	private Object sourcePort=null;
	
	private Object targetPort=null;


	public Object getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(Object sourcePort) {
		this.sourcePort = sourcePort;
	}

	public Object getTargetPort() {
		return targetPort;
	}

	public void setTargetPort(Object targetPort) {
		this.targetPort = targetPort;
	}

	public Object getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(Object sourceObject) {
		this.sourceObject = sourceObject;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	public DefaultEdge getPsedge() {
		return psedge;
	}

	public void setPsedge(DefaultEdge psedge) {
		this.psedge = psedge;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public FlowCellInfo getSource() {
		return source;
	}

	public void setSource(FlowCellInfo source) {
		this.source = source;
	}

	public FlowCellInfo getTarget() {
		return target;
	}

	public void setTarget(FlowCellInfo target) {
		this.target = target;
	}

	public int getColorRGB() {
		return colorRGB;
	}

	public void setColorRGB(int colorRGB) {
		this.colorRGB = colorRGB;
	}

	public boolean isEndFill() {
		return endFill;
	}

	public void setEndFill(boolean endFill) {
		this.endFill = endFill;
	}

	public boolean isLabelAlongEdge() {
		return labelAlongEdge;
	}

	public void setLabelAlongEdge(boolean labelAlongEdge) {
		this.labelAlongEdge = labelAlongEdge;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public float getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public double[][] getPoints() {
		return points;
	}

	public void setPoints(double[][] points) {
		this.points = points;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public FlowEdgeObject getEdgeObject() {
		return edgeObject;
	}

	public void setEdgeObject(FlowEdgeObject edgeObject) {
		this.edgeObject = edgeObject;
	}

}
