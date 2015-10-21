/**
 * @(#)ProcessDocPropertyAdapter.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.maven.flow.editor.adapter.CustomizePropertyAdapter;
import com.maven.flow.editor.adapter.ResultObject;
import com.maven.flow.editor.adapter.URLConnectionHelper;
import com.maven.flow.editor.bean.MajorBase;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.editor.model.ProcessDocFolderInfo;
import com.maven.flow.editor.model.ProcessElementObject;
import com.maven.flow.editor.ui.FlowGraphManager;
import com.maven.flow.editor.ui.UIUtil;
import com.maven.flow.util.Const;

/**
 * ���̲����ĵ��ṹ����������
 * 
 * ������̣�
 * 1������һ���Ի���
 * 2�������ǰ�����Ѿ���һ���ĵ��ṹ��������ʾ�ýṹ
 * 3�����û�У���ʾ����һ��
 * 4����󷵻ش������ĵ��ṹ��
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public class ProcessDocPropertyAdapter implements CustomizePropertyAdapter {

	private ProcessElementObject processElement = null;

	//private static KeyValueObject[] majors = null;

	public ProcessDocPropertyAdapter(ProcessElementObject peo) {
		this.processElement = peo;
	}

	/**
	 * 
	 */
	public Object getCustomizePropertyValue() {
		new DocTreeEditDlg("�༭����[" + this.processElement.getName() + "]���ĵ�Ŀ¼�ṹ").setVisible(true);
		return null;
	}

	class DocTreeEditDlg extends JDialog {

		JTree tree = new JTree();

		JToolBar toolBar = new JToolBar();

		DefaultMutableTreeNode root = null;

		DocTreeEditDlg(String title) {
			//���ñ���
			this.setTitle(title);
			//����Ϊģ̬�Ի���
			this.setModal(true);
			this.setAlwaysOnTop(true);

			this.setSize(400, 500);
			this.setResizable(false);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2, (int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.init();
		}

		void init() {
			this.root = processElement.getDocTreeRoot();
			tree.setModel(new DefaultTreeModel(this.root));
			tree.setRootVisible(false);
			tree.setToggleClickCount(1);

			//��Ӹ��ֿؼ����Ի�����
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());

			//��ӹ�����
			this.initToolBar();
			toolBar.setFloatable(false);
			toolBar.setMargin(new Insets(0, 0, 0, 0));
			c.add(toolBar, BorderLayout.NORTH);

			//������ṹ
			c.add(new JScrollPane(tree), BorderLayout.CENTER);

			JPanel btnPanel = new JPanel();
			JButton btnOk = new JButton("ȷ��");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					dispose();
				}
			});
			btnPanel.add(btnOk);

			c.add(btnPanel, BorderLayout.SOUTH);

			tree.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent event) {
					if (event.getClickCount() == 2)
						modifyFolder();
				}
			});
		}

		/**
		 * ��ʼ��������
		 *
		 */
		void initToolBar() {
			Action action = new AbstractAction("", UIUtil.loadImageIcon("resources/addroot.gif")) {
				public void actionPerformed(ActionEvent event) {
					tree.getSelectionModel().clearSelection();
					addFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "���Ӹ�Ŀ¼");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/add.gif")) {
				public void actionPerformed(ActionEvent event) {
					if (getSelectedNode() == null) {
						JOptionPane.showMessageDialog(DocTreeEditDlg.this, "��ѡ���ϼ�Ŀ¼���������Ŀ¼");
						return;
					}
					addFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "������Ŀ¼");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/delete.gif")) {
				public void actionPerformed(ActionEvent event) {
					removeFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "ɾ��ѡ��Ŀ¼");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/edit.gif")) {
				public void actionPerformed(ActionEvent event) {
					modifyFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "�޸�Ŀ¼");
			toolBar.add(action);
		}

		/**
		 * ��ѯ��Ŀ¼���Ƿ���ָ����λ���̵�Ŀ¼
		 * @param majorId
		 * @param parentNode
		 * @return
		 */
		boolean checkMajor(long majorId, DefaultMutableTreeNode node) {
			if (node == null)
				return false;

			ProcessDocFolderInfo f = (ProcessDocFolderInfo) node.getUserObject();
			if (f.getSubProjectFlag() == 1 && f.getMajorId() == majorId) {
				return true;
			} else {

				for (int i = 0; i < node.getChildCount(); i++) {
					DefaultMutableTreeNode n = (DefaultMutableTreeNode) node.getChildAt(i);
					if(this.checkMajor(majorId, n)){
						return true;
					}
				}

				return false;
			}
		}

		void addFolder() {
			DocInfoDlg dlg = new DocInfoDlg(null);
			dlg.setVisible(true);

			if (dlg.isCanceled())
				return;

			ProcessDocFolderInfo info = dlg.getDocInfo();

			//�����ǰû��ѡ�нڵ㣬���ʾ���ӵ���һ����Ŀ¼
			TreePath path = tree.getSelectionPath();
			DefaultMutableTreeNode parent = this.getSelectedNode();
			if (parent == null) {
				parent = this.root;
			}

			//�жϵ�ǰ���ڵ����Ƿ���ͬ����Ŀ¼
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode c = (DefaultMutableTreeNode) parent.getChildAt(i);
				ProcessDocFolderInfo tmpInfo = (ProcessDocFolderInfo) c.getUserObject();
				if (info.getFolderName().equalsIgnoreCase(tmpInfo.getFolderName())) {
					JOptionPane.showMessageDialog(this, "Ŀ¼[" + info.getFolderName() + "]�Ѿ����ڣ���ʹ������������");
					return;
				}
			}
			//����ǵ�λ����Ŀ¼���ж�רҵ�Ƿ��Ѿ�����
			if(info.getSubProjectFlag() == 1 && this.checkMajor(info.getMajorId(), this.root)){
				JOptionPane.showMessageDialog(this, "�Ѿ�������ͬרҵ��Ŀ¼");
				return;
			}

			DefaultMutableTreeNode n = new DefaultMutableTreeNode();
			n.setUserObject(info);
			parent.add(n);

			tree.expandPath(path);

			tree.updateUI();
		}

		void modifyFolder() {
			//���û��ѡ���κνڵ㣬ֱ�ӷ���
			DefaultMutableTreeNode node = this.getSelectedNode();
			if (node == null)
				return;
			ProcessDocFolderInfo info = (ProcessDocFolderInfo) node.getUserObject();

			DocInfoDlg dlg = new DocInfoDlg(info);
			dlg.setVisible(true);

			if (dlg.isCanceled())
				return;

			ProcessDocFolderInfo newInfo = dlg.getDocInfo();

			//�ж�ͬһ���ϼ�Ŀ¼���Ƿ�����ͬ���Ƶ�Ŀ¼���Լ�����
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode c = (DefaultMutableTreeNode) parent.getChildAt(i);
				ProcessDocFolderInfo tmpInfo = (ProcessDocFolderInfo) c.getUserObject();
				if (!node.equals(c) && newInfo.getFolderName().equalsIgnoreCase(tmpInfo.getFolderName())) {
					JOptionPane.showMessageDialog(this, "Ŀ¼[" + newInfo.getFolderName() + "]�Ѿ����ڣ���ʹ������������");
					return;
				}
			}

			info.setFolderName(newInfo.getFolderName());
			info.setFolderDesc(newInfo.getFolderDesc());
			info.setSubProjectFlag(newInfo.getSubProjectFlag());
			info.setMajorId(newInfo.getMajorId());

			tree.updateUI();
		}

		void removeFolder() {
			//���û��ѡ���κνڵ㣬ֱ�ӷ���
			DefaultMutableTreeNode node = this.getSelectedNode();
			if (node == null) {
				JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ����Ŀ¼");
				return;
			}
			if (JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ��Ŀ¼[" + node + "]��ע�⣺ɾ����Ŀ¼���Ὣ��������Ŀ¼һ��ɾ��", "ɾ��Ŀ¼", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				((DefaultMutableTreeNode) node.getParent()).remove(node);
				tree.getSelectionModel().clearSelection();

				tree.updateUI();
			}
		}

		/**
		 * ��ȡ��ǰѡ�е�Ŀ¼�ڵ�
		 * @return
		 */
		DefaultMutableTreeNode getSelectedNode() {
			TreePath path = tree.getSelectionPath();
			if (path == null || path.getLastPathComponent() == null)
				return null;
			else
				return (DefaultMutableTreeNode) path.getLastPathComponent();
		}
	}

	/**
	 * �༭Ŀ¼��Ϣ�ĶԻ���
	 */
	class DocInfoDlg extends JDialog {

		private ProcessDocFolderInfo docInfo = null;

		boolean canceled = false;

		JTextField nameField = new JTextField(28);

		JTextField descField = new JTextField(28);

		JCheckBox ckb = new JCheckBox("��λ����Ŀ¼");

		JComboBox cbb = new JComboBox(getMajors());

		DocInfoDlg(ProcessDocFolderInfo info) {

			//����Ϊģ̬�Ի���
			this.setModal(true);
			this.setAlwaysOnTop(true);

			this.setSize(420, 160);
			this.setResizable(false);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2, (int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.init();

			this.docInfo = new ProcessDocFolderInfo();
			if (info == null) {
				this.setTitle("����Ŀ¼");
			} else {
				this.setTitle("�༭Ŀ¼[" + info.getFolderName() + "]");
				this.updateInfo(info);
			}
		}

		void init() {
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());

			JPanel infoPanel = new JPanel();
			infoPanel.setLayout(new GridLayout(3, 1));

			JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p1.add(new JLabel("Ŀ¼���ƣ�"));
			p1.add(nameField);
			infoPanel.add(p1);

			JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p2.add(new JLabel("Ŀ¼������"));
			p2.add(descField);
			infoPanel.add(p2);

			JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p3.add(new JLabel("��λ�������ã�"));
			p3.add(ckb);
			p3.add(new JLabel("    רҵ��"));
			p3.add(cbb);
			infoPanel.add(p3);

			c.add(infoPanel, BorderLayout.CENTER);

			JPanel btnPanel = new JPanel();
			JButton okBtn = new JButton("ȷ��");
			okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (!check())
						return;
					canceled = false;
					setDocInfo();
					dispose();
				}
			});
			btnPanel.add(okBtn);

			JButton cncBtn = new JButton("ȡ��");
			cncBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					canceled = true;
					dispose();
				}
			});
			btnPanel.add(cncBtn);

			c.add(btnPanel, BorderLayout.SOUTH);

			ckb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					cbb.setEnabled(ckb.isSelected());
				}
			});

			cbb.setEnabled(false);
		}

		void updateInfo(ProcessDocFolderInfo info) {
			nameField.setText(info.getFolderName());
			descField.setText(info.getFolderDesc());
			ckb.setSelected(info.getSubProjectFlag() == 1);
			cbb.setEnabled(ckb.isSelected());
			int itemCount = cbb.getItemCount();
			for (int i = 0; i < itemCount; i++) {
				KeyValueObject kvo = (KeyValueObject) cbb.getItemAt(i);
				if (kvo.getKey().equals(new Long(info.getMajorId()))) {
					cbb.setSelectedIndex(i);
					break;
				}
			}
		}

		void setDocInfo() {
			//������ص���Ϣ
			docInfo.setFolderName(nameField.getText().trim());
			docInfo.setFolderDesc(descField.getText().trim());
			docInfo.setSubProjectFlag(ckb.isSelected() ? 1 : 0);
			if (cbb.getSelectedIndex() != -1) {
				docInfo.setMajorId(((Long) ((KeyValueObject) cbb.getSelectedItem()).getKey()).longValue());
			}
		}

		ProcessDocFolderInfo getDocInfo() {
			return docInfo;
		}

		//������֤
		boolean check() {
			if (nameField.getText() == null || nameField.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "Ŀ¼���Ʋ���Ϊ��");
				nameField.requestFocus();
				return false;
			}
			return true;
		}

		public boolean isCanceled() {
			return canceled;
		}

	}

	/**
	 * ��ȡרҵ��Ϣ
	 * @return
	 */
	private KeyValueObject[] getMajors() {
		KeyValueObject[] majors = null;
		if (majors == null) {
			try {
				String server = Const.server;

				if (server.indexOf("?") != -1)
					server += "&method=getmajor";
				else
					server += "?method=getmajor";
				
				ResultObject result = URLConnectionHelper.sendRequest(server, null);

				if (result.getResult() == ResultObject.SUCCESS) {
					List majorsList = (List) result.getObject();
					if (majorsList != null) {
						majors = new KeyValueObject[majorsList.size()];
						for (int i = 0; i < majors.length; i++) {
							MajorBase mb = (MajorBase) majorsList.get(i);
							majors[i] = new KeyValueObject(new Long(mb.getMajorID()), mb.getMajorName());
						}
					} else {
						majors = new KeyValueObject[0];
					}
				} else {
					majors = new KeyValueObject[0];
				}
			} catch (Exception ex) {
				FlowGraphManager.getEditor().messageView.error("��ȡרҵ��Ϣʧ�ܣ�������Ϣ��" + ex.getMessage());
				majors = new KeyValueObject[0];
			}
		}
		return majors;
	}

	public static void main(String[] args) {
		new ProcessDocPropertyAdapter(null).new DocInfoDlg(null).setVisible(true);
	}
}
