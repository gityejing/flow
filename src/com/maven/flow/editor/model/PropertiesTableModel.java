/**
 * @(#)PropertiesTableModel.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.util.Arrays;
import java.util.Comparator;

import javax.swing.table.DefaultTableModel;

import com.maven.flow.editor.ui.GraphEditor;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class PropertiesTableModel extends DefaultTableModel {

	private FlowProperty[] properties = null;

	GraphEditor editor = null;

	FlowPropertyComparetor fpComparetor = new FlowPropertyComparetor();

	public PropertiesTableModel(GraphEditor editor) {
		this.editor = editor;
	}

	/**
	 * 使用一个属性数组构造一个表model
	 * @param properties
	 */
	public PropertiesTableModel(FlowProperty[] properties) {
		this.resetProperties(properties);
	}

	/**
	 * 重新设置属性
	 * @param properties
	 */
	public void resetProperties(FlowProperty[] properties) {
		this.properties = properties;
		if (this.properties != null)
			Arrays.sort(this.properties, this.fpComparetor);
		this.fireTableDataChanged();
	}

	/**
	 * 返回列数，指定2列
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * 返回表的行数
	 */
	public int getRowCount() {
		if (this.properties == null)
			return 0;
		return this.properties.length;
	}

	/**
	 * 获取某个单元格的值
	 */
	public Object getValueAt(int row, int column) {
		if (this.properties == null)
			return "";
		if (row < 0 || row >= this.properties.length)
			return "";

		if (column == 0) {
			return this.properties[row].getDescription();
		} else if (column == 1) {
			return this.properties[row].getValue();
		} else {
			return "";
		}
	}

	/**
	 * 判断一个单元格是否可以编辑，第二列的都可以编辑
	 */
	public boolean isCellEditable(int row, int column) {
		FlowProperty property = this.getProperty(row);
		if (property == null || !property.isEditAble()) {
			return false;
		} else {
			return column == 1;
		}
	}

	/**
	 * 更新属性的值
	 */
	public void setValueAt(Object aValue, int row, int column) {
		if (this.properties == null)
			return;
		if (row < 0 || row >= this.properties.length)
			return;
     
		this.properties[row].setValue(aValue);

		//更新表格
		this.fireTableDataChanged();

		//更新图形
		this.editor.updateView();
	}

	/**
	 * 返回列名称
	 */
	public String getColumnName(int column) {
		if (column == 0) {
			return "属性名";
		} else if (column == 1) {
			return "值";
		} else {
			return "";
		}
	}

	/**
	 * 获取指定行的属性
	 * @param row
	 * @return
	 */
	public FlowProperty getProperty(int row) {
		if (this.properties == null || row < 0 || row >= this.properties.length)
			return null;
		return this.properties[row];
	}

	class FlowPropertyComparetor implements Comparator {

		/* （非 Javadoc）
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object obj1, Object obj2) {
			if (!(obj1 instanceof FlowProperty)
					|| !(obj2 instanceof FlowProperty)) {
				return 0;
			}
			FlowProperty p1 = (FlowProperty) obj1;
			FlowProperty p2 = (FlowProperty) obj2;

			int gs = p1.getGroup() - p2.getGroup();

			if (gs == 0) {
				//两个相同的组
				return p1.getOrder() - p2.getOrder();
			} else
				return gs;
		}

	}
}
