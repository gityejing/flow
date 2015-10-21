/**
 * @(#)GraphEditor.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.JGraph;
import org.jgraph.event.GraphLayoutCacheEvent;
import org.jgraph.event.GraphLayoutCacheListener;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.event.GraphModelEvent.GraphModelChange;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.Port;

import com.maven.flow.editor.model.FlowEdgeObject;
import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.service.IDService;

/**
 * �������༭������ɻ�ͼ�Ĺ���
 * 
 * @author Letinma
 * @version 1.0 2009-11-15
 * @since JDK1.4
 */

public class GraphEditor extends JPanel implements GraphSelectionListener,
		KeyListener, GraphModelListener, GraphLayoutCacheListener {
	
	// �����������
	private WorkFlowEditor editor = null;
	
	// ��ͼ����
	protected FlowGraph graph = null;
	
	
	// Undo Manager
	protected GraphUndoManager undoManager;

	protected FlowMarqueeHandler marqueeHandler = new FlowMarqueeHandler(this);//��ͼ������
	
	private JScrollPane scroller = new JScrollPane();//
	
	public GraphEditor(WorkFlowEditor editor) {
		this.editor = editor;
		
		this.init();
	}
	
	/**
	 * ��ʼ���༭��
	 * 
	 */
	protected void init() {
		this.setLayout(new BorderLayout());//�Ű淽ʽ

		// JPanel panel = new JPanel(new GridLayout(1, 2));
		// JLabel l = new JLabel("���������");
		// l.setIcon(UIUtil.loadImageIcon("resources/editor.gif"));
		// panel.add(l);

		// JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		// Action action = new AbstractAction("", UIUtil
		// .loadImageIcon("resources/close.gif")) {
		// public void actionPerformed(ActionEvent e) {
		// FlowGraphManager.closeCurrentGraph();
		// }
		//
		// };
		// action.putValue(Action.SHORT_DESCRIPTION, "�رյ�ǰ����ͼ");
		// JButton clsBtn = new JButton(action);
		// clsBtn.setBorder(new LineBorder(Color.GRAY));
		// l.setLayout(new FlowLayout(FlowLayout.RIGHT));
		//
		// closePanel.add(clsBtn);

		// panel.add(closePanel);

		// this.add(panel, BorderLayout.NORTH);
		this.add(scroller, BorderLayout.CENTER);//������
		// installRepaintListener();
		// this.add(new JScrollPane(graph));

	}

	/**
	 * �¼�������
	 */
	public class EventRedirector extends AbstractAction {

		protected Action action;

		// Construct the "Wrapper" Action
		public EventRedirector(Action a) {
			super("", (ImageIcon) a.getValue(Action.SMALL_ICON));
			this.action = a;
		}

		// Redirect the Actionevent
		public void actionPerformed(ActionEvent e) {
			e = new ActionEvent(graph, e.getID(), e.getActionCommand(), e
					.getModifiers());
			action.actionPerformed(e);
		}
	}

	/**
	 * ������������ͼ
	 * 
	 * @param newGraph
	 */
	public void setFlowGraph(FlowGraph newGraph) {
		if (newGraph == null) {
			if (graph != null)
				graph.getParent().remove(graph);
			this.graph = null;
			this.undoManager = null;

			this.updateUI();
			return;
		}
		if (this.graph != null) {
			scroller.remove(graph);
			// uninstallListeners(graph);
		}

		graph = newGraph;

		// ����UnDoManager
		if (graph.getUndoManager() == null) {
			undoManager = new GraphUndoManager() {
				// Override Superclass
				public void undoableEditHappened(UndoableEditEvent e) {
					// First Invoke Superclass
					super.undoableEditHappened(e);
					// Then Update Undo/Redo Buttons
					updateHistoryButtons();
				}
			};
			graph.getModel().addUndoableEditListener(undoManager);
			graph.setUndoManager(undoManager);
		} else {
			undoManager = graph.getUndoManager();
		}

		graph.setMarqueeHandler(this.marqueeHandler);

		this.uninstallListeners(graph);
		this.installListeners(graph);

		scroller.setViewportView(graph);

		if (editor.mainToolBar != null) {
			// ���¹�������״̬����Ҫ��������������
			editor.mainToolBar.setActionEnabled(MainActions.ACTION_REDO, false);
			editor.mainToolBar.setActionEnabled(MainActions.ACTION_UNDO, false);
			// editor.mainToolBar.setActionEnabled(MainActions.ACTION_SAVE,
			// !graph.getGraphInfo().isDeployed());
			editor.mainToolBar.setActionEnabled(MainActions.ACTION_SAVE, graph
					.getGraphInfo().getValidStatus() == 0);
		}

		// ����������ͼ
		if (graph.getSelectionCount() == 0) {
			editor.propertiesView.resetProperties(graph.getGraphInfo()
					.getProperties());
		}

		updateHistoryButtons();
	}

	/**
	 * ��װ�¼�������
	 * 
	 */
	public void installListeners(JGraph graph) {
		// ע�᳷������������
		// graph.getModel().addUndoableEditListener(undoManager);
		// ���������¼�����
		graph.getSelectionModel().addGraphSelectionListener(this);
		//
		graph.getGraphLayoutCache().addGraphLayoutCacheListener(this);
		//
		graph.getModel().addGraphModelListener(this);
		// ���̼�����
		graph.addKeyListener(this);
	}

	/**
	 * ɾ���¼�������
	 * 
	 */
	public void uninstallListeners(JGraph graph) {
		// graph.getModel().removeUndoableEditListener(undoManager);
		graph.getSelectionModel().removeGraphSelectionListener(this);
		graph.removeKeyListener(this);
	}

	//
	// KeyListener for Delete KeyStroke
	//
	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		// Listen for Delete Key Press
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			// Execute Remove Action on Delete Key Press
			// remove.actionPerformed(null);
		}
	}

	// From GraphSelectionListener Interface
	public void valueChanged(GraphSelectionEvent e) {

		// ���ù�������ذ�ť�Ŀ�����
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_GROUP, graph
				.getSelectionCount() > 1);
		// Update Button States based on Current Selection
		boolean enabled = !graph.isSelectionEmpty();
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_DELETE, enabled);
		editor.mainToolBar
				.setActionEnabled(MainActions.ACTION_UNGROUP, enabled);
		editor.mainToolBar
				.setActionEnabled(MainActions.ACTION_TOFRONT, enabled);
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_TOBACK, enabled);
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_COPY, enabled);
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_CUT, enabled);
		if (graph.getSelectionCount() > 0) {

			Object c = graph.getSelectionCell();
			if (c instanceof DefaultGraphCell) {
				DefaultGraphCell cell = (DefaultGraphCell) c;
				Object uo = cell.getUserObject();
				Rectangle2D rect = GraphConstants.getBounds(cell
						.getAttributes());
				if (rect != null) {
					editor.messageView.warn("rect=" + rect.getX() + ":"
							+ rect.getY() + rect.getWidth() + ":"
							+ rect.getHeight());
				}

				if (uo instanceof FlowElementObject) {
					editor.propertiesView
							.resetProperties(((FlowElementObject) uo)
									.getProperties());
				} else {
					// System.out.println("uo="+uo);
					undo();
					editor.messageView.warn("�����������������ڲ�����ӵ�����ֵ");
					// System.out.println("������������");
					// editor.propertiesView.resetProperties(null);
				}
			} else {
				undo();
				// editor.propertiesView.resetProperties(null);
				editor.messageView.warn("���������������������񣬲���ʶ��"
						+ c.getClass().getName());
			}

		} else {
			editor.propertiesView.resetProperties(graph.getGraphInfo()
					.getProperties());
		}
		// System.out.println("Change");
	}

	// Update Undo/Redo Button State based on Undo Manager
	protected void updateHistoryButtons() {
		// The View Argument Defines the Context
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_UNDO,
				undoManager.canUndo(graph.getGraphLayoutCache()));
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_REDO,
				undoManager.canRedo(graph.getGraphLayoutCache()));
	}

	/**
	 * ������һ���Ĳ���
	 * 
	 */
	public void undo() {
		if (undoManager == null)
			return;
		try {
			undoManager.undo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}

	/**
	 * ������һ��
	 * 
	 */
	public void redo() {
		if (undoManager == null)
			return;
		try {
			undoManager.redo(graph.getGraphLayoutCache());
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			updateHistoryButtons();
		}
	}

	/**
	 * ɾ��ѡ�е�Ԫ��
	 * 
	 */
	public void deleteSelectedCells() {
		if (!graph.isSelectionEmpty()) {
			Object[] cells = graph.getSelectionCells();
			cells = graph.getDescendants(cells);
			graph.getModel().remove(cells);
		}
	}

	/**
	 * ���ѡ�е�Ԫ��
	 * 
	 */
	public void groupSelectedCells() {
		Object[] cells = graph.getSelectionCells();
		// Order Cells by Model Layering
		cells = graph.order(cells);
		// If Any Cells in View
		if (cells != null && cells.length > 0) {
			DefaultGraphCell group = new DefaultGraphCell();
			// Insert into model
			graph.getGraphLayoutCache().insertGroup(group, cells);
		}
	}

	/**
	 * ȡ��Ԫ�ص����
	 * 
	 */
	public void ungroupSelectedCells() {
		graph.getGraphLayoutCache().ungroup(graph.getSelectionCells());
	}

	/**
	 * ����Ϊʵ�ʴ�С
	 * 
	 */
	public void zoom() {
		graph.setScale(1.0);
	}

	/**
	 * �Ŵ�
	 * 
	 */
	public void zoomIn() {
		graph.setScale(graph.getScale() * 1.5);
	}

	/**
	 * ��С
	 * 
	 */
	public void zoomOut() {
		graph.setScale(graph.getScale() / 1.5);
	}

	/**
	 * ��ѡ�е�Ԫ������һ��
	 * 
	 */
	public void toFront() {
		graph.getGraphLayoutCache().toFront(graph.getSelectionCells());
	}

	/**
	 * ��ѡ�е�Ԫ������һ��
	 * 
	 */
	public void toBack() {
		graph.getGraphLayoutCache().toBack(graph.getSelectionCells());
	}

	/**
	 * ��������Port
	 * 
	 * @param source
	 * @param target
	 */
	public void connect(Port source, Port target) {
		for (Iterator it = source.edges(); it.hasNext();) {
			Edge e = (Edge) it.next();
			if (e.getTarget() == target) {
				this.repaint();
				return;
			}
		}
		// Construct Edge with no label
		DefaultEdge edge = new DefaultEdge();
		if (graph.getModel().acceptsSource(edge, source)
				&& graph.getModel().acceptsTarget(edge, target)) {
			
			edge.setUserObject(new FlowEdgeObject(edge));
			// Create a Map thath holds the attributes for the edge
			edge.getAttributes().applyMap(createEdgeAttributes());
			// Insert the Edge and its Attributes
			graph.getGraphLayoutCache().insertEdge(edge, source, target);
			
			// ��ӵ����ṹ
			//editor.strutcView.addFlowRoute(graph, edge);
		}
	}

	/**
	 * ���������ߵ��������
	 * 
	 * @return
	 */
	public Map createEdgeAttributes() {
		Map map = new Hashtable();
		// Add a Line End Attribute
		GraphConstants.setLineEnd(map, GraphConstants.ARROW_TECHNICAL);
		// Add a label along edge attribute
		GraphConstants.setLabelAlongEdge(map, false);
		GraphConstants.setEndFill(map, true);
		GraphConstants.setLineWidth(map, 1.0f);
		GraphConstants.setLineColor(map, Color.black);
		GraphConstants.setEditable(map, false);
		
		return map;
	}

	public GraphUndoManager getUndoManager() {
		return undoManager;
	}

	public void setUndoManager(GraphUndoManager undoManager) {
		this.undoManager = undoManager;
	}

	public FlowGraph getFlowGraph() {
		return this.graph;
	}

	/**
	 * ���±༭��
	 * 
	 */
	public void updateView() {
		if (this.graph == null)
			return;
		this.graph.repaint();

		this.editor.strutcView.refresh();
	}

	/**
	 * ������ͼ�༭��������һ��Ԫ��
	 * 
	 * @param ele
	 */
	public DefaultGraphCell insertFlowElement(FlowElementObject ele) {
		if (this.graph == null)
			return null;
		
		if(ele.getId()==0){
			
			int id=(new IDService()).getProcessID();
			ele.setId(id);
			
		}
		DefaultGraphCell cell = new DefaultGraphCell();
		
		cell.setUserObject(ele);
		
		if (ele.getImageResource() != null) {
			GraphConstants.setIcon(cell.getAttributes(),UIUtil.loadImageIcon(ele.getImageResource()));
		}
		
		// �Զ����ô�С
		// GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		// GraphConstants.setBackground(cell.getAttributes(), Color.YELLOW);
		GraphConstants.setLineColor(cell.getAttributes(), Color.RED);
		GraphConstants.setLineWidth(cell.getAttributes(), 1.5f);
		GraphConstants.setLineStyle(cell.getAttributes(),
				GraphConstants.STYLE_SPLINE);
		// GraphConstants.setSizeable(cell.getAttributes(),false);
		
		GraphConstants.setBorder(cell.getAttributes(), BorderFactory
				.createLineBorder(Color.BLACK));//�����ɫ
		
		// GraphConstants.setBounds(cell.getAttributes(), new
		// Rectangle2D.Double(
		// 10, 10, 80, 40));
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				ele.getLeft().doubleValue(), ele.getTop().doubleValue(), ele
						.getWidth().doubleValue(), ele.getHeight()
						.doubleValue()));
		
		GraphConstants.setEditable(cell.getAttributes(), false);
		cell.addPort();
		
		this.graph.getGraphLayoutCache().insert(cell);//��ͼ�α༭��д�봦����ͼƬ.
		
		return cell;
	}
	
	/**
	 * д��·����Ԫ��
	 * @param ele
	 * @param cell
	 */
	public void insertRouteElement(FlowElementObject ele,DefaultGraphCell cell) {
		if (this.graph == null)
			return;
		
		//DefaultGraphCell cell = new DefaultGraphCell();
		
		
		
		if (ele.getImageResource() != null) {
			GraphConstants.setIcon(cell.getAttributes(),UIUtil.loadImageIcon(ele.getImageResource()));
		}
		
		// �Զ����ô�С
		// GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		// GraphConstants.setBackground(cell.getAttributes(), Color.YELLOW);
		GraphConstants.setLineColor(cell.getAttributes(), Color.RED);
		GraphConstants.setLineWidth(cell.getAttributes(), 1.5f);
		GraphConstants.setLineStyle(cell.getAttributes(),GraphConstants.STYLE_SPLINE);
		// GraphConstants.setSizeable(cell.getAttributes(),false);
		
		GraphConstants.setBorder(cell.getAttributes(), BorderFactory.createLineBorder(Color.BLACK));//�����ɫ
		
		// GraphConstants.setBounds(cell.getAttributes(), new
		// Rectangle2D.Double(
		// 10, 10, 80, 40));
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				ele.getLeft().doubleValue(), ele.getTop().doubleValue(), ele
						.getWidth().doubleValue(), ele.getHeight()
						.doubleValue()));
		
		GraphConstants.setEditable(cell.getAttributes(), false);
		cell.addPort();
		
		this.graph.getGraphLayoutCache().insert(cell);//��ͼ�α༭��д�봦����ͼƬ.
	}	

	public void graphChanged(GraphModelEvent e) {
		GraphModelChange change = e.getChange();
		// ����Ƿ�������Ԫ��
		Object[] inserted = change.getInserted();
		if (inserted != null && inserted.length > 0) {
			// ���½ṹ��
			for (int i = 0; i < inserted.length; i++) {
				if (inserted[i] instanceof Port) {
					continue;
				} else if (inserted[i] instanceof Edge) {
					editor.strutcView.addFlowRoute(graph, (Edge) inserted[i]);
				} else if (inserted[i] instanceof GraphCell) {
					editor.strutcView.addFlowCell(graph,
							(GraphCell) inserted[i]);
				}
			}
		}
		// ����Ƿ�ɾ����Ԫ��
		Object[] removed = change.getRemoved();
		if (removed != null && removed.length > 0) {
			// ���½ṹ��
			for (int i = 0; i < removed.length; i++) {
				if (removed[i] instanceof Edge) {
					editor.strutcView.removeFlowRoute(graph, (Edge) removed[i]);
				} else if (removed[i] instanceof GraphCell) {
					editor.strutcView.removeFlowCell(graph,
							(GraphCell) removed[i]);
				}
			}
		}
		// System.out.println("Model Event");

		// ��¼��ͼ���Ѿ�����
		graph.setChanged(true);

		// ���±��水ť
		editor.mainToolBar.setActionEnabled(MainActions.ACTION_SAVE, graph
				.getGraphInfo().getValidStatus() == 0);

		/*
		 * if (!graph.getGraphInfo().isDeployed()) {
		 * editor.mainToolBar.setActionEnabled(MainActions.ACTION_SAVE, true); }
		 */
		// editor.mainToolBar.setActionEnabled(MainActions.ACTION_SAVEALL,
		// true);
	}

	public void graphLayoutCacheChanged(GraphLayoutCacheEvent e) {
		System.out.println("GraphLayoutCacheEvent");
	}
	

}
