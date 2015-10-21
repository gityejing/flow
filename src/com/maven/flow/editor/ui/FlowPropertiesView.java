/**
 * @(#)FlowPropertiesView.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgraph.graph.DefaultGraphCell;

import com.maven.flow.editor.model.FlowProperty;
import com.maven.flow.editor.model.PropertiesTableModel;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class FlowPropertiesView extends JPanel {

	private WorkFlowEditor editor = null;

	private PropertiesTable table = null;

	private PropertiesTableModel model = null;
	
	//流程步骤属性视图
	public FlowPropertiesView(WorkFlowEditor editor) {
		this.editor = editor;
		
		model = new PropertiesTableModel(editor.graphEditor);

		table = new PropertiesTable(model);

		this.setPreferredSize(new Dimension(180, 0));// 定义属性框的大小.
		
		this.setLayout(new BorderLayout());
		// JLabel l = new JLabel("属性视图");
		// l.setIcon(UIUtil.loadImageIcon("resources/propertiesview.gif"));
		// this.add(l, BorderLayout.NORTH);//上边.

		this.add(new JScrollPane(table), BorderLayout.CENTER);// 中间.
	}

	public void resetProperties(FlowProperty[] properties) {
		table.removeEditor();
		model.resetProperties(properties);
	}
	
}
