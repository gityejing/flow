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
 * 流程步骤文档结构属性适配器
 * 
 * 处理过程：
 * 1、弹出一个对话框
 * 2、如果当前步骤已经有一个文档结构树，则显示该结构
 * 3、如果没有，提示创建一个
 * 4、最后返回创建的文档结构树
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
		new DocTreeEditDlg("编辑步骤[" + this.processElement.getName() + "]的文档目录结构").setVisible(true);
		return null;
	}

	class DocTreeEditDlg extends JDialog {

		JTree tree = new JTree();

		JToolBar toolBar = new JToolBar();

		DefaultMutableTreeNode root = null;

		DocTreeEditDlg(String title) {
			//设置标题
			this.setTitle(title);
			//设置为模态对话框
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

			//添加各种控件到对话框中
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());

			//添加工具条
			this.initToolBar();
			toolBar.setFloatable(false);
			toolBar.setMargin(new Insets(0, 0, 0, 0));
			c.add(toolBar, BorderLayout.NORTH);

			//添加树结构
			c.add(new JScrollPane(tree), BorderLayout.CENTER);

			JPanel btnPanel = new JPanel();
			JButton btnOk = new JButton("确定");
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
		 * 初始化工具条
		 *
		 */
		void initToolBar() {
			Action action = new AbstractAction("", UIUtil.loadImageIcon("resources/addroot.gif")) {
				public void actionPerformed(ActionEvent event) {
					tree.getSelectionModel().clearSelection();
					addFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "增加根目录");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/add.gif")) {
				public void actionPerformed(ActionEvent event) {
					if (getSelectedNode() == null) {
						JOptionPane.showMessageDialog(DocTreeEditDlg.this, "请选择上级目录进行添加子目录");
						return;
					}
					addFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "增加子目录");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/delete.gif")) {
				public void actionPerformed(ActionEvent event) {
					removeFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "删除选中目录");
			toolBar.add(action);

			action = new AbstractAction("", UIUtil.loadImageIcon("resources/edit.gif")) {
				public void actionPerformed(ActionEvent event) {
					modifyFolder();
				}
			};
			action.putValue(Action.SHORT_DESCRIPTION, "修改目录");
			toolBar.add(action);
		}

		/**
		 * 查询子目录中是否有指定单位工程的目录
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

			//如果当前没有选中节点，则表示增加的是一个根目录
			TreePath path = tree.getSelectionPath();
			DefaultMutableTreeNode parent = this.getSelectedNode();
			if (parent == null) {
				parent = this.root;
			}

			//判断当前父节点下是否有同名的目录
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode c = (DefaultMutableTreeNode) parent.getChildAt(i);
				ProcessDocFolderInfo tmpInfo = (ProcessDocFolderInfo) c.getUserObject();
				if (info.getFolderName().equalsIgnoreCase(tmpInfo.getFolderName())) {
					JOptionPane.showMessageDialog(this, "目录[" + info.getFolderName() + "]已经存在，请使用其它的名字");
					return;
				}
			}
			//如果是单位工程目录，判断专业是否已经存在
			if(info.getSubProjectFlag() == 1 && this.checkMajor(info.getMajorId(), this.root)){
				JOptionPane.showMessageDialog(this, "已经存在相同专业的目录");
				return;
			}

			DefaultMutableTreeNode n = new DefaultMutableTreeNode();
			n.setUserObject(info);
			parent.add(n);

			tree.expandPath(path);

			tree.updateUI();
		}

		void modifyFolder() {
			//如果没有选中任何节点，直接返回
			DefaultMutableTreeNode node = this.getSelectedNode();
			if (node == null)
				return;
			ProcessDocFolderInfo info = (ProcessDocFolderInfo) node.getUserObject();

			DocInfoDlg dlg = new DocInfoDlg(info);
			dlg.setVisible(true);

			if (dlg.isCanceled())
				return;

			ProcessDocFolderInfo newInfo = dlg.getDocInfo();

			//判断同一个上级目录下是否有相同名称的目录，自己除外
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				DefaultMutableTreeNode c = (DefaultMutableTreeNode) parent.getChildAt(i);
				ProcessDocFolderInfo tmpInfo = (ProcessDocFolderInfo) c.getUserObject();
				if (!node.equals(c) && newInfo.getFolderName().equalsIgnoreCase(tmpInfo.getFolderName())) {
					JOptionPane.showMessageDialog(this, "目录[" + newInfo.getFolderName() + "]已经存在，请使用其它的名字");
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
			//如果没有选中任何节点，直接返回
			DefaultMutableTreeNode node = this.getSelectedNode();
			if (node == null) {
				JOptionPane.showMessageDialog(this, "请选择要删除的目录");
				return;
			}
			if (JOptionPane.showConfirmDialog(this, "确定要删除目录[" + node + "]吗？注意：删除该目录将会将其所有子目录一并删除", "删除目录", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				((DefaultMutableTreeNode) node.getParent()).remove(node);
				tree.getSelectionModel().clearSelection();

				tree.updateUI();
			}
		}

		/**
		 * 获取当前选中的目录节点
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
	 * 编辑目录信息的对话框
	 */
	class DocInfoDlg extends JDialog {

		private ProcessDocFolderInfo docInfo = null;

		boolean canceled = false;

		JTextField nameField = new JTextField(28);

		JTextField descField = new JTextField(28);

		JCheckBox ckb = new JCheckBox("单位工程目录");

		JComboBox cbb = new JComboBox(getMajors());

		DocInfoDlg(ProcessDocFolderInfo info) {

			//设置为模态对话框
			this.setModal(true);
			this.setAlwaysOnTop(true);

			this.setSize(420, 160);
			this.setResizable(false);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2, (int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.init();

			this.docInfo = new ProcessDocFolderInfo();
			if (info == null) {
				this.setTitle("增加目录");
			} else {
				this.setTitle("编辑目录[" + info.getFolderName() + "]");
				this.updateInfo(info);
			}
		}

		void init() {
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());

			JPanel infoPanel = new JPanel();
			infoPanel.setLayout(new GridLayout(3, 1));

			JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p1.add(new JLabel("目录名称："));
			p1.add(nameField);
			infoPanel.add(p1);

			JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p2.add(new JLabel("目录描述："));
			p2.add(descField);
			infoPanel.add(p2);

			JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p3.add(new JLabel("单位工程设置："));
			p3.add(ckb);
			p3.add(new JLabel("    专业："));
			p3.add(cbb);
			infoPanel.add(p3);

			c.add(infoPanel, BorderLayout.CENTER);

			JPanel btnPanel = new JPanel();
			JButton okBtn = new JButton("确定");
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

			JButton cncBtn = new JButton("取消");
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
			//设置相关的信息
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

		//进行验证
		boolean check() {
			if (nameField.getText() == null || nameField.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(this, "目录名称不能为空");
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
	 * 获取专业信息
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
				FlowGraphManager.getEditor().messageView.error("获取专业信息失败，错误信息：" + ex.getMessage());
				majors = new KeyValueObject[0];
			}
		}
		return majors;
	}

	public static void main(String[] args) {
		new ProcessDocPropertyAdapter(null).new DocInfoDlg(null).setVisible(true);
	}
}
