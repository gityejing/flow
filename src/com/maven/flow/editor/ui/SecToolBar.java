package com.maven.flow.editor.ui;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JToolBar;

public class SecToolBar extends JToolBar {

	/**
	 * Action�д洢ActionId�ļ�ֵ
	 */
	private static final String ACTION_ID_KEY = "Action_Id";

	private static final String TOGLE_ID_KEY = "Is_togle";

	// �������������
	private WorkFlowEditor editor = null;

	// ����
	private MainActions actions = null;

	/**
	 * ����һ��������
	 * 
	 * @param editor
	 */
	public SecToolBar(WorkFlowEditor editor) {
		this.editor = editor;
		this.actions = editor.mainActions;

		this.init();
	}

	/**
	 * ��ʼ��������
	 * 1,����������ʽ
	 * 2,���������С
	 * 
	 */
	protected void init() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();//��ʼ��ͼ�λ���.
		String[] names = ge.getAvailableFontFamilyNames();
		String[] sizes = new String[] { "6", "8", "10", "12", "14", "16", "18",
				"20", "22", "24", "26", "28", "36", "48", "72" };
		JComboBox fontNames;

		JComboBox fontSizes;
		_IconToggleButton boldButton;

		_IconToggleButton italicButton;
		fontNames = new JComboBox(names);
		fontNames.setPreferredSize(new Dimension(150, 25));
		fontSizes = new JComboBox(sizes);
		fontSizes.setPreferredSize(new Dimension(55, 25));

		boldButton = new _IconToggleButton(UIUtil
				.loadImageIcon("resources/_bold.gif"));//����Ӵ�

		italicButton = new _IconToggleButton(UIUtil
				.loadImageIcon("resources/_italic.gif"));//б����
		this.add(fontNames);//1,����������ʽ
		this.add(fontSizes);//2,���������С

		this.addSeparator();

		this.add(boldButton);
		this.add(italicButton);
		
		fontNames.setMaximumSize(fontNames.getPreferredSize());
		fontSizes.setMaximumSize(fontSizes.getPreferredSize());

		boldButton.setAlignmentY(0.5f);
		italicButton.setAlignmentY(0.5f);
		boldButton.setAlignmentX(0.5f);
		italicButton.setAlignmentX(0.5f);
		// ���ò�������
		// this.setFloatable(false);
		this.addSeparator();
		
		
		//�������Ԫ�ذ�ť��
		_IconButton srartButton = new _IconButton(this.editor,0,UIUtil
				.loadImageIcon("resources/start_small.gif"));
		_IconButton endButton = new _IconButton(this.editor,1,UIUtil
				.loadImageIcon("resources/end_small.gif"));
		_IconButton HandleButton = new _IconButton(this.editor,2,UIUtil
				.loadImageIcon("resources/ps1_small.gif"));
		_IconButton Approval = new _IconButton(this.editor,3,UIUtil
				.loadImageIcon("resources/ps2_small.gif"));
		this.add(srartButton);
		this.add(endButton);
		this.add(HandleButton);
		this.add(Approval);
		this.setPreferredSize(new Dimension(1024, 25));
	}

}