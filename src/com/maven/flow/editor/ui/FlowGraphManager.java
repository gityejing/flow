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
	 * װ������ͼ
	 * 
	 */
	
	public static void importGraph() {

		FlowGraph g = fileAdapter.load();

		editor.graphEditor.installListeners(g);
		g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

		// editor.graphEditor.setFlowGraph(g);
		setCurrentGraph(g);

		// ��ӵ��ṹ��
		editor.strutcView.addGraph(g);
	}

	public static void loadFromDb()
	{
		
	}
	/**
	 * ������ǰ�༭��ͼ��
	 * 
	 */
	public static void exportCurrentGraph() {
		exportGraph(getCurrentGraph());
	}

	/**
	 * ����ָ����ͼ��
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
	 * ���浱ǰ�༭������ͼ
	 * 
	 */
	public static void saveCurrentGraph() {
		saveGraph(getCurrentGraph());
	}

	/**
	 * ��浱ǰ����ͼ
	 */
	public static void saveCurrentGraphAs() {
		saveGraphAs(getCurrentGraph());
	}

	/**
	 * ����ָ��������ͼ
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
	 * ������ͼ
	 * 
	 */
	public static void openGraph() {
		FlowGraph g = new FlowServletAdapter(editor).load();
		if (g != null) {

			editor.graphEditor.installListeners(g);
			g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

			// editor.graphEditor.setFlowGraph(g);
			setCurrentGraph(g);

			// ��ӵ��ṹ��
			editor.strutcView.addGraph(g);
		}
	}

	public static void openGraph(long appId) {
		FlowGraph g = new FlowServletAdapter(editor)
				.load(String.valueOf(appId));
		if (g != null) {

			editor.graphEditor.installListeners(g);// �༭����ͼ��װ���µ�ͼ��.

			g.setMarqueeHandler(new FlowMarqueeHandler(editor.graphEditor));

			// editor.graphEditor.setFlowGraph(g);
			setCurrentGraph(g);// ����Ϊ��ǰ���ص�������.

			// ��ӵ��ṹ��
			editor.strutcView.addGraph(g);// ��һ����������,��ṹ��ͼҲ�ᷢ���ı�.
		}
	}

	/**
	 * ������ǰ�༭������ͼ
	 * 
	 */
	public static void deployCurrentGraph() {
		deployGraph(getCurrentGraph());
	}

	/**
	 * ����ָ��������ͼ
	 * 
	 * @param graph
	 */
	public static void deployGraph(FlowGraph graph) {
		new FlowDeployer(editor).deployFlow(graph);
	}

	/**
	 * ��ȡ��ǰ���ڱ༭������ͼ
	 * 
	 * @return ��ǰ���ڱ༭������ͼ
	 */
	public static FlowGraph getCurrentGraph() {
		return currentGraph;
	}

	/**
	 * ����µ�����ͼ
	 * 
	 */
	public static void addNewGraph() {
		FlowGraph graph = new FlowGraph(new FlowGraphModel());

		graph.getGraphInfo().setCreateTime(new Date());
		graph.getGraphInfo().setCreator(""+SystemGloable.CurUserInfo.getFemployeeSn());
		graph.getGraphInfo().setCreatorName(SystemGloable.CurUserInfo.getFemployeeName());

		allGraphs.add(graph);

		// �µ�����ͼ������Ϊ��ǰ�༭����ͼ
		setCurrentGraph(graph);

		// editor.strutcView.refresh();
		//editor.strutcView.addGraph(graph);
	}

	/**
	 * ���õ�ǰ���ڱ༭������ͼ
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
	 * ��ȡ���е�����ͼ
	 * 
	 * @return
	 */
	public static FlowGraph[] getAllFlowGraphs() {
		return (FlowGraph[]) allGraphs.toArray(new FlowGraph[allGraphs.size()]);
	}

	/**
	 * ���õ�ǰѡ�еĵ�Ԫ
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
	 * �رյ�ǰ�༭ͼ��
	 * 
	 */
	public static void closeCurrentGraph() {
		closeGraph(getCurrentGraph());
	}

	/**
	 * �ر�ָ����ͼ��
	 * 
	 * @param graph
	 */
	public static void closeGraph(FlowGraph graph) {
		if (graph == null)
			return;
		// ��ɾ��ͼ��
		allGraphs.remove(graph);
		// ��ͼ�α༭����ɾ��
		// editor.graphEditor.setFlowGraph(null);
		setCurrentGraph(null);
		editor.propertiesView.resetProperties(null);

		// ɾ�����ṹ�е�ͼ��
		editor.strutcView.removeGraph(graph);

		// �򿪵�һ��ͼ�Σ����û���򲻴�
		if (allGraphs.size() > 0) {
			setCurrentGraph((FlowGraph) allGraphs.get(0));
		}
	}

	/**
	 * ˢ��ͼ��
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