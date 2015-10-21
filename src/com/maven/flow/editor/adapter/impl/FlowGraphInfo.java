/**
 * @(#)FlowGraphInfo.java 2007-6-5
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import com.maven.flow.editor.model.FlowEdgeObject;
import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.editor.model.GraphElement;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.editor.ui.UIUtil;
import com.maven.flow.util.StreamobjectUtil;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-6-5
 * @since JDK1.4
 */

public class FlowGraphInfo implements Serializable {

	private GraphElement graphElement = null;// ���̱��������

	private FlowCellInfo[] cells = null;// �������̽ڵ�

	private FlowEdgeInfo[] edges = null;// ��������

	private FlowPortInfo[] ports = null;// �������ӵ�

	/**
	 * ��ȡ����ͼ�������Ϣ
	 * 
	 * @param graph
	 */
	public void getInfoFromGraph(FlowGraph graph) {
		
		HashMap cellInfoMap = new HashMap();
		
		
		HashMap infoCellMap = new HashMap();
		// ��ȡ���̱������Ϣ
		this.graphElement = graph.getGraphInfo();

		// ��ȡ�ڵ���Ϣ
		Object[] cs = graph.getGraphLayoutCache().getCells(false, true, false,
				false);
		if (cs != null) {
			cells = new FlowCellInfo[cs.length];
			for (int i = 0; i < cs.length; i++) {
				DefaultGraphCell c = (DefaultGraphCell) cs[i];
				cells[i] = this.toCellInfo(c);
				cellInfoMap.put(c, cells[i]);
				//oldObjectmap.put(c, c);
				
				infoCellMap.put(cells[i], c);
			}
		}
		
		// ��ȡ������Ϣ
		Object[] es = graph.getGraphLayoutCache().getCells(false, false, false,
				true);
		if (es != null) {
			edges = new FlowEdgeInfo[es.length];
			for (int i = 0; i < es.length; i++) {
				DefaultEdge edge = (DefaultEdge) es[i];
				
				Object sourceCell = ((DefaultPort) edge.getSource()).getParent();
				Object targetCell = ((DefaultPort) edge.getTarget()).getParent();
				
				edges[i] = this.toEdgeInfo(edge);
				
				edges[i].setSource((FlowCellInfo) cellInfoMap.get(sourceCell));
				edges[i].setTarget((FlowCellInfo) cellInfoMap.get(targetCell));
				
				
				
				edges[i].setPsedge(edge);
				try{
					
					byte[] b = StreamobjectUtil.ObjectToByte(edges[i]);
					edges[i].setBytes(b);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * ������ͼ����Ϣд�뵽ͼ��
	 * 
	 * @param graph
	 */
	public void setInfoToGraph(FlowGraph graph) {
		HashMap infoCellMap = new HashMap();

		// �������̱��������
		graph.setGraphInfo(this.graphElement);

		// ���ýڵ���Ϣ
		if (this.cells != null) {
			for (int i = 0; i < cells.length; i++) {
				DefaultGraphCell cell = this.fromCellInfo(cells[i]);
				graph.getGraphLayoutCache().insert(cell);

				infoCellMap.put(cells[i], cell);
			}
		}
		
		
		// ����·����Ϣ
		if (this.edges != null) {
			for (int i = 0; i < edges.length; i++) {
				DefaultEdge edge = this.fromEdgeInfo(edges[i]);

				Object sourceCell = ((DefaultGraphCell) infoCellMap
						.get(edges[i].getSource())).getChildAt(0);
				Object targetCell = ((DefaultGraphCell) infoCellMap
						.get(edges[i].getTarget())).getChildAt(0);

				graph.getGraphLayoutCache().insertEdge(edge, sourceCell,targetCell);
				
				
			}
		}
		
		
	}

	/**
	 * ��ȡ�ڵ����Ϣ
	 * 
	 * @param cell
	 * @return
	 */
	private FlowCellInfo toCellInfo(DefaultGraphCell cell) {
		FlowCellInfo info = new FlowCellInfo();
		info.setAutoSize(GraphConstants.isAutoSize(cell.getAttributes()));
		info.setEditable(GraphConstants.isEditable(cell.getAttributes()));

		Rectangle2D bounds = GraphConstants.getBounds(cell.getAttributes());
		info.setX(bounds.getX());
		info.setY(bounds.getY());
		info.setWidth(bounds.getWidth());
		info.setHeight(bounds.getHeight());

		info.setFlowObject((FlowElementObject) cell.getUserObject());

		return info;
	}

	/**
	 * ���ݽڵ����Ϣ����һ���ڵ�
	 * 
	 * @param info
	 * @return
	 */
	private DefaultGraphCell fromCellInfo(FlowCellInfo info) {
		DefaultGraphCell cell = new DefaultGraphCell();

		cell.setUserObject(info.getFlowObject());

		if (info.getFlowObject() != null) {
			if (info.getFlowObject().getImageResource() != null) {
				GraphConstants
						.setIcon(cell.getAttributes(), UIUtil
								.loadImageIcon(info.getFlowObject()
										.getImageResource()));
			}
		}

		GraphConstants.setOpaque(cell.getAttributes(), info.isAutoSize());
		GraphConstants.setLineColor(cell.getAttributes(), Color.black);
		GraphConstants.setLineWidth(cell.getAttributes(), 1.0f);
		GraphConstants.setLineStyle(cell.getAttributes(),
				GraphConstants.STYLE_SPLINE);

		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				info.getX(), info.getY(), info.getWidth(), info.getHeight()));
		GraphConstants.setBorder(cell.getAttributes(), BorderFactory
				.createLineBorder(Color.BLACK));
		GraphConstants.setEditable(cell.getAttributes(), false);

		cell.addPort();

		return cell;
	}

	/**
	 * ������·��ת����·����Ϣ
	 * 
	 * @param edge
	 * @return
	 */
	private FlowEdgeInfo toEdgeInfo(DefaultEdge edge) {
		FlowEdgeInfo info = new FlowEdgeInfo();
		
		info.setColorRGB(GraphConstants.getLineColor(edge.getAttributes())
				.getRGB());
		info.setEndFill(GraphConstants.isEndFill(edge.getAttributes()));
		info.setLabelAlongEdge(GraphConstants.isLabelAlongEdge(edge
				.getAttributes()));
		info.setLineEnd(GraphConstants.getLineEnd(edge.getAttributes()));
		info.setLineWidth(GraphConstants.getLineWidth(edge.getAttributes()));
		info.setEditable(GraphConstants.isEditable(edge.getAttributes()));
		
		// ��ȡ���ߵĶ�����Ϣ
		List c = GraphConstants.getPoints(edge.getAttributes());
		
		if (c != null) {
			double[][] points = new double[c.size()][2];
			for (int i = 0; i < points.length; i++) {
				Point2D p = (Point2D) c.get(i);
				
				if(i==0){
					
					
				}else if(i==points.length-1){
					
					
				}else{
					
					
				}
				
				points[i][0] = p.getX();
				points[i][1] = p.getY();
				
				System.out.println("��ͼ�λ��ʱxʱ�������ǣ���"+p.getX()+",y�������ǣ�"+p.getY());
				
			}
			info.setPoints(points);
		}

		if (edge.getUserObject() != null
				&& (edge.getUserObject() instanceof FlowEdgeObject)) {
			info.setEdgeObject((FlowEdgeObject) edge.getUserObject());
		}

		return info;
	}

	
	/**
	 * ������·��ת����·����Ϣ
	 * �Ѿ�ת���˶��������δ�����ꡣ
	 * @param edge
	 * @return
	 */
	private FlowEdgeInfo toEdgeInfo2(DefaultEdge edge,FlowCellInfo source,FlowCellInfo target) {
		
		
		FlowEdgeInfo info = new FlowEdgeInfo();
		double sourceWidth=source.getWidth();
		double sourceHeight=source.getHeight();
		
		double targetWidth=target.getWidth();
		double targetHeight=target.getHeight();		
		
		
		
		info.setColorRGB(GraphConstants.getLineColor(edge.getAttributes())
				.getRGB());
		info.setEndFill(GraphConstants.isEndFill(edge.getAttributes()));
		info.setLabelAlongEdge(GraphConstants.isLabelAlongEdge(edge
				.getAttributes()));
		info.setLineEnd(GraphConstants.getLineEnd(edge.getAttributes()));
		info.setLineWidth(GraphConstants.getLineWidth(edge.getAttributes()));
		info.setEditable(GraphConstants.isEditable(edge.getAttributes()));
		
		// ��ȡ���ߵĶ�����Ϣ
		List c = GraphConstants.getPoints(edge.getAttributes());
		
		if (c != null) {
			double[][] points = new double[c.size()][2];
			for (int i = 0; i < points.length; i++) {
				Point2D p = (Point2D) c.get(i);
				
				System.out.println("ת��֮ǰ����ͼ�λ��ʱxʱ�������ǣ���"+p.getX()+",y�������ǣ�"+p.getY());
				if(i==0){
					points[i][0] = p.getX()+sourceWidth/2;
					points[i][1] = p.getY()+sourceHeight/2;
					
				}else if(i==points.length-1){
					points[i][0] = p.getX()+targetWidth/2;
					points[i][1] = p.getY()+targetHeight/2;
					
				}else{
					
					points[i][0] = p.getX();
					points[i][1] = p.getY();
					
				}
				

				
				System.out.println("ת��֮�󣬴�ͼ�λ��ʱxʱ�������ǣ���"+p.getX()+",y�������ǣ�"+p.getY());
				
			}
			info.setPoints(points);
		}

		if (edge.getUserObject() != null
				&& (edge.getUserObject() instanceof FlowEdgeObject)) {
			info.setEdgeObject((FlowEdgeObject) edge.getUserObject());
		}

		return info;
	}
	/**
	 * ��������·����Ϣ����һ������·��
	 * 
	 * @param info
	 * @return
	 */
	public DefaultEdge fromEdgeInfo(FlowEdgeInfo info) {
		DefaultEdge edge = new DefaultEdge();
		System.out.println("fromEdgeInfo....");
		/**
		try {

			if (info.getBytes() != null && info.getBytes().length > 0) {
				AttributeMap  edge2 = (AttributeMap) StreamobjectUtil.byteToObject(info.getBytes());
				
				edge.setAttributes(edge2);
				//return edge2;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
		
		// Add a Line End Attribute
		GraphConstants.setLineEnd(edge.getAttributes(), info.getLineEnd());
		// Add a label along edge attribute
		GraphConstants.setLabelAlongEdge(edge.getAttributes(), info
				.isLabelAlongEdge());
		GraphConstants.setEndFill(edge.getAttributes(), info.isEndFill());
		GraphConstants.setLineWidth(edge.getAttributes(), info.getLineWidth());
		GraphConstants.setLineColor(edge.getAttributes(), new Color(info
				.getColorRGB()));
		GraphConstants.setEditable(edge.getAttributes(), info.isEditable());

		// �������ߵĶ�����Ϣ
		double[][] points = info.getPoints();
		if (points != null && points.length > 0) {
			List ps = new ArrayList();
			for (int i = 0; i < points.length; i++) {
				Point2D p = new Point2D.Double(points[i][0], points[i][1]);
				
				System.out.println("�����ݵ���ʱ���������x��:"+points[i][0]+",y�᣺"+points[i][1]);
				ps.add(p);
			}

			GraphConstants.setPoints(edge.getAttributes(), ps);
		}
		

		// edge.setUserObject(new FlowEdgeObject(edge));

		edge.setUserObject(info.getEdgeObject());

		return edge;
	}

	public FlowCellInfo[] getCells() {
		return cells;
	}

	public void setCells(FlowCellInfo[] cells) {
		this.cells = cells;
	}

	public FlowEdgeInfo[] getEdges() {
		return edges;
	}

	public void setEdges(FlowEdgeInfo[] edges) {
		this.edges = edges;
	}

	public GraphElement getGraphElement() {
		return graphElement;
	}

	public void setGraphElement(GraphElement graphElement) {
		this.graphElement = graphElement;
	}

	public FlowPortInfo[] getPorts() {
		return ports;
	}

	public void setPorts(FlowPortInfo[] ports) {
		this.ports = ports;
	}

}
