/*
 * Created on 2005-3-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.maven.flow.editor.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.maven.flow.editor.SystemGloable;

/**
 * @author Administrator To change the template for this generated type comment
 *         go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 *         Comments
 */
public class Dialog extends JDialog implements WindowListener {
	// public final ImageIcon topline = new ImageIcon(
	// Const.class.getResource(Const.PubImagePath + "qq/12.png"));
	public Dialog() {
		super(SystemGloable.frame);
		this.setModal(true);
		// this.setIconImage(topline.getImage());
	}

	public Dialog(JFrame frame) {
		super(frame);
		this.setModal(true);
		// this.setIconImage(topline.getImage());
	}

	public void CenterComponent() {
		setResizable(false);
		Dimension dlgSize = this.getSize();
		Dimension frmSize = this.getToolkit().getScreenSize();
		// Point loc = getLocation();
		setLocation((frmSize.width - dlgSize.width) / 2,
				(frmSize.height - dlgSize.height) / 2);
	}

	public static void main(String[] args) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		onClose();
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	// ***********************************************************/
	public void onClose() {
		this.dispose();
	}

	class HelpWindows extends Dialog {
		public HelpWindows() {
			super();
		}
	}
}
