/**
 * @(#)FlowGraphExporter.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import com.maven.flow.editor.ui.FlowGraph;

/**
 * ����ͼ���ļ�Adapter
 * 1����ͼ�α�����ļ�
 * 2�����ļ���ԭ��ͼ��
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public interface FlowGraphSourceAdapter {

	/**
	 * ��ͼ�α��浽�ļ�
	 * @param graph
	 * @param file
	 */
	void save(FlowGraph graph);
	
	/**
	 * ���ļ��л�ԭͼ��
	 * @param file
	 * @return
	 */
	FlowGraph load();
}
