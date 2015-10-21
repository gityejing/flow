/**
 * @(#)FlowNetSaver.java 2007-6-8
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import com.maven.flow.editor.adapter.FlowGraphSourceAdapter;
import com.maven.flow.editor.bean.imp.FlowBeanInfo;
import com.maven.flow.editor.model.ApprovalProcessObject;
import com.maven.flow.editor.model.EndElement;
import com.maven.flow.editor.model.FlowEdgeObject;
import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.editor.model.HandleProcessObject;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.editor.model.ProcessElementObject;
import com.maven.flow.editor.model.StartElement;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.editor.ui.FlowGraphManager;
import com.maven.flow.editor.ui.UIUtil;
import com.maven.flow.editor.ui.WorkFlowEditor;
import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessPeople;
import com.maven.flow.service.ApplicationService;
import com.maven.flow.service.ProcessService;
import com.maven.flow.service.RouteService;
import com.maven.flow.service.TblProcessPeopleService;
import com.maven.flow.util.StreamobjectUtil;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-6-8
 * @since JDK1.5
 */

public class FlowServletAdapter implements FlowGraphSourceAdapter {

	private ApplicationService appService = new ApplicationService();

	private ProcessService processService = new ProcessService();
	
	private RouteService rs=new RouteService();
	
	private WorkFlowEditor editor = null;

	public FlowServletAdapter(WorkFlowEditor editor) {
		this.editor = editor;
	}

	/**
	 * 另存流程图
	 */
	public void saveAs(FlowGraph graph) {
		if (graph == null)
			return;

		String newName = this.getNewGraphName(graph.getGraphInfo().getName());

		if (newName == null || newName.trim().length() == 0)
			return;

		graph.getGraphInfo().setName(newName);

		graph.getGraphInfo().setFilePath("");
		graph.getGraphInfo().setAppId(-1);
		graph.getGraphInfo().setDeployed(false);
		graph.getGraphInfo().setReleaseStatus(0);
		graph.getGraphInfo().setValidStatus(0);

		this.save(graph);

		editor.strutcView.updateGraphNode(graph);
	}

	/**
	 * 将通过Servlet来保存流程图
	 */
	public void save(FlowGraph graph) {
		FlowGraphInfo info = new FlowGraphInfo();
		info.getInfoFromGraph(graph);
		String filePath = info.getGraphElement().getFilePath();
		if (filePath == null || filePath.trim().length() == 0) {
			filePath = System.currentTimeMillis() + ".flow";
			info.getGraphElement().setFilePath(filePath);
		}

		try {
			FlowBeanInfo flowbeanInfo = new FlowBeanInfo(info);
			flowbeanInfo.Save();
			editor.messageView.info("工作流保存成功");
		} catch (Exception ex) {
			editor.messageView.error(ex.getMessage());
		}
	}

	public FlowGraph load() {
		//
		try {
			// 先获取列表
			// 然后选择一个
			List appList = appService.appDAO.findAll();
			// 将ID NAME存入
			if (appList.size() > 0) {
				String[][] infos = new String[appList.size()][2];
				for (int i = 0; i < appList.size(); i++) {
					TblApplication app = (TblApplication) appList.get(i);
					infos[i][0] = String.valueOf(app.getAppId());
					infos[i][1] = app.getAppId() + " -- " + app.getAppName();

				}

				FlowOpenDlg dlg = new FlowOpenDlg(infos);
				dlg.setVisible(true);
				String flowFile = dlg.getSelectedFile();
				return this.load(flowFile);
			} else {
				editor.messageView.error("还没有流程，请新建流程！");
			}

		} catch (Exception ex) {
			editor.messageView.error(ex.getMessage());
		}
		return null;
	}

	// 这里需要修改，直接不用访问数据器，访问数据库即可。

	public FlowGraph load(String appIdStr) {
		FlowGraph graph = null;
		try {
			TblApplication appInfo = this.appService.findByAppId(new Integer(
					appIdStr));
			//
			long appId = appInfo.getAppId().longValue();
			FlowGraphManager.addNewGraph();
			graph = FlowGraphManager.getCurrentGraph();
			graph.getGraphInfo().setAppId(appId);
			graph.getGraphInfo().setCreateTime(appInfo.getAppCreateTime());
			graph.getGraphInfo().setCreator("" + appInfo.getAppCreateUser());
			graph.getGraphInfo().setCreatorName(appInfo.getAppCreateUserName());
			graph.getGraphInfo()
					.setDeployed(appInfo.getAppReleaseStatus().intValue() == 1);
			graph.getGraphInfo().setName(appInfo.getAppName());
			graph.getGraphInfo().setDescription(appInfo.getAppDesc());
			graph.getGraphInfo().setValidStatus(appInfo.getAppValidStatus().intValue());
			graph.getGraphInfo().setApplicationCode(appInfo.getApplicationCode());
			
			
			// 新的流程图，设置为当前编辑流程图
			// editor.graphEditor.al
			// 添加操作步骤
		
			ArrayList list = (ArrayList) processService
					.getProcessByAppId(new Integer(appIdStr));
			HashMap processMap=new HashMap(); 
			TblProcessPeopleService pps=new TblProcessPeopleService();
			for (int i = 0; i < list.size(); i++) {
				
				TblProcess process = (TblProcess) list.get(i);
				ProcessElementObject ele = null;
				if (process.getProcessType().intValue() == TblProcess.TYPE_START) {
					ele = new StartElement();
				} else if (process.getProcessType().intValue() == TblProcess.TYPE_APPROVAL) {
					ele = new ApprovalProcessObject();
				} else if (process.getProcessType().intValue() == TblProcess.TYPE_HANDLE) {
					ele = new HandleProcessObject();
					
				} else if (process.getProcessType().intValue() ==TblProcess.TYPE_END) {
					ele = new EndElement();
				}
				
				
				ele.setViewPageUrl(process.getViewURL());
				ele.setHandlePageUrl(process.getHandleURL());
				//
				ele.setName(process.getProcessName());
				ele.setLeft(process.getLeft());
				ele.setTop(process.getTop());
				ele.setWidth(process.getWidth());
				ele.setHeight(process.getHeight());
				
				//FlowCellInfo
				//初始化步骤属性,从数据库里将属性值付给图形
				ele.setId(process.getProcessId().intValue());
				
				if(process.getIfcanSplitFlow()!=null){
					ele.setIfcanSplitFlow(process.getIfcanSplitFlow().intValue());
				}
				
				if(process.getIsSubFlowStep()!=null){
					ele.setIssubFlowStep(process.getIsSubFlowStep().intValue());
				}
				
				if(process.getIsfirstSubFlow()!=null){
					ele.setIsfirstSubFlow(process.getIsfirstSubFlow().intValue());
				}
				
				if(process.getStep()!=null){
					ele.setStep(process.getStep().intValue());
				}
				
				if(process.getUniteStep()!=null){
					ele.setUniteStep(process.getUniteStep().intValue());
				}
				
				if(process.getIsWaitForSubFlow()!=null){
					ele.setIsWaitForSubFlow(process.getIsWaitForSubFlow().intValue());
				}
				
				if(process.getSplitType()!=null){
					ele.setSplitType(process.getSplitType().intValue());
				}
				
				if(process.getInnerStep()!=null){
					ele.setInnerStep(process.getInnerStep().intValue());
				}
				
				if(process.getMultiJobHandle()!=null){
					ele.setMultiJobHandle(process.getMultiJobHandle().intValue());
				}
				
				ele.setSplitProcessHandle(process.getSplitProcessHandle());
				
				//from applet.
				ele.setIsProcessAsynchronism(process.getIsProcessAsynchronism());
				ele.setCurProcessHandleMethod(process.getCurProcessHandleMethod());
				ele.setProcessGroup(process.getProcessGroup());
				ele.setBeforeHandleClass(process.getBeforeHandleClass());
				ele.setAfterHandleClass(process.getAfterHandleClass());
				ele.setProcessCode(process.getProcessCode());
				ele.setIsAgr(process.getIsAgr());
				ele.setBeforeHandleClass(process.getBeforeHandleClass());
				ele.setAfterHandleClass(process.getAfterHandleClass());
				ele.setId(process.getProcessId());
				
				List peoples=pps.findByProperty("processId", process.getProcessId());
				if(peoples!=null && peoples.size()>0){
					
					for(int j=0;j<peoples.size();j++){
						TblProcessPeople tpp=(TblProcessPeople)peoples.get(j);
						if(tpp.getPeopleType()!=null && tpp.getPeopleType().intValue()==1){
							KeyValueObject kvo=new KeyValueObject(new Integer(tpp.getRealFsn()),tpp.getRealValue());
							ele.getAccessRoles().add(kvo);
						}else if(tpp.getPeopleType()!=null && tpp.getPeopleType().intValue()==2){
							KeyValueObject kvo=new KeyValueObject(new Long(tpp.getRealFsn()),tpp.getRealValue());
							ele.getAccessUsers().add(kvo);
						}else if(tpp.getPeopleType()!=null && tpp.getPeopleType().intValue()==3){
							KeyValueObject kvo=new KeyValueObject(new Long(tpp.getRealFsn()),tpp.getRealValue());
							ele.getProjectRoles().add(kvo);
						}else if(tpp.getPeopleType()!=null && tpp.getPeopleType().intValue()==4){
							KeyValueObject kvo=new KeyValueObject(new Long(tpp.getRealFsn()),tpp.getRealValue());
							ele.getHuiQianRoles().add(kvo);
						}
					}
				}
				//
				
				//
				DefaultGraphCell cell=this.editor.graphEditor.insertFlowElement(ele);
				//System.out.println("processId==========="+process.getProcessId()+",cell==="+cell);
				processMap.put(process.getProcessId()+"", cell);
			}
			
			//加载线
			List routes=rs.getRouteByAppId(new Integer(appIdStr));
			for(int i=0;i<routes.size();i++){
				TblAppRoute route=(TblAppRoute)routes.get(i);
				
				
				FlowEdgeObject ele = new FlowEdgeObject();
				
				//DefaultGraphCell cell = new DefaultGraphCell();
				//System.out.println(route.getBytes().length);
				
				//ele.setConditionExpress(route.getConditionExpress());
				FlowEdgeInfo info=(FlowEdgeInfo)StreamobjectUtil.byteToObject(route.getBytes());
				ele.setConditionExpress(route.getConditionExpress());
				ele.setIsBack(route.getIsBack());
				ele.setIsRouteAsynchronism(route.getIsRouteAsynchronism());
				ele.setIsConditionPath(route.getIsConditionPath());
				ele.setName(route.getRouteName());
				//ele.setSubFlowPath(route.getSubFlowPath());
				//ele.setDisplayText(route.getRouteDisplayText());
				
				//ele.setConditionClass(route.getConditionExpress());
				ele.setConditionClass(route.getConditionClass());
				/**
				
				FlowEdgeInfo info = new FlowEdgeInfo();
				info.setBytes(route.getBytes());
				info.setEdgeObject(ele);
				List c = GraphConstants.getPoints(am);
				if (c != null) {
					double[][] points = new double[c.size()][2];
					for (int j = 0; j < points.length; j++) {
						Point2D p = (Point2D) c.get(j);
						points[i][0] = p.getX();
						points[i][1] = p.getY();
					}
					info.setPoints(points);
				}
				
				*/
				FlowGraphInfo fgi=new FlowGraphInfo();
				//DefaultEdge edge = fgi.fromEdgeInfo(info);
				
				DefaultEdge edge=info.getPsedge();
				//DefaultEdge edge = fgi.fromEdgeInfo(info);
				
				//DefaultPort source=info.getSourcePort();
				
				//DefaultPort target=info.getTargetPort();
				//System.out.println("getRouteBeginProcess==========="+route.getRouteBeginProcess());
				
				//System.out.println("getRouteEndProcess==========="+route.getRouteEndProcess());
				
				
				//如果用以下的效果来做，可以实现移动Process时，线紧跟着。但是起点坐标和终点坐搞错了。且保存是有一个类转换错误。
				
				DefaultGraphCell GCsource=(DefaultGraphCell)processMap.get(route.getRouteBeginProcess()+"");
				DefaultGraphCell GCtarget=(DefaultGraphCell)processMap.get(route.getRouteEndProcess()+"");
				if(GCsource==null || GCtarget==null){
					continue;
				}
				
				Object source=GCsource.getChildAt(0);
				Object target=GCtarget.getChildAt(0);
				//DefaultGraphCell source1=(DefaultGraphCell)info.getSourceObject();
				
				//DefaultGraphCell target1=(DefaultGraphCell)info.getTargetObject();
				
				
				//DefaultPort source=(DefaultPort)info.getSourcePort();
				//DefaultPort target=(DefaultPort)info.getTargetPort();
				
				
				
				//source.setParent((DefaultGraphCell)info.getSourceObject());
				//target.setParent((DefaultGraphCell)info.getTargetObject());
				
				//source=info.getSourceObject();
				//target=info.getTargetObject();
				
				
				//============================================
				//de.fromEdgeInfo()
				//this.editor.graphEditor.insertRouteElement(ele, cell)
				//if(source!=null && target!=null){
				
				//System.out.println("---------------"+graph.getGraphLayoutCache()+",edge==="+edge+",source="+source+",target="+target);
				if (graph.getModel().acceptsSource(edge, source)) {
					edge.setSource(source);
					//System.out.println("edge 与source已经连接。。。。。。。。。。	");
				}
				if(graph.getModel().acceptsTarget(edge, target)){
					edge.setTarget(target);
					//System.out.println("edge 与target已经连接。。。。。。。。。。	");
				}
				
				edge.setSource(source);
				edge.setTarget(target);
				
				graph.getGraphLayoutCache().insertEdge(edge, source,target);
				
				//graph.getGraphLayoutCache().insert(edge);
				//}
				//this.editor.graphEditor.insertRouteElement(ele,cell);
			}
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return graph;
	}

	private String getNewGraphName(String oldName) {
		GetNameDlg dlg = new GetNameDlg(oldName);
		dlg.setVisible(true);

		return dlg.getNewName();
	}

	class GetNameDlg extends JDialog {

		String oldName = null;

		JTextField field = new JTextField();

		boolean canceled = false;

		GetNameDlg(String oldName) {
			this.oldName = oldName;

			this.setModal(true);

			this.setSize(new Dimension(300, 90));
			this.setResizable(false);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(
					(int) (screenSize.getWidth() - this.getWidth()) / 2,
					(int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.setTitle("请输入要保存的工作流名称");

			JPanel p = new JPanel();
			JButton bok = new JButton("确定");
			JButton bcnl = new JButton("取消");
			p.add(bok);
			p.add(bcnl);

			Container c = this.getContentPane();

			field.setText(this.oldName);

			c.add(field, BorderLayout.CENTER);
			c.add(p, BorderLayout.SOUTH);

			bok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (!validateName())
						return;
					canceled = false;
					dispose();
				}
			});

			bcnl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					canceled = true;
					dispose();
				}
			});

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					canceled = true;
				}
			});
		}

		String getNewName() {
			if (canceled)
				return null;
			return field.getText().trim();
		}

		boolean validateName() {
			String newName = field.getText();
			if (newName == null || newName.trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "请输入流程的名称");
				// editor.alert("请输入流程的名称");
				return false;
			}
			if (newName.equals(oldName)) {
				JOptionPane.showMessageDialog(this, "流程名称不能与原来的名称相同");
				// editor.alert("流程名称不能与原来的名称相同");
				return false;
			}
			return true;
		}
	}

	class FlowOpenDlg extends JDialog {
		String[][] names = null;

		JList list = new JList();

		boolean canceled = false;

		public FlowOpenDlg(String[][] names) {
			this.names = names;

			this.setModal(true);
			this.setAlwaysOnTop(true);

			this.setSize(new Dimension(300, 300));
			this.setResizable(false);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(
					(int) (screenSize.getWidth() - this.getWidth()) / 2,
					(int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.setTitle("请双击以打开工作流");

			this.init();
		}

		private void init() {
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setCellRenderer(new FlowListRender());
			list.setListData(names);

			Container c = this.getContentPane();

			c.setLayout(new BorderLayout());

			c.add(new JScrollPane(list), BorderLayout.CENTER);

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					canceled = true;
				}
			});

			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 2) {
						Object tmp = list.getSelectedValue();
						String[] info = (String[]) tmp;
						// 检查是否已经打开，根据名称检查
						FlowGraph[] graphs = FlowGraphManager
								.getAllFlowGraphs();
						for (int i = 0; i < graphs.length; i++) {
							if (graphs[i] == null
									|| graphs[i].getGraphInfo() == null)
								continue;
							if (info[1].equals(graphs[i].getGraphInfo()
									.getName())) {
								// JOptionPane.showMessageDialog(,"流程已经打开");
								// editor.alert("流程[] 已经打开");
								FlowGraphManager.setCurrentGraph(graphs[i]);
								canceled = true;
								dispose();
								return;
							}
						}
						canceled = false;
						dispose();
					}
				}
			});
		}

		String getSelectedFile() {
			if (canceled)
				return null;
			Object obj = list.getSelectedValue();
			if (obj == null)
				return null;
			return ((String[]) obj)[0];
		}
	}

	class FlowListRender extends JLabel implements ListCellRenderer {

		// This is the only method defined by ListCellRenderer.
		// We just reconfigure the JLabel each time we're called.

		public Component getListCellRendererComponent(JList list, Object value, // value
				// to
				// display
				int index, // cell index
				boolean isSelected, // is the cell selected
				boolean cellHasFocus) // the list and the cell have the focus
		{
			String[] info = (String[]) value;
			setText(info[1]);
			setIcon(UIUtil.loadImageIcon("resources/editor.gif"));
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
	}

}