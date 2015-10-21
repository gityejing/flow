/**
 * @(#)CustomizeToolBar.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import javax.swing.JToolBar;


/**
 * �Զ��幤����
 * ͨ���޸������ļ���������ӹ�������ť
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class CustomizeToolBar extends JToolBar {

	private WorkFlowEditor editor = null;

	/**
	 * ���췽��
	 * @param editor
	 */
	public CustomizeToolBar(WorkFlowEditor editor) {
		this.editor = editor;

		this.init();
	}

	/**
	 * ��ʼ���Զ��幤����
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

		//���ò��ɸ���
		this.setFloatable(false);
	}

	/**
	 * ��ȡ��������ť����
	 * @return
	 */
	public int getActioinCount() {
		return this.getComponentCount();
	}
}
