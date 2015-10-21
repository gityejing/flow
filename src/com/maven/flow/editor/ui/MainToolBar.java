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
 * ��������������湤����
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class MainToolBar extends JToolBar {

	/**
	 * Action�д洢ActionId�ļ�ֵ
	 */
	private static final String ACTION_ID_KEY = "Action_Id";

	private static final String TOGLE_ID_KEY = "Is_togle";

	// �������������
	private WorkFlowEditor editor = null;

	// ����
	private MainActions actions = null;//�������Ѿ�����ʼ��).

	/**
	 * ����һ��������
	 * 
	 * @param editor
	 */
	public MainToolBar(WorkFlowEditor editor) {
		this.editor = editor;
		this.actions = editor.mainActions;

		this.init();
	}

	/**
	 * ��ʼ��������
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

		
		
		
		//�������ƣ����У�ճ����
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
		// ���ò�������
		// this.setFloatable(false);

	}

	/**
	 * ����ָ��ActionId�Ĺ�������ť�Ƿ����
	 * 
	 * @param actionId
	 *            �������
	 * @param enabled
	 *            �Ƿ����
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
	 * �鿴ָ����Action�ڹ��������Ƿ����
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
	 * ����һ������
	 * 
	 * @param actionId
	 *            ����ID
	 * @param iconLocation
	 *            ������ͼ��
	 * @param enabled �Ƿ����          
	 * @return
	 */
	protected Action createAction(final int actionId, String iconLocation,
			boolean enabled) {
		Action action = new AbstractAction(null, loadIcon(iconLocation)) {
			public void actionPerformed(ActionEvent event) {
				actions.executeAction(actionId, event);
			}
		};
		// ���ù�����ʾ(������ؼ�ʱ����ʾ)
		action.putValue(Action.SHORT_DESCRIPTION, actions.getActionTip(actionId));
		
		
		// ����ActionId
		action.putValue(ACTION_ID_KEY, new Integer(actionId));//
		
		// �����Ƿ����
		action.setEnabled(enabled);

		return action;
	}

	/**
	 * ������ϵͳ��صĶ������縴�ơ����С�ճ����
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
	 * ���¶�����ť��ͼ��
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
	 * ������Դ·��װ��ͼ��
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