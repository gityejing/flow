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
 * ���������������
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class WorkFlowEditor extends JFrame {
	{
		FlowGraphManager.setEditor(this);//ָ��editor.
	}
	
	// ����ά������
	public MainActions mainActions = new MainActions(this);//��ʼ��������
	
	// ���˵��ؼ�
	private  MainMenuBar menuBar = new MainMenuBar(this);//��ʼ���˵���
	
	// ͼ�α༭��[�м��ͼ�α༭��]!
	public GraphEditor graphEditor = new GraphEditor(this);//��ʼ��ͼ�α༭��.
	
	// ���̹�����
	// public FlowGraphManager graphManager = new FlowGraphManager(this);
	
	// ��������
	public MainToolBar mainToolBar = new MainToolBar(this);//��ʼ����������
	
	public SecToolBar secToolBar = new SecToolBar(this);//��ʼ����������.
	
	// �Զ��幤����
	public CustomizeToolBar customizeToolBar = new CustomizeToolBar(this);//
	
	// �ṹ��ͼ
	public FlowStructView strutcView = new FlowStructView(this);//�ṹ��ͼ ���
	
	// ����Ԫ����ͼ
	public FlowElementPanel elementView = new FlowElementPanel(this);//����Ԫ�����(��ʼ����������)
	
	// ������ͼ
	public FlowPropertiesView propertiesView = new FlowPropertiesView(this);
	
	// ��Ϣ��ͼ
	public FlowMessageView messageView = new FlowMessageView(this);//��Ϣ��ͼ
	
	private JLabel stateBar=new JLabel("    �����״�Ƽ����ſƼ��������޹�˾  ��ӱ�·906��1604-1607  ");

	/**
	 * Applet�ĳ�ʼ�� ��������
	 */
	public void init() {
		
		// super.init();
		
		// System.getProperties().list(System.out);
		
		Container container = this.getContentPane();
		container.setLayout(new BorderLayout());// ���ıߺ��м��BorderLayout.
		
		//JPanel toolBarPanel = new JPanel();// ���������.
		//if (customizeToolBar.getActioinCount() > 0) {
		//	toolBarPanel.setLayout(new GridLayout(2, 1));
		//} else {
		//	toolBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		//}
		// �����������
		//toolBarPanel.add(mainToolBar);// ��������Ĳ���ΪFlowLayout,������ֹԪ�ع������.
		//if (customizeToolBar.getActioinCount() > 0) {
		//	toolBarPanel.add(customizeToolBar);
		//}
		JPanel p = new JPanel(new GridLayout(2, 1));
		JPanel s = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p.add(mainToolBar);//��������
		s.add(secToolBar);//��������
		
		p.add(s);//
		
		//editor.add(p, BorderLayout.NORTH);
		//JPanel toolBarPanel = new JPanel();
		
		container.add(p, BorderLayout.NORTH);//�������档
		//container.add(toolBarPanel, BorderLayout.NORTH);// ������������������.
		
		// ���ͼ�α༭��
		//container.add(graphEditor, BorderLayout.CENTER);// ���������ͼ�α༭��.
		
		// �����ͼ----��ߵ���ͼ��Ϊ������. һ�����ǽṹ��ͼ,��һ�����ǻ�����ͼ...
		JSplitPane leftPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);// ��PaneΪֱ��
		
		// ��ӽṹ��ͼ
		//leftPanel.setTopComponent(strutcView);// �ϱߵ����.
		JTabbedPane propertyPage=new JTabbedPane() ;
		propertyPage.add("����	", propertiesView);
		propertyPage.setIconAt(0, UIUtil.loadImageIcon("resources/propertiesview.gif")) ;
		//propertyPage.add("�¼�	", null);
		leftPanel.setTopComponent(propertyPage);//�������
		
		// ���Ԫ����ͼ
		JTabbedPane flowPage=new JTabbedPane() ;//������壺���������壬��Ϣ���
		flowPage.add("�������",graphEditor);//���뻭Ƭ���
		flowPage.setIconAt(0, UIUtil.loadImageIcon("resources/editor.gif"));
		flowPage.add("��Ϣ���",messageView);//������Ϣ���
		//flowPage.add("��Ϣ���",elementView);
		
		flowPage.setIconAt(1, UIUtil.loadImageIcon("resources/messageview.gif"));
		//leftPanel.setBottomComponent(elementView);// �ױߵ����
		leftPanel.setBottomComponent(flowPage);//
		// container.add(strutcView,BorderLayout.WEST);
		container.add(leftPanel, BorderLayout.CENTER);//
		
		this.setJMenuBar(menuBar);
		// ���������ͼ
		//container.add(propertiesView, BorderLayout.EAST);
		
		// �����Ϣ��ͼ
		
		//container.add(messageView, BorderLayout.SOUTH);
		container.add(stateBar, BorderLayout.SOUTH);//���ϱ�
		
		// ����LookAndFeel
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				openWindow();
			}
			
			public void windowClosing(WindowEvent e) {
				closingWindow();
			}
		});


		//Ĭ��һ��������
		FlowGraphManager.addNewGraph();
		//ditor.graphEditor.addNewGraph();
	}

	/**
	 * ִ�ж���
	 * 
	 * @param actionId
	 * @param event
	 */
	protected void executeAction(int actionId, EventObject event) {
		this.mainActions.executeAction(actionId, event);
	}

	/**
	 * ������ͼ,�򿪹�����ͼ��.....
	 * 
	 * @param appId
	 */
	public void openFlow(long appId) {
		FlowGraphManager.openGraph(appId);
	}

	/**
	 * ���Է���
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
			
			editor.setTitle("�����������1.0");
			// ��ȡϵͳͼ��
			URL editorIconUrl = WorkFlowEditor.class.getClassLoader().getResource(
					"resources/editor.gif");
			// ���ͼ�����
			if (editorIconUrl != null) {
				// װ��ͼ��
				ImageIcon editorIcon = new ImageIcon(editorIconUrl);
				// ���ô���ͼ��
				editor.setIconImage(editorIcon.getImage());
			}
			
			// ����Ĭ�ϴ�С
			editor.setSize(800, 600);
			editor.setExtendedState(6);
			editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// ��ʾ����
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
