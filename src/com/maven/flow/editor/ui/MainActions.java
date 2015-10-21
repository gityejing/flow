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
	 * 动作：新建流程
	 */
	public static final int ACTION_NEW = 0;

	/**
	 * 动作：打开已有流程
	 */
	public static final int ACTION_OPEN = 1;

	/**
	 * 动作：保存流程
	 */
	public static final int ACTION_SAVE = 2;

	/**
	 * 动作：复制
	 */
	public static final int ACTION_COPY = 3;

	/**
	 * 动作：剪切
	 */
	public static final int ACTION_CUT = 4;

	/**
	 * 动作：粘贴
	 */
	public static final int ACTION_PASTE = 5;

	/**
	 * 动作：删除选中元素
	 */
	public static final int ACTION_DELETE = 6;

	/**
	 * 动作：撤销
	 */
	public static final int ACTION_UNDO = 7;

	/**
	 * 动作：重复
	 */
	public static final int ACTION_REDO = 8;

	/**
	 * 动作：设置到顶层
	 */
	public static final int ACTION_TOFRONT = 9;

	/**
	 * 动作：设置到底层
	 */
	public static final int ACTION_TOBACK = 10;

	/**
	 * 动作：实际大小
	 */
	public static final int ACTION_ZOOM = 11;

	/**
	 * 动作：放大
	 */
	public static final int ACTION_ZOOMIN = 12;

	/**
	 * 动作：缩小
	 */
	public static final int ACTION_ZOOMOUT = 13;

	/**
	 * 动作：组合
	 */
	public static final int ACTION_GROUP = 14;

	/**
	 * 动作：取消组合
	 */
	public static final int ACTION_UNGROUP = 15;

	/**
	 * 动作：导入流程
	 */
	public static final int ACTION_IMPORT = 16;

	/**
	 * 动作：导出流程
	 */
	public static final int ACTION_EXPORT = 17;

	/**
	 * 动作：保存所有
	 */
	public static final int ACTION_SAVEALL = 18;

	/**
	 * 动作：打开、隐藏结构视图
	 */
	public static final int ACTION_STRUCTVIEW = 19;

	/**
	 * 动作：打开、隐藏属性视图
	 */
	public static final int ACTION_PROPERTIESVIEW = 20;

	/**
	 * 动作：显示、隐藏消息视图
	 */
	public static final int ACTION_MESSAGEVIEW = 21;

	/**
	 * 动作：设置连线模式
	 */
	public static final int ACTION_LINKMODE = 22;

	/**
	 * 动作：发布流程
	 */
	public static final int ACTION_DEPLOY = 23;

	/**
	 * 动作：另存为
	 */
	public static final int ACTION_SAVEAS = 24;

	// 编辑器主界面
	private WorkFlowEditor editor = null;

	/**
	 * 构造方法
	 * 
	 * @param editor
	 */
	public MainActions(WorkFlowEditor editor) {
		this.editor = editor;
	}

	/**
	 * 执行动作
	 * 
	 * @param actionId
	 * @param event
	 */
	public void executeAction(int actionId, EventObject event) {
		// 执行动作
		// TODO: 执行动作的代码
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
			System.out.println("触发动作：" + this.getActionTip(actionId));
		}
	}

	/**
	 * 根据动作ID获取动作的提示
	 * 
	 * @param actionId
	 * @return
	 */
	public String getActionTip(int actionId) {
		switch (actionId) {
		case ACTION_COPY:
			return "复制";
		case ACTION_CUT:
			return "剪切";
		case ACTION_DELETE:
			return "删除";
		case ACTION_DEPLOY:
			return "发布流程";
		case ACTION_EXPORT:
			return "导出流程";
		case ACTION_ZOOM:
			return "实际大小";
		case ACTION_GROUP:
			return "组合";
		case ACTION_IMPORT:
			return "导入流程";
		case ACTION_LINKMODE:
			return "连线模式";
		case ACTION_MESSAGEVIEW:
			return "显示/隐藏消息视图";
		case ACTION_NEW:
			return "新建流程";
		case ACTION_OPEN:
			return "打开流程";
		case ACTION_PASTE:
			return "粘贴";
		case ACTION_PROPERTIESVIEW:
			return "显示/隐藏属性视图";
		case ACTION_REDO:
			return "重做";
		case ACTION_SAVE:
			return "保存流程";
		case ACTION_SAVEALL:
			return "保存所有流程";
		case ACTION_STRUCTVIEW:
			return "显示/隐藏结构视图";
		case ACTION_TOBACK:
			return "往下一层";
		case ACTION_TOFRONT:
			return "往上一层";
		case ACTION_UNDO:
			return "撤销";
		case ACTION_UNGROUP:
			return "撤销组合";
		case ACTION_ZOOMIN:
			return "放大";
		case ACTION_ZOOMOUT:
			return "缩小";
		case ACTION_SAVEAS:
			return "另存为...";
		default:
			return "";
		}
	}
}