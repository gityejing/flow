/**
 * @(#)CustomizeToolBar.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import javax.swing.JToolBar;


/**
 * 自定义工具条
 * 通过修改配置文件，可以添加工具条按钮
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class CustomizeToolBar extends JToolBar {

	private WorkFlowEditor editor = null;

	/**
	 * 构造方法
	 * @param editor
	 */
	public CustomizeToolBar(WorkFlowEditor editor) {
		this.editor = editor;

		this.init();
	}

	/**
	 * 初始化自定义工具条
	 *
	 */
	protected void init() {
//		this.add(new AbstractAction("Test") {
//			public void actionPerformed(ActionEvent event) {
//				editor.graphEditor.graph.getGraphLayoutCache().insert(
//						new FlowElementCell(null, getClass().getClassLoader()
//								.getResource("resources/test.jpg")));
//			}
//		});

		//设置不可浮动
		this.setFloatable(false);
	}

	/**
	 * 获取工具条按钮个数
	 * @return
	 */
	public int getActioinCount() {
		return this.getComponentCount();
	}
}
