/**
 * @(#)CustomerAction.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.maven.flow.editor.ui.WorkFlowEditor;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public abstract class CustomerAction extends AbstractAction {

	protected WorkFlowEditor editor = null;

	public abstract void actionPerformed(ActionEvent e);

	/**
	 * ��ȡ�༭��������
	 * @return
	 */
	public WorkFlowEditor getEditor() {
		return editor;
	}

	/**
	 * ���ñ༭�������档��ϵͳ����
	 * @param editor
	 */
	public void setEditor(WorkFlowEditor editor) {
		this.editor = editor;
	}

}
