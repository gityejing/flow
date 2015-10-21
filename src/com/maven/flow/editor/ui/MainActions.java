/**
 * @(#)WorkFlowActions.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.util.EventObject;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class MainActions {

	/**
	 * �������½�����
	 */
	public static final int ACTION_NEW = 0;

	/**
	 * ����������������
	 */
	public static final int ACTION_OPEN = 1;

	/**
	 * ��������������
	 */
	public static final int ACTION_SAVE = 2;

	/**
	 * ����������
	 */
	public static final int ACTION_COPY = 3;

	/**
	 * ����������
	 */
	public static final int ACTION_CUT = 4;

	/**
	 * ������ճ��
	 */
	public static final int ACTION_PASTE = 5;

	/**
	 * ������ɾ��ѡ��Ԫ��
	 */
	public static final int ACTION_DELETE = 6;

	/**
	 * ����������
	 */
	public static final int ACTION_UNDO = 7;

	/**
	 * �������ظ�
	 */
	public static final int ACTION_REDO = 8;

	/**
	 * ���������õ�����
	 */
	public static final int ACTION_TOFRONT = 9;

	/**
	 * ���������õ��ײ�
	 */
	public static final int ACTION_TOBACK = 10;

	/**
	 * ������ʵ�ʴ�С
	 */
	public static final int ACTION_ZOOM = 11;

	/**
	 * �������Ŵ�
	 */
	public static final int ACTION_ZOOMIN = 12;

	/**
	 * ��������С
	 */
	public static final int ACTION_ZOOMOUT = 13;

	/**
	 * ���������
	 */
	public static final int ACTION_GROUP = 14;

	/**
	 * ������ȡ�����
	 */
	public static final int ACTION_UNGROUP = 15;

	/**
	 * ��������������
	 */
	public static final int ACTION_IMPORT = 16;

	/**
	 * ��������������
	 */
	public static final int ACTION_EXPORT = 17;

	/**
	 * ��������������
	 */
	public static final int ACTION_SAVEALL = 18;

	/**
	 * �������򿪡����ؽṹ��ͼ
	 */
	public static final int ACTION_STRUCTVIEW = 19;

	/**
	 * �������򿪡�����������ͼ
	 */
	public static final int ACTION_PROPERTIESVIEW = 20;

	/**
	 * ��������ʾ��������Ϣ��ͼ
	 */
	public static final int ACTION_MESSAGEVIEW = 21;

	/**
	 * ��������������ģʽ
	 */
	public static final int ACTION_LINKMODE = 22;

	/**
	 * ��������������
	 */
	public static final int ACTION_DEPLOY = 23;

	/**
	 * ���������Ϊ
	 */
	public static final int ACTION_SAVEAS = 24;

	// �༭��������
	private WorkFlowEditor editor = null;

	/**
	 * ���췽��
	 * 
	 * @param editor
	 */
	public MainActions(WorkFlowEditor editor) {
		this.editor = editor;
	}

	/**
	 * ִ�ж���
	 * 
	 * @param actionId
	 * @param event
	 */
	public void executeAction(int actionId, EventObject event) {
		// ִ�ж���
		// TODO: ִ�ж����Ĵ���
		switch (actionId) {
		case ACTION_UNDO:
			editor.graphEditor.undo();
			break;
		case ACTION_REDO:
			editor.graphEditor.redo();
			break;
		case ACTION_DELETE:
			editor.graphEditor.deleteSelectedCells();
			break;
		case ACTION_GROUP:
			editor.graphEditor.groupSelectedCells();
			break;
		case ACTION_UNGROUP:
			editor.graphEditor.ungroupSelectedCells();
			break;
		case ACTION_ZOOMIN:
			editor.graphEditor.zoomIn();
			break;
		case ACTION_ZOOM:
			editor.graphEditor.zoom();
			break;
		case ACTION_ZOOMOUT:
			editor.graphEditor.zoomOut();
			break;
		case ACTION_NEW:
			// editor.graphEditor.addNewGraph();
			FlowGraphManager.addNewGraph();
			break;
		case ACTION_IMPORT:
			// editor.graphEditor.setFlowGraph(new
			// FileAdapter().load(editor.graphEditor));
			FlowGraphManager.importGraph();
			break;
		case ACTION_EXPORT:
			// new
			// FileAdapter().save(editor.graphEditor,editor.graphEditor.graph);
			FlowGraphManager.exportCurrentGraph();
			break;
		case ACTION_TOFRONT:
			editor.graphEditor.toFront();
			break;
		case ACTION_TOBACK:
			editor.graphEditor.toBack();
			break;
		case ACTION_STRUCTVIEW:
			if (editor.strutcView != null)
				editor.strutcView.setVisible(!editor.strutcView.isVisible());
			break;
		case ACTION_PROPERTIESVIEW:
			if (editor.propertiesView != null)
				editor.propertiesView.setVisible(!editor.propertiesView
						.isVisible());
			break;
		case ACTION_MESSAGEVIEW:
			if (editor.messageView != null)
				editor.messageView.setVisible(!editor.messageView.isVisible());
			break;
		case ACTION_LINKMODE:
			editor.graphEditor.graph.setPortsVisible(!editor.graphEditor.graph.isPortsVisible());
			break;
		case ACTION_DEPLOY:
			FlowGraphManager.deployCurrentGraph();
			break;
		case ACTION_SAVE:
			FlowGraphManager.saveCurrentGraph();
			break;
		case ACTION_OPEN:
			FlowGraphManager.openGraph();
			break;
		case ACTION_SAVEAS:
			FlowGraphManager.saveCurrentGraphAs();
			break;
		default:
			System.out.println("����������" + this.getActionTip(actionId));
		}
	}

	/**
	 * ���ݶ���ID��ȡ��������ʾ
	 * 
	 * @param actionId
	 * @return
	 */
	public String getActionTip(int actionId) {
		switch (actionId) {
		case ACTION_COPY:
			return "����";
		case ACTION_CUT:
			return "����";
		case ACTION_DELETE:
			return "ɾ��";
		case ACTION_DEPLOY:
			return "��������";
		case ACTION_EXPORT:
			return "��������";
		case ACTION_ZOOM:
			return "ʵ�ʴ�С";
		case ACTION_GROUP:
			return "���";
		case ACTION_IMPORT:
			return "��������";
		case ACTION_LINKMODE:
			return "����ģʽ";
		case ACTION_MESSAGEVIEW:
			return "��ʾ/������Ϣ��ͼ";
		case ACTION_NEW:
			return "�½�����";
		case ACTION_OPEN:
			return "������";
		case ACTION_PASTE:
			return "ճ��";
		case ACTION_PROPERTIESVIEW:
			return "��ʾ/����������ͼ";
		case ACTION_REDO:
			return "����";
		case ACTION_SAVE:
			return "��������";
		case ACTION_SAVEALL:
			return "������������";
		case ACTION_STRUCTVIEW:
			return "��ʾ/���ؽṹ��ͼ";
		case ACTION_TOBACK:
			return "����һ��";
		case ACTION_TOFRONT:
			return "����һ��";
		case ACTION_UNDO:
			return "����";
		case ACTION_UNGROUP:
			return "�������";
		case ACTION_ZOOMIN:
			return "�Ŵ�";
		case ACTION_ZOOMOUT:
			return "��С";
		case ACTION_SAVEAS:
			return "���Ϊ...";
		default:
			return "";
		}
	}
}