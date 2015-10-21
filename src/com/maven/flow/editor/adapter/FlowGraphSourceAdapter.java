/**
 * @(#)FlowGraphExporter.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import com.maven.flow.editor.ui.FlowGraph;

/**
 * 流程图形文件Adapter
 * 1、将图形保存成文件
 * 2、将文件还原成图形
 *
 * @author      kinz
 * @version     1.0 2007-5-26
 * @since       JDK1.4
 */

public interface FlowGraphSourceAdapter {

	/**
	 * 将图形保存到文件
	 * @param graph
	 * @param file
	 */
	void save(FlowGraph graph);
	
	/**
	 * 从文件中还原图形
	 * @param file
	 * @return
	 */
	FlowGraph load();
}
