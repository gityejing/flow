/**
 * @(#)UIUtil.java 2007-5-27
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.ui;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-27
 * @since       JDK1.4
 */

public class UIUtil {

	public static ImageIcon loadImageIcon(String rs) {
		URL u = UIUtil.class.getClassLoader().getResource(rs);
		return new ImageIcon(u);
	}

}
