/**
 * @(#)FileAdapter.java 2007-5-26
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter.impl;

import java.awt.Color;
import java.awt.Container;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.maven.flow.editor.adapter.FlowGraphSourceAdapter;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.editor.ui.FlowGraphModel;

/**
 * 
 * 
 * @author kinz
 * @version 1.0 2007-5-26
 * @since JDK1.4
 */

public class FileAdapter implements FlowGraphSourceAdapter {

	private JFileChooser fileChooser = null;

	public void save(FlowGraph graph) {
		int returnValue = JFileChooser.CANCEL_OPTION;
		initFileChooser(false);
		returnValue = fileChooser.showSaveDialog(graph);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			ObjectOutputStream encoder = null;
			Container parent = graph.getParent();
			try {
				File f = fileChooser.getSelectedFile();
				if (f.getName().endsWith(".png")) {
					Color bg = graph.getBackground();
					BufferedImage img = graph.getImage(bg, 1);
					System.out.println(img);
					OutputStream out = new FileOutputStream(f);
					ImageIO.write(img, "png", out);
					out.flush();
					out.close();
				} else {
					FlowGraphInfo info = new FlowGraphInfo();
					info.getInfoFromGraph(graph);
					encoder = new ObjectOutputStream(new FileOutputStream(f));
					encoder.writeObject(info);
					encoder.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(graph, e.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public FlowGraph load() {
		int returnValue = JFileChooser.CANCEL_OPTION;
		initFileChooser(true);
		returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream decoder = new ObjectInputStream(
						new FileInputStream(fileChooser.getSelectedFile()));

				FlowGraph graph = new FlowGraph(new FlowGraphModel());

				FlowGraphInfo info = (FlowGraphInfo) decoder.readObject();

				info.setInfoToGraph(graph);

				return graph;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误",
						JOptionPane.ERROR_MESSAGE);
			}

		}
		return null;
	}

	protected void initFileChooser(final boolean isOpen) {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
		}
		FileFilter fileFilter = new FileFilter() {
			/**
			 * @see javax.swing.filechooser.FileFilter#accept(File)
			 */
			public boolean accept(File f) {
				if (f == null)
					return false;
				if (f.getName() == null)
					return false;
				if (f.getName().endsWith(".flow"))
					return true;
				if (!isOpen && f.getName().endsWith(".png")) {
					return true;
				}
				if (f.isDirectory())
					return true;

				return false;
			}

			/**
			 * @see javax.swing.filechooser.FileFilter#getDescription()
			 */
			public String getDescription() {
				if (!isOpen) {
					return "流程文件/图片 (.flow,.png)";
				} else {
					return "流程设计文件 ( .flow)";
				}
			}
		};
		fileChooser.setFileFilter(fileFilter);
	}

}
