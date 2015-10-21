/**
 * @(#)ProcessFolderAccessPropertyAdapter.java 2007-6-11
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jgraph.graph.DefaultGraphCell;

import com.maven.flow.editor.adapter.CustomizePropertyAdapter;
import com.maven.flow.editor.model.EndElement;
import com.maven.flow.editor.model.ProcessDocFolderAccessInfo;
import com.maven.flow.editor.model.ProcessDocFolderInfo;
import com.maven.flow.editor.model.ProcessElementObject;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.editor.ui.FlowGraphManager;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-11
 * @since       JDK1.5
 */

public class ProcessFolderAccessPropertyAdapter extends JDialog implements CustomizePropertyAdapter {

	JTree tree = new JTree();

	private JCheckBox deleteCkb = new JCheckBox("可删除");

	private JCheckBox viewCkb = new JCheckBox("可查看");

	private JCheckBox operateCkb = new JCheckBox("可操作");

	private JCheckBox addCkb = new JCheckBox("可增加");

	private JCheckBox updateCkb = new JCheckBox("可修改");

	private JCheckBox allCkb = new JCheckBox("全部");

	DefaultMutableTreeNode root = null;

	private ProcessElementObject process = null;//对相应步骤的一个引用

	private Map accessMap = new HashMap();//缓存

	private ProcessDocFolderAccessInfo currentInfo = null;//当前选中的信息

	private boolean isNew = false;

	public ProcessFolderAccessPropertyAdapter(ProcessElementObject process) {
		this.process = process;

		if (this.process.getDocFolderAccess() != null) {
			for (Iterator it = this.process.getDocFolderAccess().iterator(); it.hasNext();) {
				ProcessDocFolderAccessInfo info = (ProcessDocFolderAccessInfo) it.next();

				accessMap.put(info.getFolder(), info);
			}
		}

		this.init();
	}

	public Object getCustomizePropertyValue() {
		this.setVisible(true);
		return null;
	}

	void init() {
		//设置标题
		this.setTitle("设置步骤[" + this.process.getName() + "]的人员的文档权限");
		//设置为模态对话框
		this.setModal(true);
		this.setAlwaysOnTop(true);

		this.setSize(500, 500);
		this.setResizable(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2, (int) (screenSize.getHeight() - this.getHeight()) / 2);
		this.root = this.getFlowDocFolderRoot();
		tree.setModel(new DefaultTreeModel(this.root));
		tree.setCellRenderer(new DocFolderAccessTreeCellRenderer());
		//			editor.setClickCountToStart(0);

		//tree.setEditable(true);
		//tree.setRootVisible(false);
		tree.setToggleClickCount(2);

		//添加各种控件到对话框中
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());

		//增加设置按钮
		JPanel ckbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ckbPanel.add(new JLabel("设置权限："));
		ckbPanel.add(allCkb);
		ckbPanel.add(deleteCkb);
		ckbPanel.add(viewCkb);
		ckbPanel.add(operateCkb);
		ckbPanel.add(addCkb);
		ckbPanel.add(updateCkb);
		c.add(ckbPanel, BorderLayout.NORTH);

		//添加树结构
		c.add(new JScrollPane(tree), BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		JButton btnOk = new JButton("确定");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setToProcess();
				dispose();
			}
		});
		btnPanel.add(btnOk);
		JButton cBtn = new JButton("取消");
		cBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

		c.add(btnPanel, BorderLayout.SOUTH);

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = getSelectedNode();
				updateCkbState(null);

				if (node == null) {
					updateCkbEnable(false);
					return;
				}
				Object obj = node.getUserObject();
				if (obj == null)
					return;
				if (obj instanceof ProcessElementObject) {
					updateCkbEnable(true);
					return;
				} else if (!(obj instanceof ProcessDocFolderInfo)) {
					updateCkbEnable(false);
					return;
				}
				updateCkbEnable(true);
				ProcessDocFolderInfo docInfo = (ProcessDocFolderInfo) obj;
				ProcessDocFolderAccessInfo info = (ProcessDocFolderAccessInfo) accessMap.get(docInfo);
				if (info == null) {
					isNew = true;
					info = new ProcessDocFolderAccessInfo();
					info.setFolder(docInfo);
					info.setProcess(process);
				} else {
					isNew = false;
				}
				updateCkbState(info);
				allCkb.setSelected(false);
			}

		});

		deleteCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setDeleteFlag(deleteCkb.isSelected() ? 1 : 0);
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (deleteCkb.isSelected())
						currentInfo.setDeleteFlag(1);
					else
						currentInfo.setDeleteFlag(0);

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}
				}
				tree.updateUI();
			}
		});

		viewCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setViewFlag(viewCkb.isSelected() ? 1 : 0);
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (viewCkb.isSelected())
						currentInfo.setViewFlag(1);
					else
						currentInfo.setViewFlag(0);

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}
				}
				tree.updateUI();
			}
		});

		operateCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setOperateFlag(operateCkb.isSelected() ? 1 : 0);
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (operateCkb.isSelected())
						currentInfo.setOperateFlag(1);
					else
						currentInfo.setOperateFlag(0);

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}
				}
				tree.updateUI();
			}
		});
		addCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setAddFolderFlag(addCkb.isSelected() ? 1 : 0);
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (addCkb.isSelected())
						currentInfo.setAddFolderFlag(1);
					else
						currentInfo.setAddFolderFlag(0);

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}
				}
				tree.updateUI();
			}
		});
		updateCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setUpdateFolderFlag(updateCkb.isSelected() ? 1 : 0);
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (updateCkb.isSelected())
						currentInfo.setUpdateFolderFlag(1);
					else
						currentInfo.setUpdateFolderFlag(0);

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}
				}
				tree.updateUI();
			}
		});
		allCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode node = getSelectedNode();
				if (node == null || node.getUserObject() == null)
					return;
				Object obj = node.getUserObject();
				if (obj instanceof ProcessElementObject) {
					//选中的是步骤节点，选择都将对第一层目录生效
					int folderCount = node.getChildCount();
					for (int i = 0; i < folderCount; i++) {
						ProcessDocFolderInfo folderInfo = (ProcessDocFolderInfo) ((DefaultMutableTreeNode) node.getChildAt(i))
								.getUserObject();

						ProcessDocFolderAccessInfo accessInfo = (ProcessDocFolderAccessInfo) accessMap.get(folderInfo);

						if (accessInfo == null) {
							//创建一个新的
							accessInfo = new ProcessDocFolderAccessInfo();
							accessInfo.setFolder(folderInfo);
							accessInfo.setProcess(process);

							accessMap.put(folderInfo, accessInfo);
						}
						accessInfo.setOperateFlag(allCkb.isSelected() ? 1 : 0);
						accessInfo.setViewFlag(allCkb.isSelected() ? 1 : 0);
						accessInfo.setDeleteFlag(allCkb.isSelected() ? 1 : 0);
						accessInfo.setAddFolderFlag(allCkb.isSelected() ? 1 : 0);
						accessInfo.setUpdateFolderFlag(allCkb.isSelected() ? 1 : 0);
						viewCkb.setSelected(allCkb.isSelected());
						deleteCkb.setSelected(allCkb.isSelected());
						operateCkb.setSelected(allCkb.isSelected());
						addCkb.setSelected(allCkb.isSelected());
						updateCkb.setSelected(allCkb.isSelected());
					}
				} else if (currentInfo == null) {
					return;
				} else {
					if (allCkb.isSelected()) {
						currentInfo.setDeleteFlag(1);
						currentInfo.setViewFlag(1);
						currentInfo.setOperateFlag(1);
						currentInfo.setAddFolderFlag(1);
						currentInfo.setUpdateFolderFlag(1);
					} else {
						currentInfo.setDeleteFlag(0);
						currentInfo.setViewFlag(0);
						currentInfo.setOperateFlag(0);
						currentInfo.setAddFolderFlag(0);
						currentInfo.setUpdateFolderFlag(0);
					}

					if (isNew) {
						accessMap.put(currentInfo.getFolder(), currentInfo);
						isNew = false;
					}

					updateCkbState(currentInfo);
				}
				tree.updateUI();
			}
		});

		this.updateCkbEnable(false);
	}

	/**
	 * 将权限设置写入到步骤中
	 *
	 */
	private void setToProcess() {
		List tmp = new ArrayList();
		tmp.addAll(accessMap.values());
		System.out.println(tmp.size());
		this.process.setDocFolderAccess(tmp);
	}

	private void updateCkbEnable(boolean enable) {
		allCkb.setEnabled(enable);
		deleteCkb.setEnabled(enable);
		viewCkb.setEnabled(enable);
		operateCkb.setEnabled(enable);
		addCkb.setEnabled(enable);
		updateCkb.setEnabled(enable);
	}

	private void updateCkbState(ProcessDocFolderAccessInfo info) {
		if (info == null) {
			deleteCkb.setSelected(false);
			viewCkb.setSelected(false);
			operateCkb.setSelected(false);
			addCkb.setSelected(false);
			updateCkb.setSelected(false);
		} else {
			deleteCkb.setSelected(info.getDeleteFlag() == 1);
			viewCkb.setSelected(info.getViewFlag() == 1);
			operateCkb.setSelected(info.getOperateFlag() == 1);
			addCkb.setSelected(info.getAddFolderFlag() == 1);
			updateCkb.setSelected(info.getUpdateFolderFlag() == 1);
		}
		this.currentInfo = info;
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

	/**
	 * 创建整个流程的目录树结构
	 * @return
	 */
	private DefaultMutableTreeNode getFlowDocFolderRoot() {
		FlowGraph graph = FlowGraphManager.getCurrentGraph();

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(graph.getGraphInfo().getName());

		Object[] cells = graph.getGraphLayoutCache().getCells(false, true, false, false);
		Arrays.sort(cells, new ProcessElementComparator());
		for (int i = 0; i < cells.length; i++) {
			if (!(cells[i] instanceof DefaultGraphCell))
				continue;

			Object obj = ((DefaultGraphCell) cells[i]).getUserObject();
			if (!(obj instanceof ProcessElementObject))
				continue;
			if (obj instanceof EndElement)
				continue;

			DefaultMutableTreeNode n = ((ProcessElementObject) obj).getDocTreeRoot();

			if (n == null)
				continue;

			//n.setUserObject((ProcessElementObject) obj);
			rootNode.add(n);
		}

		return rootNode;
	}

	class DocFolderAccessTreeCellRenderer extends DefaultTreeCellRenderer {

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			//			return new DocFolderAccessTreeCellEditor();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
			Object obj = node.getUserObject();
			if (obj instanceof ProcessDocFolderInfo) {
				ProcessDocFolderInfo dinfo = (ProcessDocFolderInfo) obj;
				ProcessDocFolderAccessInfo ainfo = (ProcessDocFolderAccessInfo) accessMap.get(dinfo);
				if (ainfo != null)
					return super.getTreeCellRendererComponent(tree, ainfo, sel, expanded, leaf, row, hasFocus);
			}
			return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		}

	}

	class ProcessElementComparator implements Comparator {

		public int compare(Object p1, Object p2) {
			if (!(p1 instanceof DefaultGraphCell) || !(p2 instanceof DefaultGraphCell)) {
				return 0;
			}
			p1 = ((DefaultGraphCell) p1).getUserObject();
			p2 = ((DefaultGraphCell) p2).getUserObject();
			if (!(p1 instanceof ProcessElementObject) || !(p2 instanceof ProcessElementObject)) {
				return 0;
			}
			/*
			System.out.println("比较结果："
					+ ((ProcessElementObject) p2).getName()
					+ " - "
					+ ((ProcessElementObject) p1).getName()
					+ " - "
					+ ((ProcessElementObject) p2).getName().compareTo(((ProcessElementObject) p1).getName()));
					*/
			return ((ProcessElementObject) p1).getName().compareTo(((ProcessElementObject) p2).getName());
		}

	}
}
