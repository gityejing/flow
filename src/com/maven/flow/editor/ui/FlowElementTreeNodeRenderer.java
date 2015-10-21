/**
 * @(#)FlowElementTreeNodeRenderer.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.Edge;

import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.editor.ui.FlowStructView.ElementTreeObject;

/**
 * 渲染流程节点在结构树中的展现
 *
 * @author      kinz
 * @version     1.0 2007-6-6
 * @since       JDK1.4
 */

public class FlowElementTreeNodeRenderer extends DefaultTreeCellRenderer {

	private Icon defaultLeafIcon = this.getLeafIcon();
	
	private Icon defaultOpenIcon = this.getOpenIcon();
	
	private Icon defaultCloseIcon = this.getClosedIcon();

	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object nodeObject = node.getUserObject();
			if (nodeObject instanceof Edge) {
				this.setLeafIcon(UIUtil.loadImageIcon("resources/link.gif"));
			} else if (nodeObject instanceof DefaultGraphCell) {
				FlowElementObject fo = (FlowElementObject) ((DefaultGraphCell) nodeObject)
						.getUserObject();
				if (fo.getIconResource() != null
						&& fo.getIconResource().trim().length() > 0) {
					this
							.setLeafIcon(UIUtil.loadImageIcon(fo
									.getIconResource()));
				}
			} else if (nodeObject instanceof FlowElementObject) {
				FlowElementObject fo = (FlowElementObject) nodeObject;
				if (fo.getIconResource() != null
						&& fo.getIconResource().trim().length() > 0) {
					this
							.setLeafIcon(UIUtil.loadImageIcon(fo
									.getIconResource()));
				}
			}else if(nodeObject instanceof FlowGraph){
				this.setOpenIcon(UIUtil.loadImageIcon("resources/editor.gif"));
				this.setClosedIcon(UIUtil.loadImageIcon("resources/editor.gif"));
			}else if(nodeObject instanceof ElementTreeObject){
				ElementTreeObject eto = (ElementTreeObject)nodeObject;
				if(eto.getType() == ElementTreeObject.CELL){
					this.setOpenIcon(UIUtil.loadImageIcon("resources/process_icon.gif"));
					this.setClosedIcon(UIUtil.loadImageIcon("resources/process_icon.gif"));
					this.setLeafIcon(UIUtil.loadImageIcon("resources/process_icon.gif"));
				}else if(eto.getType() == ElementTreeObject.EDGE){
					this.setOpenIcon(UIUtil.loadImageIcon("resources/link_icon.gif"));
					this.setClosedIcon(UIUtil.loadImageIcon("resources/link_icon.gif"));
					this.setLeafIcon(UIUtil.loadImageIcon("resources/link_icon.gif"));
				}else{
					//
				}
			}
			else {
				this.setLeafIcon(defaultLeafIcon);
				this.setOpenIcon(this.defaultOpenIcon);
				this.setClosedIcon(this.defaultCloseIcon);
			}
		} else {
			this.setLeafIcon(defaultLeafIcon);
			this.setOpenIcon(this.defaultOpenIcon);
			this.setClosedIcon(this.defaultCloseIcon);
		}
		return super.getTreeCellRendererComponent(tree, value, sel, expanded,
				leaf, row, hasFocus);
	}
}
