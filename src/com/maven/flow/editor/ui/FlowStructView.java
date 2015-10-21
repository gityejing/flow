/**
 * @(#)FlowStructView.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphCell;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class FlowStructView extends JPanel {

    private WorkFlowEditor editor = null;

    public FlowStructTree structTree = null;

    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("流程图结构");

    private DefaultMutableTreeNode validRoot = new DefaultMutableTreeNode(
            "有效的流程");

    private DefaultMutableTreeNode inValidRoot = new DefaultMutableTreeNode(
            "暂存的流程");

    private DefaultTreeModel model = new DefaultTreeModel(root);

    private FlowElementTreeNodeRenderer renderer = new FlowElementTreeNodeRenderer();

    private HashMap cellNodeMap = new HashMap();

    public FlowStructView(WorkFlowEditor editor) {
        this.editor = editor;

        this.setPreferredSize(new Dimension(140, 350));
        this.setMinimumSize(new Dimension(140, 200));

        this.setLayout(new BorderLayout());
        JLabel l = new JLabel("结构视图");
        l.setIcon(UIUtil.loadImageIcon("resources/structview.gif"));
        this.add(l, BorderLayout.NORTH);

        this.structTree = new FlowStructTree();

        this.structTree.setModel(model);
        this.structTree.setCellRenderer(renderer);
//        this.structTree.setRootVisible(false);

        this.add(new JScrollPane(structTree), BorderLayout.CENTER);

        root.add(validRoot);
        root.add(inValidRoot);
    }

    public void refresh() {
        //this.structTree.refresh();

        this.structTree.updateUI();
    }

    public void updateGraphNode(FlowGraph graph) {
        if (graph == null)
            return;

        DefaultMutableTreeNode node = this.getGraphNode(graph);

        if(node == null)
        	return;
        
        if(graph.getGraphInfo().getValidStatus() == 1){
            if (!this.validRoot.equals(node.getParent())) {
                node.removeFromParent();
                this.validRoot.add(node);
            }
        }else{
            if (!this.inValidRoot.equals(node.getParent())) {
                node.removeFromParent();
                this.inValidRoot.add(node);
            }
        }
        /*
        if (graph.getGraphInfo().isDeployed()) {
            if (!this.validRoot.equals(node.getParent())) {
                node.removeFromParent();
                this.validRoot.add(node);
            }
        } else {
            if (!this.inValidRoot.equals(node.getParent())) {
                node.removeFromParent();
                this.inValidRoot.add(node);
            }
        }
        */

        this.refresh();
    }

    /**
     * 将一个图形加到结构树中
     * @param graph
     */
    public void addGraph(FlowGraph graph) {
        DefaultMutableTreeNode tmp = null;
        if(graph.getGraphInfo().getValidStatus() == 1){
        	tmp = this.validRoot;
        }else{
        	tmp = this.inValidRoot;
        }
        /*
        if (graph.getGraphInfo().isDeployed())
            tmp = this.deployedRoot;
        else
            tmp = this.unDeployedRoot;
        */

        DefaultMutableTreeNode graphNode = this.createGraphNode(graph);
        tmp.add(graphNode);

        //添加节点
        Object[] cells = graph.getGraphLayoutCache().getCells(false, true,
                false, false);
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] instanceof GraphCell) {
                this.addFlowCell(graphNode, (GraphCell) cells[i]);
            }
        }

        //添加连线
        Object[] edges = graph.getGraphLayoutCache().getCells(false, false,
                false, true);
        for (int i = 0; i < edges.length; i++) {
            if (edges[i] instanceof Edge) {
                this.addFlowRoute(graphNode, (Edge) edges[i]);
            }
        }

        this.refresh();
    }

    /**
     * 将指定的图形从结构树中删除
     * @param graph
     */
    public void removeGraph(FlowGraph graph) {
        DefaultMutableTreeNode node = this.getGraphNode(graph);
        if (node == null)
            return;
        node.removeFromParent();
        //root.remove(node);

        this.refresh();
    }

    /**
     * 将流程元素加到结构树中
     * @param cell
     */
    public void addFlowCell(FlowGraph graph, GraphCell cell) {
        this.addFlowCell(this.getGraphNode(graph),cell);
    }
    
    private void addFlowCell(DefaultMutableTreeNode graphNode,GraphCell cell){
        if(this.cellNodeMap.containsKey(cell))
            return;
        
        DefaultMutableTreeNode cn = this.getCellRoot(graphNode);
        if(cn == null)
            return;
        
        //获取Cell节点
        DefaultMutableTreeNode n = this.createCellNode(cell);
        cn.add(n);
        
        //缓存
        this.cellNodeMap.put(cell,n);
        
        this.refresh();
    }

    /**
     * 从结构树中删除指定的流程环节
     * @param cell
     */
    public void removeFlowCell(FlowGraph graph, GraphCell cell) {
        if (!this.cellNodeMap.containsKey(cell))
            return;
        DefaultMutableTreeNode node = this.getCellRoot(graph);
        node.remove((DefaultMutableTreeNode) this.cellNodeMap.get(cell));
        this.cellNodeMap.remove(cell);

        this.refresh();
    }

    /**
     * 将流程路径加到结构树中
     * @param edge
     */
    public void addFlowRoute(FlowGraph graph, Edge edge) {
        this.addFlowRoute(this.getGraphNode(graph),edge);
    }
    
    private void addFlowRoute(DefaultMutableTreeNode graphNode,Edge edge){
        //如果已经存在，不再进行添加
        if (this.cellNodeMap.containsKey(edge))
            return;

        //获取Edge节点
        DefaultMutableTreeNode en = this.getEdgeRoot(graphNode);
        if (en == null)
            return;

        DefaultMutableTreeNode n = this.createEdgeNode(edge);

        en.add(n);

        //缓存
        this.cellNodeMap.put(edge, n);

        this.refresh();
    }

    /**
     * 从结构树中删除流程路径
     * @param edge
     */
    public void removeFlowRoute(FlowGraph graph, Edge edge) {
        if (!this.cellNodeMap.containsKey(edge))
            return;
        DefaultMutableTreeNode node = this.getEdgeRoot(graph);
        node.remove((DefaultMutableTreeNode) this.cellNodeMap.get(edge));

        this.cellNodeMap.remove(edge);

        this.refresh();
    }

    /**
     * 获取当前编辑图形对应结构树上的节点
     * @return
     */
    private DefaultMutableTreeNode getCurrentGraphNode() {
        return this.getGraphNode(FlowGraphManager.getCurrentGraph());
    }

    /**
     * 获取指定图形对应结构树上的节点
     * @param graph
     * @return
     */
    private DefaultMutableTreeNode getGraphNode(FlowGraph graph) {
        if (graph == null)
            return null;

        int childCount = validRoot.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) validRoot
                    .getChildAt(i);
            if (n.getUserObject().equals(graph)) {
                return n;
            }
        }
        
        childCount = inValidRoot.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode n = (DefaultMutableTreeNode) inValidRoot
                    .getChildAt(i);
            if (n.getUserObject().equals(graph)) {
                return n;
            }
        }
        
        return null;
    }

    /**
     * 获取当前编辑流程图对应结构树上的Cell节点
     * @return
     */
    private DefaultMutableTreeNode getCellRoot(FlowGraph graph) {
        DefaultMutableTreeNode n = this.getGraphNode(graph);

        return this.getCellRoot(n);
    }

    private DefaultMutableTreeNode getCellRoot(DefaultMutableTreeNode graphNode) {
        if (graphNode == null)
            return null;

        int childCount = graphNode.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode c = (DefaultMutableTreeNode) graphNode
                    .getChildAt(i);
            Object o = c.getUserObject();
            if (o instanceof ElementTreeObject) {
                if (((ElementTreeObject) o).getType() == ElementTreeObject.CELL) {
                    return c;
                }
            }
        }

        return null;
    }

    /**
     * 获取当前编辑流程图对应结构树上的Edge节点
     * @return
     */
    private DefaultMutableTreeNode getEdgeRoot(FlowGraph graph) {
        DefaultMutableTreeNode n = this.getGraphNode(graph);
        return this.getEdgeRoot(n);
    }

    private DefaultMutableTreeNode getEdgeRoot(DefaultMutableTreeNode graphNode) {
        if (graphNode == null)
            return null;

        int childCount = graphNode.getChildCount();
        for (int i = 0; i < childCount; i++) {
            DefaultMutableTreeNode c = (DefaultMutableTreeNode) graphNode
                    .getChildAt(i);
            Object o = c.getUserObject();
            if (o instanceof ElementTreeObject) {
                if (((ElementTreeObject) o).getType() == ElementTreeObject.EDGE) {
                    return c;
                }
            }
        }

        return null;
    }

    /**
     * 创建一个流程的树节点
     * @param graph
     * @return
     */
    private DefaultMutableTreeNode createGraphNode(FlowGraph graph) {
        DefaultMutableTreeNode n = new DefaultMutableTreeNode(graph);

        DefaultMutableTreeNode cn = new DefaultMutableTreeNode(
                new ElementTreeObject("环节", ElementTreeObject.CELL));
        DefaultMutableTreeNode en = new DefaultMutableTreeNode(
                new ElementTreeObject("路径", ElementTreeObject.EDGE));

        n.add(cn);
        n.add(en);
        return n;
    }

    /**
     * 创建Cell结构树节点
     * @param cell
     * @return
     */
    private DefaultMutableTreeNode createCellNode(GraphCell cell) {
        return new DefaultMutableTreeNode(cell);
    }

    /**
     * 创建连线结构树节点
     * @param edge
     * @return
     */
    private DefaultMutableTreeNode createEdgeNode(Edge edge) {
        return new DefaultMutableTreeNode(edge);
    }

    class ElementTreeObject {
        final static int CELL = 1;

        final static int EDGE = 2;

        private String name = "";

        private int type = CELL;

        public ElementTreeObject(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String toString() {
            return this.name;
        }
    }
}