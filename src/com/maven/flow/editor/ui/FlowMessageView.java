/**
 * @(#)FlowMessagePanel.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.maven.flow.editor.model.Message;



/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public class FlowMessageView extends JPanel {

	private WorkFlowEditor editor = null;

	private JList msgList = new JList();//��Ϣ��ͼ���б�

	private Vector msgListData = new Vector();

	public FlowMessageView(WorkFlowEditor editor) {
		this.editor = editor;

		this.setPreferredSize(new Dimension(100, 150));

		this.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Action action = new AbstractAction("", UIUtil
				.loadImageIcon("resources/delete.gif")) {
			public void actionPerformed(ActionEvent e) {
				clearMessage();
			}
		};
		action.putValue(Action.SHORT_DESCRIPTION, "�����Ϣ");
		JButton btn = new JButton(action);
		btn.setBorder(new LineBorder(Color.gray));

		titlePanel.setBorder(null);
		titlePanel.add(new JLabel("��Ϣ��ͼ", UIUtil
				.loadImageIcon("resources/messageview.gif"), JLabel.LEFT));

		titlePanel.add(btn);

		//this.add(titlePanel, BorderLayout.NORTH);

		this.add(new JScrollPane(msgList), BorderLayout.CENTER);

		msgList.setCellRenderer(new MsgListRenderer());
		msgList.setFixedCellHeight(15);
		msgList.setListData(msgListData);
	}

	/**
	 * �����Ϣ
	 *
	 */
	public void clearMessage() {
		msgListData.clear();

		msgList.updateUI();
	}

	/**
	 * �����Ϣ����Ϣ��ͼ����ʾ
	 * @param message
	 */
	public void addMessage(Message message) {
		if (message == null)
			return;
		msgListData.add(message);

		msgList.updateUI();
	}

	/**
	 * ��һ����Ϣ���б���ʾ����ͼ��
	 * @param messages
	 */
	public void addMessage(List messages) {
		if (messages == null)
			return;
		for (Iterator it = messages.iterator(); it.hasNext();) {
			Object obj = it.next();
			if (obj instanceof Message) {
				this.addMessage((Message) obj);
			}
		}
	}

	/**
	 * ��ʾ��Ϣ
	 * @param message
	 */
	public void info(String message) {
		Message msg = new Message();
		msg.setType(Message.TYPE_INFO);
		msg.setMessage(message);
		this.addMessage(msg);
	}

	/**
	 * ��ʾ������Ϣ
	 * @param message
	 */
	public void warn(String message) {
		Message msg = new Message();
		msg.setType(Message.TYPE_WARN);
		msg.setMessage(message);
		this.addMessage(msg);
	}

	/**
	 * ��ʾ������Ϣ
	 * @param message
	 */
	public void error(String message) {
		Message msg = new Message();
		msg.setType(Message.TYPE_ERROR);
		msg.setMessage(message);
		this.addMessage(msg);
	}

	class MsgListRenderer extends JLabel implements ListCellRenderer {

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Message msg = (Message) value;

			switch (msg.getType()) {
				case Message.TYPE_WARN: {
					setForeground(Color.BLUE);
					setText(msg.getMessage());
					setIcon(UIUtil.loadImageIcon("resources/warn.gif"));
					break;
				}
				case Message.TYPE_ERROR: {
					setForeground(Color.RED);
					setText(msg.getMessage());
					setIcon(UIUtil.loadImageIcon("resources/error.gif"));
					break;
				}
				case Message.TYPE_INFO:
				default: {
					setForeground(list.getSelectionForeground());
					setText(msg.getMessage());
					setIcon(UIUtil.loadImageIcon("resources/info.gif"));
				}
			}
			if (isSelected) {
				setBackground(list.getSelectionBackground());
			} else {
				setBackground(list.getBackground());
			}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
		}

	}
}
