/**
 * @(#)FlowGraphManager.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jgraph.graph.BasicMarqueeHandler;

import com.maven.flow.editor.SystemGloable;
import com.maven.flow.editor.adapter.FlowDeployer;
import com.maven.flow.editor.adapter.FlowGraphSourceAdapter;
import com.maven.flow.editor.adapter.impl.FileAdapter;
import com.maven.flow.editor.adapter.impl.FlowServletAdapter;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class FlowGraphManager {

	private static FlowGraphSourceAdapter fileAdapter = new FileAdapter();

	private static List allGraphs = new ArrayList();

	private static FlowGraph currentGraph = null;

	private static WorkFlowEditor editor = null;

	/**
	 * 装载流程图
	 * 
	 */
	
	public static void importGraph() {

		FlowGraph g = fileAdapter.load();

		editor.graphEditor.installListeners(g);
		g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

		// editor.graphEditor.setFlowGraph(g);
		setCurrentGraph(g);

		// 添加到结构树
		editor.strutcView.addGraph(g);
	}

	public static void loadFromDb()
	{
		
	}
	/**
	 * 导出当前编辑的图形
	 * 
	 */
	public static void exportCurrentGraph() {
		exportGraph(getCurrentGraph());
	}

	/**
	 * 导出指定的图形
	 * 
	 * @param graph
	 */
	public static void exportGraph(FlowGraph graph) {
		if (graph == null)
			return;
		editor.graphEditor.uninstallListeners(graph);
		BasicMarqueeHandler marquee = graph.getMarqueeHandler();
		graph.setMarqueeHandler(null);
		fileAdapter.save(graph);
		editor.graphEditor.installListeners(graph);
		graph.setMarqueeHandler(marquee);
	}

	/**
	 * 保存当前编辑的流程图
	 * 
	 */
	public static void saveCurrentGraph() {
		saveGraph(getCurrentGraph());
	}

	/**
	 * 另存当前流程图
	 */
	public static void saveCurrentGraphAs() {
		saveGraphAs(getCurrentGraph());
	}

	/**
	 * 保存指定的流程图
	 * 
	 * @param graph
	 */
	public static void saveGraph(FlowGraph graph) {
		//
		new FlowServletAdapter(editor).save(graph);
	}

	public static void saveGraphAs(FlowGraph graph) {
		new FlowServletAdapter(editor).saveAs(graph);

		editor.graphEditor.setFlowGraph(graph);
	}

	/**
	 * 打开流程图
	 * 
	 */
	public static void openGraph() {
		FlowGraph g = new FlowServletAdapter(editor).load();
		if (g != null) {

			editor.graphEditor.installListeners(g);
			g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

			// editor.graphEditor.setFlowGraph(g);
			setCurrentGraph(g);

			// 添加到结构树
			editor.strutcView.addGraph(g);
		}
	}

	public static void openGraph(long appId) {
		FlowGraph g = new FlowServletAdapter(editor)
				.load(String.valueOf(appId));
		if (g != null) {

			editor.graphEditor.installListeners(g);// 编辑器视图里装载新的图像.

			g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

			// editor.graphEditor.setFlowGraph(g);
			setCurrentGraph(g);// 设置为当前加载到的流程.

			// 添加到结构树
			editor.strutcView.addGraph(g);// 打开一个工作流后,其结构视图也会发生改变.
		}
	}

	/**
	 * 发布当前编辑的流程图
	 * 
	 */
	public static void deployCurrentGraph() {
		deployGraph(getCurrentGraph());
	}

	/**
	 * 发布指定的流程图
	 * 
	 * @param graph
	 */
	public static void deployGraph(FlowGraph graph) {
		new FlowDeployer(editor).deployFlow(graph);
	}

	/**
	 * 获取当前正在编辑的流程图
	 * 
	 * @return 当前正在编辑的流程图
	 */
	public static FlowGraph getCurrentGraph() {
		return currentGraph;
	}

	/**
	 * 添加新的流程图
	 * 
	 */
	public static void addNewGraph() {
		FlowGraph graph = new FlowGraph(new FlowGraphModel());

		graph.getGraphInfo().setCreateTime(new Date());
		graph.getGraphInfo().setCreator(""+SystemGloable.CurUserInfo.getFemployeeSn());
		graph.getGraphInfo().setCreatorName(SystemGloable.CurUserInfo.getFemployeeName());

		allGraphs.add(graph);

		// 新的流程图，设置为当前编辑流程图
		setCurrentGraph(graph);

		// editor.strutcView.refresh();
		//editor.strutcView.addGraph(graph);
	}

	/**
	 * 设置当前正在编辑的流程图
	 * 
	 * @param graph
	 */
	public static void setCurrentGraph(FlowGraph graph) {
		currentGraph = graph;

		editor.graphEditor.setFlowGraph(currentGraph);

		if (!allGraphs.contains(graph)) {
			allGraphs.add(graph);
		}
	}

	/**
	 * 获取所有的流程图
	 * 
	 * @return
	 */
	public static FlowGraph[] getAllFlowGraphs() {
		return (FlowGraph[]) allGraphs.toArray(new FlowGraph[allGraphs.size()]);
	}

	/**
	 * 设置当前选中的单元
	 * 
	 * @param cell
	 */
	public static void setSelectedCell(Object cell) {
		if (getCurrentGraph() == null)
			return;
		getCurrentGraph().setSelectionCell(cell);
		editor.graphEditor.updateView();
	}

	/**
	 * 关闭当前编辑图形
	 * 
	 */
	public static void closeCurrentGraph() {
		closeGraph(getCurrentGraph());
	}

	/**
	 * 关闭指定的图形
	 * 
	 * @param graph
	 */
	public static void closeGraph(FlowGraph graph) {
		if (graph == null)
			return;
		// 先删除图形
		allGraphs.remove(graph);
		// 从图形编辑器中删除
		// editor.graphEditor.setFlowGraph(null);
		setCurrentGraph(null);
		editor.propertiesView.resetProperties(null);

		// 删除树结构中的图形
		editor.strutcView.removeGraph(graph);

		// 打开第一个图形，如果没有则不打开
		if (allGraphs.size() > 0) {
			setCurrentGraph((FlowGraph) allGraphs.get(0));
		}
	}

	/**
	 * 刷新图形
	 * 
	 */
	public static void refreshGraph() {
		editor.graphEditor.updateView();
	}

	public static WorkFlowEditor getEditor() {
		return editor;
	}

	protected static void setEditor(WorkFlowEditor editor) {
		FlowGraphManager.editor = editor;
	}
}