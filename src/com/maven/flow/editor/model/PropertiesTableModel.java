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
	 * ʹ��һ���������鹹��һ����model
	 * @param properties
	 */
	public PropertiesTableModel(FlowProperty[] properties) {
		this.resetProperties(properties);
	}

	/**
	 * ������������
	 * @param properties
	 */
	public void resetProperties(FlowProperty[] properties) {
		this.properties = properties;
		if (this.properties != null)
			Arrays.sort(this.properties, this.fpComparetor);
		this.fireTableDataChanged();
	}

	/**
	 * ����������ָ��2��
	 */
	public int getColumnCount() {
		return 2;
	}

	/**
	 * ���ر������
	 */
	public int getRowCount() {
		if (this.properties == null)
			return 0;
		return this.properties.length;
	}

	/**
	 * ��ȡĳ����Ԫ���ֵ
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
	 * �ж�һ����Ԫ���Ƿ���Ա༭���ڶ��еĶ����Ա༭
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
	 * �������Ե�ֵ
	 */
	public void setValueAt(Object aValue, int row, int column) {
		if (this.properties == null)
			return;
		if (row < 0 || row >= this.properties.length)
			return;
     
		this.properties[row].setValue(aValue);

		//���±��
		this.fireTableDataChanged();

		//����ͼ��
		this.editor.updateView();
	}

	/**
	 * ����������
	 */
	public String getColumnName(int column) {
		if (column == 0) {
			return "������";
		} else if (column == 1) {
			return "ֵ";
		} else {
			return "";
		}
	}

	/**
	 * ��ȡָ���е�����
	 * @param row
	 * @return
	 */
	public FlowProperty getProperty(int row) {
		if (this.properties == null || row < 0 || row >= this.properties.length)
			return null;
		return this.properties[row];
	}

	class FlowPropertyComparetor implements Comparator {

		/* ���� Javadoc��
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
				//������ͬ����
				return p1.getOrder() - p2.getOrder();
			} else
				return gs;
		}

	}
}
