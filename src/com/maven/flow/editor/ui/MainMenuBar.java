package com.maven.flow.editor.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {

	private JMenu ppmAlignMenu;

	private JMenu ppmSizeMenu;

	private JMenu alignMenu;

	private JMenu sizeMenu;

    //�������������
    private WorkFlowEditor editor = null;

    // ����
    private MainActions actions = null;
    /**
     * �������˵���
     * @param editor
     */
	public MainMenuBar(WorkFlowEditor editor) {
        this.editor = editor;
        this.actions = editor.mainActions;
        this.init();
    }

	/**
	 * ��ʼ�����˵���
	 */
	private void init() {
		JMenu fileMenu = new JMenu("�ļ�", true);
		fileMenu.setMnemonic('F');
		fileMenu.add(new JMenuItem("�½�"));
		fileMenu.add(new JMenuItem("��"));
		fileMenu.add(new JMenuItem("����"));
		fileMenu.add(new JMenuItem("���Ϊ..."));
		fileMenu.add(new JMenuItem("�ر�"));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("ҳ������"));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("�ļ�����"));
		// MruPopupMenu reOpenMenu = new MruPopupMenu(App.getMruManager());
		// reOpenMenu.append(fileMenu);
		// reOpenMenu.refresh();
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("�˳�"));
		this.add(fileMenu);
		
		JMenu editMenu = new JMenu("�༭");
		editMenu.setMnemonic('E');
		editMenu.add(new JMenuItem("����"));
		editMenu.add(new JMenuItem("����"));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem("����"));
		editMenu.add(new JMenuItem("����"));
		editMenu.add(new JMenuItem("ճ��"));
		editMenu.add(new JMenuItem("ɾ��"));
		this.add(editMenu);
		/*
		 * JMenu newMenu = new JMenu(App.messages.getString("res.107"), true);
		 * newMenu.setMnemonic('I'); newMenu.add(new
		 * JMenuItem(am.newLabelAction)); newMenu.add(new
		 * JMenuItem(am.newTextAction));
		 * 
		 * newMenu.add(new JMenuItem(am.newImageAction));
		 * 
		 * newMenu.add(new JMenuItem(am.newPanelAction));
		 * 
		 * newMenu.add(new JMenuItem(am.newTableAction));
		 * 
		 * JMenu formatMenu = new JMenu(App.messages.getString("res.108"),
		 * true); formatMenu.setMnemonic('O'); formatMenu.add(new
		 * JMenuItem(am.bringToFrontAction)); formatMenu.add(new
		 * JMenuItem(am.bringToBackAction));
		 * 
		 * alignMenu = new JMenu(App.messages.getString("res.103"));
		 * alignMenu.setIcon(ReportAction.EMPTY_ICON); alignMenu.add(new
		 * JMenuItem(am.leftAlignAction)); alignMenu.add(new
		 * JMenuItem(am.rightAlignAction)); alignMenu.add(new
		 * JMenuItem(am.centerAlignAction)); alignMenu.add(new
		 * JMenuItem(am.topAlignAction)); alignMenu.add(new
		 * JMenuItem(am.bottomAlignAction)); alignMenu.add(new
		 * JMenuItem(am.verticalCenterAlignAction)); formatMenu.add(alignMenu);
		 * formatMenu.add(new JMenuItem(am.centerPageAction));
		 * 
		 * sizeMenu = new JMenu(App.messages.getString("res.104"));
		 * 
		 * sizeMenu.setIcon(ReportAction.EMPTY_ICON); sizeMenu.add(new
		 * JMenuItem(am.sameHeightAction)); sizeMenu.add(new
		 * JMenuItem(am.sameWidthAction)); sizeMenu.add(new
		 * JMenuItem(am.sameBothAction)); formatMenu.add(sizeMenu);
		 * 
		 */
	}
}
