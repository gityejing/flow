/**
 * @(#)FlowGraph.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import org.jgraph.JGraph;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.GraphUndoManager;

import com.maven.flow.editor.model.GraphElement;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class FlowGraph extends JGraph {

	private static int newIndex = 1;

	private boolean changed = false;

	private GraphElement graphInfo = new GraphElement();

	// Undo Manager
	protected GraphUndoManager undoManager;

	// Construct the Graph using the Model as its Data Source
	public FlowGraph(GraphModel model) {
		this(model, null);
		//this(new FlowGraphModel(), null);
	}

	// Construct the Graph using the Model as its Data Source
	public FlowGraph(GraphModel model, GraphLayoutCache cache) {
		super(model, cache);
		// Make Ports Visible by Default
		setPortsVisible(true);
		// Use the Grid (but don't make it Visible)
		setGridEnabled(true);
		// Set the Grid Size to 10 Pixel
		setGridSize(6);
		// Set the Tolerance to 2 Pixel
		setTolerance(2);
		// Accept edits if click on background
		setInvokesStopCellEditing(true);
		// Allows control-drag
		setCloneable(true);
		// Jump to default port on connect
		setJumpToDefaultPort(true);

		this.graphInfo.setName("Î´ÃüÃû " + (newIndex++));
	}

	public GraphElement getGraphInfo() {
		return graphInfo;
	}

	public void setGraphInfo(GraphElement graphInfo) {
		this.graphInfo = graphInfo;
	}

	public String toString() {
		return this.graphInfo.getName();
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public GraphUndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(GraphUndoManager undoManager) {
		this.undoManager = undoManager;
	}

}
