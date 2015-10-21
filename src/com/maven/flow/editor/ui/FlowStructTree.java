/**
 * @(#)FlowStructTree.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.jgraph.graph.GraphCell;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class FlowStructTree extends JTree implements MouseListener {

    public FlowStructTree() {

        this.addMouseListener(this);

        //        this.setRootVisible(false);

        this.setToggleClickCount(3);
    }

    public void mouseClicked(MouseEvent e) {
        TreePath path = this.getSelectionPath();
        if (path == null)
            return;
        
        if (path.getPathCount() < 3){
            return;
        }
        DefaultMutableTreeNode graphNode = (DefaultMutableTreeNode) path
                .getPathComponent(1);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
                .getLastPathComponent();

        if (node == null)
            return;
        Object graph = graphNode.getUserObject();
        Object obj = node.getUserObject();
        if (e.getClickCount() == 2) {
            if (obj instanceof FlowGraph) {
                //双击，打开流程图
                FlowGraphManager.setCurrentGraph((FlowGraph) obj);
            } else if (obj instanceof GraphCell) {
                if ((graph instanceof FlowGraph)
                        && (!graph.equals(FlowGraphManager.getCurrentGraph()))) {
                    FlowGraphManager.setCurrentGraph((FlowGraph) graph);
                    FlowGraphManager.setSelectedCell(obj);
                }
            }
        } else if (e.getClickCount() == 1) {
            if (graphNode == null || graphNode.getUserObject() == null)
                return;

            FlowGraphManager.setSelectedCell(obj);
        }
    }

    public void mouseEntered(MouseEvent e) {
        // TODO 自动生成方法存根

    }

    public void mouseExited(MouseEvent e) {
        // TODO 自动生成方法存根

    }

    public void mousePressed(MouseEvent e) {
        // TODO 自动生成方法存根

    }

    public void mouseReleased(MouseEvent e) {
        // TODO 自动生成方法存根

    }

}