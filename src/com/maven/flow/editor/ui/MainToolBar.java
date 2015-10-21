/**
 * @(#)WorkFlowToolBar.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;

/**
 * 工作流设计主界面工具条
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class MainToolBar extends JToolBar {

	/**
	 * Action中存储ActionId的键值
	 */
	private static final String ACTION_ID_KEY = "Action_Id";

	private static final String TOGLE_ID_KEY = "Is_togle";

	// 对主界面的引用
	private WorkFlowEditor editor = null;

	// 动作
	private MainActions actions = null;//动作（已经被初始化).

	/**
	 * 构建一个工具条
	 * 
	 * @param editor
	 */
	public MainToolBar(WorkFlowEditor editor) {
		this.editor = editor;
		this.actions = editor.mainActions;

		this.init();
	}

	/**
	 * 初始化工具条
	 * 
	 */
	protected void init() {
		JButton button = null;
		button = this.add(this.createAction(MainActions.ACTION_NEW,
				"resources/new.gif", true));

		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_OPEN,
				"resources/open.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_SAVE,
				"resources/save.gif", true));
		initActionSize(button);
		// this.add(this.createAction(MainActions.ACTION_SAVEALL,
		// "resources/saveall.gif", true));
		button = this.add(this.createAction(MainActions.ACTION_SAVEAS,
				"resources/saveas.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_IMPORT,
				"resources/import.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_EXPORT,
				"resources/export.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_DEPLOY,
				"resources/deploy.gif", true));
		initActionSize(button);
		this.addSeparator();

		button = this.add(this.createAction(MainActions.ACTION_UNDO,
				"resources/undo.gif", false));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_REDO,
				"resources/redo.gif", false));
		initActionSize(button);
		this.addSeparator();

		
		
		
		//创建复制，剪切，粘贴。
		button = this.add(this.createTransferAction(MainActions.ACTION_COPY,
				"resources/copy.gif", false));
		initActionSize(button);
		button = this.add(this.createTransferAction(MainActions.ACTION_CUT,
				"resources/cut.gif", false));
		initActionSize(button);
		button = this.add(this.createTransferAction(MainActions.ACTION_PASTE,
				"resources/paste.gif", true));
		initActionSize(button);
		
		
		
		
		button = this.add(this.createAction(MainActions.ACTION_DELETE,
				"resources/delete.gif", false));
		initActionSize(button);
		this.addSeparator();
		
		

		this.add(createToggleActionComponent(this.createAction(
				MainActions.ACTION_LINKMODE, "resources/link.gif", true)));
		this.addSeparator();

		
		
		
		
		button = this.add(this.createAction(MainActions.ACTION_TOFRONT,
				"resources/tofront.gif", false));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_TOBACK,
				"resources/toback.gif", false));
		initActionSize(button);
		this.addSeparator();

		button = this.add(this.createAction(MainActions.ACTION_ZOOM,
				"resources/zoom.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_ZOOMIN,
				"resources/zoomin.gif", true));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_ZOOMOUT,
				"resources/zoomout.gif", true));
		initActionSize(button);
		this.addSeparator();
		
		button = this.add(this.createAction(MainActions.ACTION_GROUP,
				"resources/group.gif", false));
		initActionSize(button);
		button = this.add(this.createAction(MainActions.ACTION_UNGROUP,
				"resources/ungroup.gif", false));
		initActionSize(button);
		this.addSeparator();
		
		
		this.add(createToggleActionComponent(this.createAction(
				MainActions.ACTION_STRUCTVIEW, "resources/structview.gif",
				false)));
		initActionSize(button);
		this.add(createToggleActionComponent(this.createAction(
				MainActions.ACTION_PROPERTIESVIEW,
				"resources/propertiesview.gif", true)));
		initActionSize(button);
		this.add(createToggleActionComponent(this.createAction(
				MainActions.ACTION_MESSAGEVIEW, "resources/messageview.gif",
				true)));
		
		initActionSize(button);
		// 设置不允许浮动
		// this.setFloatable(false);

	}

	/**
	 * 设置指定ActionId的工具条按钮是否可用
	 * 
	 * @param actionId
	 *            动作编号
	 * @param enabled
	 *            是否可用
	 */
	public void setActionEnabled(int actionId, boolean enabled) {
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (!(components[i] instanceof JButton))
				continue;
			JButton b = (JButton) components[i];
			Action a = b.getAction();
			if (new Integer(actionId).equals(a.getValue(ACTION_ID_KEY))) {
				a.setEnabled(enabled);
				break;
			}
		}
	}

	/**
	 * 查看指定的Action在工具条中是否可用
	 * 
	 * @param actionId
	 * @return
	 */
	public boolean isActionEnabled(int actionId) {
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (!(components[i] instanceof JButton))
				continue;
			JButton b = (JButton) components[i];
			Action a = b.getAction();
			if (new Integer(actionId).equals(a.getValue(ACTION_ID_KEY))) {
				return a.isEnabled();
			}
		}
		return true;
	}

	/**
	 * 创建一个动作
	 * 
	 * @param actionId
	 *            动作ID
	 * @param iconLocation
	 *            动作的图标
	 * @param enabled 是否可用          
	 * @return
	 */
	protected Action createAction(final int actionId, String iconLocation,
			boolean enabled) {
		Action action = new AbstractAction(null, loadIcon(iconLocation)) {
			public void actionPerformed(ActionEvent event) {
				actions.executeAction(actionId, event);
			}
		};
		// 设置工具提示(鼠标放入控件时的提示)
		action.putValue(Action.SHORT_DESCRIPTION, actions.getActionTip(actionId));
		
		
		// 设置ActionId
		action.putValue(ACTION_ID_KEY, new Integer(actionId));//
		
		// 设置是否可用
		action.setEnabled(enabled);

		return action;
	}

	/**
	 * 创建与系统相关的动作，如复制、剪切、粘贴等
	 * 
	 * @param actionid
	 * @param iconLocation
	 * @param enabled
	 * @return
	 */
	protected Action createTransferAction(final int actionId,
			String iconLocation, boolean enabled) {
		Action a = null;
		switch (actionId) {
		case MainActions.ACTION_COPY:
			a = TransferHandler.getCopyAction();
			break;
		case MainActions.ACTION_CUT:
			a = TransferHandler.getCutAction();
			break;
		case MainActions.ACTION_PASTE:
			a = TransferHandler.getPasteAction();
			break;
		default:
			return this.createAction(actionId, iconLocation, enabled);
		}
		a.putValue(Action.SMALL_ICON, this.loadIcon(iconLocation));
		a = editor.graphEditor.new EventRedirector(a);

		a.putValue(ACTION_ID_KEY, new Integer(actionId));
		a.setEnabled(enabled);

		return a;
	}

	/**
	 * 更新动作按钮的图标
	 * 
	 * @param actionId
	 * @param iconLocation
	 */
	public void updateActionIcon(int actionId, String iconLocation) {
		Component[] components = this.getComponents();
		for (int i = 0; i < components.length; i++) {
			if (!(components[i] instanceof JButton))
				continue;
			JButton b = (JButton) components[i];
			Action a = b.getAction();
			if (new Integer(actionId).equals(a.getValue(ACTION_ID_KEY))) {
				a.putValue(Action.SMALL_ICON, this.loadIcon(iconLocation));
			}
		}
	}

	/**
	 * 根据资源路径装载图标
	 * 
	 * @param location
	 * @return
	 */
	protected ImageIcon loadIcon(String location) {
		URL url = this.getClass().getClassLoader().getResource(location);
		if (url != null) {
			return new ImageIcon(url);
		} else {
			return null;
		}
	}

	protected JToggleButton createToggleActionComponent(final Action a) {
		String text = a != null ? (String) a.getValue(Action.NAME) : null;
		Icon icon = a != null ? (Icon) a.getValue(Action.SMALL_ICON) : null;
		boolean enabled = a != null ? a.isEnabled() : true;
		String tooltip = a != null ? (String) a
				.getValue(Action.SHORT_DESCRIPTION) : null;
		JToggleButton b = new JToggleButton(text, icon);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				a.actionPerformed(event);
			}
		});
		b.setSelected(true);
		if (icon != null) {
			b.putClientProperty("hideActionText", Boolean.TRUE);
		}
		b.setHorizontalTextPosition(JButton.CENTER);
		b.setVerticalTextPosition(JButton.BOTTOM);
		b.setEnabled(enabled);
		b.setToolTipText(tooltip);
		b.setPreferredSize(new Dimension(28, 25));
		b.setMinimumSize(new Dimension(28, 25));
		b.setMaximumSize(new Dimension(28, 25));
		return b;
	}

	private void initActionSize(JButton button) {

		button.setPreferredSize(new Dimension(28, 25));
		button.setMinimumSize(new Dimension(28, 25));
		button.setMaximumSize(new Dimension(28, 25));
	}
}