/**
 * @(#)BroseFieldEditor.java 2007-6-7
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.maven.flow.editor.model.FlowProperty;

/**
 * 提供对LIST类型的FlowProperty进行编辑的功能
 *
 * @author      kinz
 * @version     1.0 2007-6-7
 * @since       JDK1.5
 */

public class ListPropertyEditor extends JTextField {

	private JButton browseBtn = new JButton("...");

	private JTextField txtField = new JTextField();

	private List selectedValues = null;

	private List allValues = null;

	protected FlowProperty property = null;

	public ListPropertyEditor(FlowProperty property) {

		this.property = property;

		this.setBorder(null);

		this.setMargin(new Insets(0, 0, 0, 0));
		this.setLayout(new BorderLayout());
		this.add(browseBtn, BorderLayout.EAST);
		this.add(txtField, BorderLayout.CENTER);

		txtField.setEditable(false);
		txtField.setBorder(null);
		browseBtn.setBorder(new LineBorder(Color.BLACK));

		this.setEditable(false);

		txtField.addFocusListener(new FocusAdapter() {

			public void focusGained(FocusEvent e) {
				txtField.selectAll();
			}

		});

		txtField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					setValue();
			}
		});

		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setValue();
			}
		});

		Object value = this.property.getValue();
		if (value != null && (value instanceof List)) {
			selectedValues = (List) value;
		}

		this.setTextFieldValue(selectedValues);

		this.allValues = new ArrayList();
		this.allValues.addAll(Arrays.asList(this.property.getListValues()));
	}

	private void setTextFieldValue(List value) {
		//		if (value == null)
		//			return;
		//		String tmp = null;
		//		for (Iterator it = selectedValues.iterator(); it.hasNext();) {
		//			if (tmp == null)
		//				tmp = String.valueOf(it.next());
		//			else
		//				tmp += "," + String.valueOf(it.next());
		//		}
		//		txtField.setText(tmp);

		if (value == null || value.size() == 0)
			txtField.setText("未定义");
		else
			txtField.setText(String.valueOf(value));
	}

	/**
	 * 通过弹出窗口设置列表值
	 *
	 */
	protected void setValue() {
		GetValueDlg dlg = new GetValueDlg("编辑-" + this.property.getDescription(), this.allValues, this.selectedValues);
		dlg.setVisible(true);

		//点击了取消按钮，不做更改
		if (dlg.isCanceled())
			return;

		this.selectedValues = dlg.getSelectedValues();

		this.property.setValue(selectedValues);

		this.setTextFieldValue(this.selectedValues);
	}

	private static class GetValueDlg extends JDialog {

		private JButton okBtn = new JButton("确定");

		private JButton cancelBtn = new JButton("取消");

		private List allValues = null;

		private List selectedValues = null;

		//private JList list = new JList();
		
		private List allCheckBoxs = new ArrayList();

		private boolean canceled = false;

		/**
		 * 构建一个选择值的对话框
		 * @param allValues 所有的值
		 * @param selectedValues 已经选中的值
		 */
		GetValueDlg(String title, List allValues, List selectedValues) {
			//设置标题
			this.setTitle(title);
			//设置为模态对话框
			this.setModal(true);
			this.setAlwaysOnTop(true);

			this.setSize(300, 300);
			this.setResizable(false);

			this.allValues = allValues;
			this.selectedValues = selectedValues;

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2, (int) (screenSize.getHeight() - this.getHeight()) / 2);

			this.init();
		}

		private void init() {
			allCheckBoxs.clear();
			
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());

			//c.add(new JLabel("按下 Ctrl 或者 Shift 键可进行多选"), BorderLayout.NORTH);
			//c.add(new JScrollPane(list), BorderLayout.CENTER);
			JPanel listPanel = new JPanel();
			c.add(new JScrollPane(listPanel), BorderLayout.CENTER);
			if (allValues.size() > 11) {
				listPanel.setLayout(new GridLayout(allValues.size()+1, 1));
			} else {
				listPanel.setLayout(new GridLayout(12, 1));
			}
			listPanel.setBackground(Color.white);
			for (Iterator it = allValues.iterator(); it.hasNext();) {
				Object obj = it.next();
				JCheckBox ck = new JCheckBox(String.valueOf(obj));
				ck.setSelected(false);
				for (Iterator it1 = selectedValues.iterator(); it1.hasNext();) {
					Object obj1 = (Object) it1.next();
					if (obj.equals(obj1)) {
						ck.setSelected(true);
						break;
					}
				}
				ck.setBackground(Color.white);
				listPanel.add(ck);
				
				allCheckBoxs.add(ck);
			}

			JPanel bp = new JPanel();
			bp.setLayout(new FlowLayout(FlowLayout.CENTER));
			bp.add(okBtn);
			bp.add(cancelBtn);
			c.add(bp, BorderLayout.SOUTH);
			
			/*
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.setCellRenderer(new PropertyListValueRenderer());

			list.setListData(allValues.toArray());
			list.setFixedCellHeight(15);

			//设置选中的Item
			int[] selectedIndeces = new int[selectedValues.size()];
			int tmp = 0;
			for (Iterator it = selectedValues.iterator(); it.hasNext();) {
				Object obj = it.next();
				for (int i = 0; i < allValues.size(); i++) {
					if (obj.equals(allValues.get(i))) {
						selectedIndeces[tmp] = i;
						break;
					}
				}
				tmp++;
			}

			list.setSelectedIndices(selectedIndeces);
			*/

			cancelBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					canceled = true;
					GetValueDlg.this.dispose();
				}
			});

			okBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					canceled = false;
					GetValueDlg.this.dispose();
				}
			});

			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent event) {
					canceled = true;
				}
			});
		}

		/**
		 * 返回是否点击了取消按钮
		 * @return
		 */
		public boolean isCanceled() {
			return canceled;
		}

		/**
		 * 获取选中的值
		 * @return
		 */
		public List getSelectedValues() {
			/*
			Object[] objs = list.getSelectedValues();
			List l = new ArrayList();
			if (objs != null)
				l.addAll(Arrays.asList(objs));
			return l;
			*/
			List selectedValues = new ArrayList();
			for(int i=0;i<allCheckBoxs.size();i++){
				JCheckBox ck = (JCheckBox) allCheckBoxs.get(i);
				if(ck.isSelected()){
					selectedValues.add(allValues.get(i));
				}
			}
			
			return selectedValues;
		}
	}

	private static class PropertyListValueRenderer extends JCheckBox implements ListCellRenderer {

		public Component getListCellRendererComponent(final JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			this.setEnabled(true);
			this.setText(String.valueOf(value));

			this.setSelected(isSelected);

			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					System.out.println(event);
				}
			});

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			setFont(list.getFont());
			setOpaque(true);
			this.setMaximumSize(new Dimension(10000, 5));
			return this;
		}

		//		FlowElementObject fe = (FlowElementObject) value;
		//		setText(fe.getName());
		//		if (fe.getIconResource() != null)
		//			setIcon(UIUtil.loadImageIcon(fe.getIconResource()));
		//		if (isSelected) {
		//			setBackground(list.getSelectionBackground());
		//			setForeground(list.getSelectionForeground());
		//		} else {
		//			setBackground(list.getBackground());
		//			setForeground(list.getForeground());
		//		}
		//		setEnabled(list.isEnabled());
		//		setFont(list.getFont());
		//		setOpaque(true);
		//		return this;
		//	}
	}
}
