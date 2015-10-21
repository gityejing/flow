/**
 * @(#)FlowElementPanel.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.maven.flow.editor.model.ApprovalProcessObject;
import com.maven.flow.editor.model.EndElement;
import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.editor.model.HandleProcessObject;
import com.maven.flow.editor.model.StartElement;

/**
 * ��ʾ����Ԫ�ص����
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class FlowElementPanel extends JPanel {

	private WorkFlowEditor editor = null;

	public JList elementList = new JList();

	public FlowElementPanel(WorkFlowEditor editor) {
		this.setPreferredSize(new Dimension(140, 200));
		this.editor = editor;

		this.setLayout(new BorderLayout());

		JLabel l = new JLabel("�������");
		l.setIcon(UIUtil.loadImageIcon("resources/elements.gif"));
		this.add(l, BorderLayout.NORTH);
		this.add(new JScrollPane(elementList), BorderLayout.CENTER);

		//elementList.setLayoutOrientation(JList.VERTICAL_WRAP);

		elementList.setCellRenderer(new ElementListRender());

		elementList.setListData(this.getAllElements());
		elementList.setToolTipText("˫��ĳ������Ԫ�ؽ�����ӵ�����ͼ��");

		final WorkFlowEditor e = editor;
		elementList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				try {
					if (event.getClickCount() == 2) {//˫��ʱ��
						e.graphEditor
								.insertFlowElement((FlowElementObject) elementList
										.getSelectedValue().getClass()
										.newInstance());
					} else {
						//
					}
				} catch (Exception ex) {

				}
			}
		});
	}

	/**
	 * ֧�ֵ�����Ԫ��
	 * @return
	 */
	public FlowElementObject[] getAllElements() {
		FlowElementObject e0 = new StartElement();

		FlowElementObject e1 = new EndElement();

		FlowElementObject e2 = new HandleProcessObject();

		FlowElementObject e3 = new ApprovalProcessObject();

		return new FlowElementObject[] { e0, e1, e2, e3 };
	}

	static class ElementListRender extends JLabel implements ListCellRenderer {

		// This is the only method defined by ListCellRenderer.
		// We just reconfigure the JLabel each time we're called.

		public Component getListCellRendererComponent(JList list, Object value, // value to display
				int index, // cell index
				boolean isSelected, // is the cell selected
				boolean cellHasFocus) // the list and the cell have the focus
		{
			FlowElementObject fe = (FlowElementObject) value;
			setText(fe.getName());
			if (fe.getIconResource() != null)
				setIcon(UIUtil.loadImageIcon(fe.getIconResource()));
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}
	}
}
