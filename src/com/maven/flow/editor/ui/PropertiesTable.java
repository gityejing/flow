/**
 * @(#)PropertiesTable.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import com.maven.flow.editor.model.FlowProperty;
import com.maven.flow.editor.model.PropertiesTableModel;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class PropertiesTable extends JTable implements  MouseListener{

	private PropertiesTableModel model = null;

	private TableCellRenderer requiredPropertyRenderer = new RequiredPropertyCellRender();
	
	private JTextField bigField;
	public PropertiesTable(PropertiesTableModel model) {

		super(model);

		this.model = model;
 		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//设置只能选择一行

	}

	public TableCellRenderer getCellRenderer(int row, int column) {
		if (column != 0)
			return super.getCellRenderer(row, column);
		FlowProperty p = model.getProperty(row);
		if (p == null || !p.isRequired())
			return super.getCellRenderer(row, column);

		return new RequiredPropertyCellRender();
	}

	public TableCellEditor getCellEditor(final int row, final int column) {
		TableCellEditor editor = null;
		if (row < 0 || row >= model.getRowCount() || model.getProperty(row) == null) {
			editor = super.getCellEditor(row, column);
		} else {
			FlowProperty p = model.getProperty(row);
			if (column == 1) {
				switch (p.getType()) {
					case FlowProperty.BOOLEAN:
						JComboBox bx = new JComboBox(new Object[] { new Boolean(true), new Boolean(false) });
						bx.setSelectedItem(p.getValue());
						bx.setBorder(null);
						editor = new DefaultCellEditor(bx);
						break;
					case FlowProperty.OPTION:
						Object[] objs = p.getListValues();
						JComboBox bx1 = new JComboBox(objs);
						for (int i = 0; i < objs.length; i++) {
							if(objs[i].equals(p.getValue())){
								bx1.setSelectedIndex(i);
								break;
							}
						}
						/*
						bx1.setSelectedItem(p.getValue());
						System.out.println(p.getValue());
						*/
						bx1.setBorder(null);
						editor = new DefaultCellEditor(bx1);
						break;
					case FlowProperty.LIST:
						editor = new DefaultCellEditor(new ListPropertyEditor(p));
						break;
					case FlowProperty.CUSTOMIZE:
						editor = new DefaultCellEditor(new CustomizePropertyEditor(p));
						break;
					
					case FlowProperty.BIGSTRING:
						bigField = new JTextField(String.valueOf(p.getValue()));
						bigField.setBorder(null);
						bigField.addFocusListener(new FocusAdapter() {
							public void focusGained(FocusEvent e) {
								bigField.selectAll();
							}
						});
						bigField.addKeyListener(new KeyAdapter() {

							public void keyTyped(KeyEvent e) {
//								System.out.println("当前编辑框的值："+tf.getText());
//								System.out.println("当前键："+e.getKeyChar());
//								System.out.println("键代码："+e.getKeyCode());
							}
							
							
							
							
							public void keyReleased(KeyEvent e) {
//								System.out.println("当前编辑框的值："+tf.getText());
//								System.out.println("当前键："+e.getKeyChar());
//								System.out.println("键代码："+e.getKeyCode());
								PropertiesTable.this.getModel().setValueAt(bigField.getText(), row, column);
							}
							
						});
						bigField.addMouseListener(this);
						
						
						editor = new DefaultCellEditor(bigField);
						break;
						
					case FlowProperty.STRING:
					case FlowProperty.INT:
					case FlowProperty.FLOAT:
						final JTextField tf = new JTextField(String.valueOf(p.getValue()));
						tf.setBorder(null);
						tf.addFocusListener(new FocusAdapter() {
							public void focusGained(FocusEvent e) {
								tf.selectAll();
							}
						});
						tf.addKeyListener(new KeyAdapter() {

							public void keyTyped(KeyEvent e) {
//								System.out.println("当前编辑框的值："+tf.getText());
//								System.out.println("当前键："+e.getKeyChar());
//								System.out.println("键代码："+e.getKeyCode());
							}

							public void keyReleased(KeyEvent e) {
//								System.out.println("当前编辑框的值："+tf.getText());
//								System.out.println("当前键："+e.getKeyChar());
//								System.out.println("键代码："+e.getKeyCode());
								PropertiesTable.this.getModel().setValueAt(tf.getText(), row, column);
							}
							
						});
						editor = new DefaultCellEditor(tf);
						break;
					default:
						editor = super.getCellEditor(row, column);
				}
			} else {
				editor = super.getCellEditor(row, column);
			}
		}
		if (editor != null && (editor instanceof DefaultCellEditor)) {
			((DefaultCellEditor) editor).setClickCountToStart(1);
		}
		return editor;
	}

	class RequiredPropertyCellRender extends JLabel implements TableCellRenderer {
		protected Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			this.setText(String.valueOf(value));

			//Font t = table.getFont();

			//Font f = new Font(t.getName(), Font., t.getSize());
           //t.
			//setFont(t);
            this.setForeground(Color.red);
			if (hasFocus) {
				Border border = null;
				if (isSelected) {
					border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
				}
				if (border == null) {
					border = UIManager.getBorder("Table.focusCellHighlightBorder");
				}
				setBorder(border);

				if (!isSelected && table.isCellEditable(row, column)) {
					Color col;
					col = UIManager.getColor("Table.focusCellForeground");
					if (col != null) {
						super.setForeground(col);
					}
					col = UIManager.getColor("Table.focusCellBackground");
					if (col != null) {
						super.setBackground(col);
					}
				}
			} else {
				setBorder(noFocusBorder);
			}

			return this;
		}

	}

	public void mouseClicked(MouseEvent arg0) {
		if(true){
			return;
		}
		// TODO Auto-generated method stub
		String express=bigField.getText();
		System.out.println("表达式是："+express);
		JFrame newf=new JFrame("条件判断表达式");//
		
		Container contentPane=newf.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		final JTextArea t1=new JTextArea(5,25);
		t1.setText(express);
		
		t1.setTabSize(10);
		t1.setLineWrap(false);//激活自动换行功能
		t1.setWrapStyleWord(true);//激活断行不断字功能
		      
		//p1.add(new JScrollPane(t1));//将JTextArea放入JScrollPane中，这样就能利用滚动的效果看到输入超过JTextArea高度的
		                                  //文字.

		JButton b1=new JButton("确定");
		//b1.setBorder(BorderFactory.createLineBorder(Color.blue,10));//createLineBorder方法指定边界的颜色与宽度.
		b1.setPreferredSize(new Dimension(3,5));
		
		b1.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("ddd");
				bigField.setText(t1.getText());
				
			}
        });
		
		contentPane.add(b1,BorderLayout.SOUTH);
		contentPane.add(new JScrollPane(t1),BorderLayout.CENTER);
		
		newf.setBounds(300, 300, 500, 500);
	    //newf.setSize(500,500);
	    newf.show();
	    
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
