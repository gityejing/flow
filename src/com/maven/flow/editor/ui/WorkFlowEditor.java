/**
 * @(#)WorkFlowEditor.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.incors.plaf.alloy.AlloyLookAndFeel;
import com.incors.plaf.alloy.themes.glass.GlassTheme;
import com.maven.flow.editor.SystemGloable;
import com.maven.flow.service.ApplicationService;

/**
 * 工作流设计主界面
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class WorkFlowEditor extends JFrame {
	{
		FlowGraphManager.setEditor(this);//指定editor.
	}
	
	// 动作维护对象
	public MainActions mainActions = new MainActions(this);//初始化动作器
	
	// 主菜单控件
	private  MainMenuBar menuBar = new MainMenuBar(this);//初始化菜单项
	
	// 图形编辑器[中间的图形编辑器]!
	public GraphEditor graphEditor = new GraphEditor(this);//初始化图形编辑器.
	
	// 流程管理器
	// public FlowGraphManager graphManager = new FlowGraphManager(this);
	
	// 主工具条
	public MainToolBar mainToolBar = new MainToolBar(this);//初始化主工具条
	
	public SecToolBar secToolBar = new SecToolBar(this);//初始化副工具条.
	
	// 自定义工具条
	public CustomizeToolBar customizeToolBar = new CustomizeToolBar(this);//
	
	// 结构视图
	public FlowStructView strutcView = new FlowStructView(this);//结构视图 面板
	
	// 流程元素视图
	public FlowElementPanel elementView = new FlowElementPanel(this);//流程元素面板(开始，处理，结理)
	
	// 属性视图
	public FlowPropertiesView propertiesView = new FlowPropertiesView(this);
	
	// 消息视图
	public FlowMessageView messageView = new FlowMessageView(this);//消息视图
	
	private JLabel stateBar=new JLabel("    广州易达科技建信科技开发有限公司  天河北路906号1604-1607  ");

	/**
	 * Applet的初始化 构建界面
	 */
	public void init() {
		
		// super.init();
		
		// System.getProperties().list(System.out);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());// 有四边和中间的BorderLayout.
		
		//JPanel toolBarPanel = new JPanel();// 工具条面板.
		//if (customizeToolBar.getActioinCount() > 0) {
		//	toolBarPanel.setLayout(new GridLayout(2, 1));
		//} else {
		//	toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		//}
		// 添加主工具条
		//toolBarPanel.add(mainToolBar);// 定义该面板的布局为FlowLayout,这样防止元素过长溢出.
		//if (customizeToolBar.getActioinCount() > 0) {
		//	toolBarPanel.add(customizeToolBar);
		//}
		JPanel p = new JPanel(new GridLayout(2, 1));
		JPanel s = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p.add(mainToolBar);//主工具条
		s.add(secToolBar);//副工具条
		
		p.add(s);//
		
		//editor.add(p, BorderLayout.NORTH);
		//JPanel toolBarPanel = new JPanel();
		
		container.add(p, BorderLayout.NORTH);//加在上面。
		//container.add(toolBarPanel, BorderLayout.NORTH);// 容器里加入这个工具体.
		
		// 添加图形编辑器
		//container.add(graphEditor, BorderLayout.CENTER);// 容器里加入图形编辑器.
		
		// 左边视图----左边的视图分为二部分. 一部分是结构视图,另一部分是环节视图...
		JSplitPane leftPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);// 该Pane为直的
		
		// 添加结构视图
		//leftPanel.setTopComponent(strutcView);// 上边的组件.
		JTabbedPane propertyPage=new JTabbedPane() ;
		propertyPage.add("属性	", propertiesView);
		propertyPage.setIconAt(0, UIUtil.loadImageIcon("resources/propertiesview.gif")) ;
		//propertyPage.add("事件	", null);
		leftPanel.setTopComponent(propertyPage);//属性面板
		
		// 添加元素视图
		JTabbedPane flowPage=new JTabbedPane() ;//流程面板：包括设计面板，消息面板
		flowPage.add("流程设计",graphEditor);//加入画片面板
		flowPage.setIconAt(0, UIUtil.loadImageIcon("resources/editor.gif"));
		flowPage.add("消息输出",messageView);//加入消息面板
		//flowPage.add("消息输出",elementView);
		
		flowPage.setIconAt(1, UIUtil.loadImageIcon("resources/messageview.gif"));
		//leftPanel.setBottomComponent(elementView);// 底边的组件
		leftPanel.setBottomComponent(flowPage);//
		// container.add(strutcView,BorderLayout.WEST);
		container.add(leftPanel, BorderLayout.CENTER);//
		
		this.setJMenuBar(menuBar);
		// 添加属性视图
		//container.add(propertiesView, BorderLayout.EAST);
		
		// 添加消息视图
		
		//container.add(messageView, BorderLayout.SOUTH);
		container.add(stateBar, BorderLayout.SOUTH);//最南边
		
		// 设置LookAndFeel
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				openWindow();
			}
			
			public void windowClosing(WindowEvent e) {
				closingWindow();
			}
		});


		//默认一个新流程
		FlowGraphManager.addNewGraph();
		//ditor.graphEditor.addNewGraph();
	}

	/**
	 * 执行动作
	 * 
	 * @param actionId
	 * @param event
	 */
	protected void executeAction(int actionId, EventObject event) {
		this.mainActions.executeAction(actionId, event);
	}

	/**
	 * 打开流程图,打开工作流图形.....
	 * 
	 * @param appId
	 */
	public void openFlow(long appId) {
		FlowGraphManager.openGraph(appId);
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {


		WorkFlowEditor editor = new WorkFlowEditor();
		setLookAndFeel(editor);
		
		SystemGloable.frame=editor;
		if (ClientLogin.CheckUser())
		{
			editor.init();
			
			editor.setTitle("工作流设计器1.0");
			// 获取系统图标
			URL editorIconUrl = WorkFlowEditor.class.getClassLoader().getResource(
					"resources/editor.gif");
			// 如果图标存在
			if (editorIconUrl != null) {
				// 装载图标
				ImageIcon editorIcon = new ImageIcon(editorIconUrl);
				// 设置窗口图标
				editor.setIconImage(editorIcon.getImage());
			}
			
			// 设置默认大小
			editor.setSize(800, 600);
			editor.setExtendedState(6);
			editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// 显示窗口
			setLookAndFeel(editor);
			editor.setVisible(true);
		}
	}

	private static void setLookAndFeel(Frame ParentFrame) {
		try {
			LookAndFeel alloyLnF = new AlloyLookAndFeel(new GlassTheme());
			UIManager.setLookAndFeel(alloyLnF);
			Font font = new Font("Dialog", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
			SwingUtilities.updateComponentTreeUI(ParentFrame);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void openWindow() {
	}

	public void closingWindow() {
		System.exit(0);

	}

}
