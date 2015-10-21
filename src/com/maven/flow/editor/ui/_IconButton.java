package com.maven.flow.editor.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;

import com.maven.flow.editor.model.ApprovalProcessObject;
import com.maven.flow.editor.model.EndElement;
import com.maven.flow.editor.model.HandleProcessObject;
import com.maven.flow.editor.model.StartElement;

public class _IconButton extends JButton implements ActionListener {

	private WorkFlowEditor editor = null;

	private int FlowElementType;

	_IconButton(WorkFlowEditor editor, int FlowElementType, Icon icon) {
		super(icon);
		this.editor = editor;
		this.FlowElementType = FlowElementType;
		this.setPreferredSize(new Dimension(25, 25));
		this.setMinimumSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
		this.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (FlowElementType == 0) {
			editor.graphEditor.insertFlowElement(new StartElement());
		} else if (FlowElementType == 1) {
			editor.graphEditor.insertFlowElement(new EndElement());
		} else if (FlowElementType == 2) {
			editor.graphEditor.insertFlowElement(new HandleProcessObject());
		} else if (FlowElementType == 3) {
			editor.graphEditor.insertFlowElement(new ApprovalProcessObject());
		}

	}

}