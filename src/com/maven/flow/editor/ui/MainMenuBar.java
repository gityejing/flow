package com.maven.flow.editor.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenuBar extends JMenuBar {

	private JMenu ppmAlignMenu;

	private JMenu ppmSizeMenu;

	private JMenu alignMenu;

	private JMenu sizeMenu;

    //对主界面的引用
    private WorkFlowEditor editor = null;

    // 动作
    private MainActions actions = null;
    /**
     * 构建主菜单。
     * @param editor
     */
	public MainMenuBar(WorkFlowEditor editor) {
        this.editor = editor;
        this.actions = editor.mainActions;
        this.init();
    }

	/**
	 * 初始化主菜单栏
	 */
	private void init() {
		JMenu fileMenu = new JMenu("文件", true);
		fileMenu.setMnemonic('F');
		fileMenu.add(new JMenuItem("新建"));
		fileMenu.add(new JMenuItem("打开"));
		fileMenu.add(new JMenuItem("保存"));
		fileMenu.add(new JMenuItem("另存为..."));
		fileMenu.add(new JMenuItem("关闭"));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("页面设置"));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("文件设置"));
		// MruPopupMenu reOpenMenu = new MruPopupMenu(App.getMruManager());
		// reOpenMenu.append(fileMenu);
		// reOpenMenu.refresh();
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem("退出"));
		this.add(fileMenu);
		
		JMenu editMenu = new JMenu("编辑");
		editMenu.setMnemonic('E');
		editMenu.add(new JMenuItem("撤消"));
		editMenu.add(new JMenuItem("重做"));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem("复制"));
		editMenu.add(new JMenuItem("剪贴"));
		editMenu.add(new JMenuItem("粘贴"));
		editMenu.add(new JMenuItem("删除"));
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
