package com.maven.flow.util;

/*
 * Created on 2005-3-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Log {
	public static final int DEBUG = 0;

	public static final int MESSAGE = 1;

	public static final int WARRING = 2;

	public static final int ERROR = 3;

	/**
	 * 
	 */
	public static void RecLog(int debug, Object object) {
		if (debug == DEBUG) {
			System.out.println(object.toString());
		}
		if (debug == MESSAGE) {
			ShowMsg(Const.frame, object.toString());
			// System.out.println(object.toString());
		}
		if (debug == WARRING) {
			ShowMsg(Const.frame, object.toString());
			// System.out.println(object.toString());
		}
		if (debug == ERROR) {
			ShowMsg(Const.frame, object.toString());
			// System.out.println(object.toString());
		}
	}

	private Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void ExitSystem() {
		if (ShowYNMsg(Const.frame, "退出系统", "确实要退出系统吗?")) {
			System.exit(0);
		}
	}

	public static void ShowMsg(Component AComponent, String S) {
		JOptionPane.showMessageDialog(AComponent, S);
	}

	public static String ImputMsg(Component AComponent, String message,
			String defaultvalue) {
		return JOptionPane.showInputDialog(AComponent, message, defaultvalue);
	}

	/**
	 * 默认否
	 * 
	 * @param AComponent
	 * @param Title
	 * @param S
	 * @return
	 */
	public static boolean ShowYNMsg(Component AComponent, String Title, String S) {
		return JOptionPane.showConfirmDialog(AComponent, S, Title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION;
	}

	/**
	 * 用户指定默认按键
	 * 
	 * @param AComponent
	 * @param Title
	 * @param S
	 * @param Default
	 * @return
	 */
	public static boolean ShowYNMsg(Component AComponent, String Title,
			String S, int Default) {
		return JOptionPane.showConfirmDialog(AComponent, S, Title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null) == JOptionPane.YES_OPTION;
	}

	public static void main(String[] args) {
	}
}
