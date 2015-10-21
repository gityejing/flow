package com.maven.flow.util;

/*
 * Created on 2005-3-25
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * @author Administrator
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Const {



	public static JFrame frame;

	private static Const _const = null;

	// 
	public static String server="http://192.168.0.12:8080/costagency/costagency/system/workflow/workFlowEditor.jsp?appId=160";
	public static String ClientFilePath = "c:/bocim/";

	// 标准信息
	public static final String Title = "超越即时通 (1.0) ";

	public static final String PubImagePath = "images/";

	public static final String NewLine = "#%"; // 用[#%]来表示要换行

	public static final char Link = '-';

	public static final double screenWidth = Toolkit.getDefaultToolkit()
			.getScreenSize().getWidth();

	public static final boolean Debug = false;

	//
	public static String User = "24401000234";

	public static String Pass = "1";

	public static String ServerIP = "127.0.0.1";

	//
	public static ArrayList Group;

	//
	public static HashMap OnlineFriend;

	public static ArrayList FriendList;

	public static ArrayList UserList;

	// ***************************测试人员信息******************************/

	// *************************** 数据库信息********************************/
	public static String URL; // dirver url

	public static String UserName; // db user

	public static String Password; // db password

	public static String LogFilePath = "c:/bocim/"; // 


	public static int Status = 0;

	private Const() {
	}

	public static Const getInstance() {
		if (_const == null) {
			_const = new Const();
		}
		return _const;
	}

	public static Font getFont() {
		int fontSize = screenWidth > 800 ? 12 : 12;
		return new Font("Dialog", Font.PLAIN, fontSize);
	}

	public static ImageIcon getIcon(String name) {
		return new ImageIcon(Const.class.getResource(PubImagePath + name));
	}

	public static String DateToString(String Fmt, long date) {
		SimpleDateFormat Tf = new SimpleDateFormat(Fmt);
		return Tf.format(new Date(date));
	}

	public static String DateToString(Date date) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat Tf = new SimpleDateFormat("yyyy.MM.dd");
			return Tf.format(date);
		}
	}

	public static Date StringToDate(String datestr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
		return StringToDate(format, datestr);
	}

	public static Date StringToDate(SimpleDateFormat format, String datestr) {
		Date datetime = null;
		try {
			datetime = format.parse(datestr);
		} catch (Exception e) {
			Log.RecLog(Log.ERROR, "类型转换出错！" + e);
		}
		return datetime;
	}

	public static String DateToString(String Fmt, Date date) {
		SimpleDateFormat Tf = new SimpleDateFormat(Fmt);
		return Tf.format(date);
	}

	public static void setParameter(String[] args) {
		if (args != null && args.length > 0) {
			// The URL of MySQL Driver
			if (!args[0].trim().startsWith("[")) {
				StringBuffer url = new StringBuffer();
				if (!args[0].trim().startsWith("jdbc:mysql://")) {
					url.append("jdbc:mysql://");
				}
				url.append(args[0].trim());
				URL = url.toString();
			}
			if (args.length > 2) {
				// DB UserName
				if (!args[1].trim().startsWith("[")) {
					UserName = args[1].trim();
				}
				// DB Password
				if (!args[2].trim().startsWith("[")) {
					Password = args[2].trim();
				}
			}
			if (args.length > 3) {
				// Log File Path
				if (!args[3].trim().startsWith("[")) {
					LogFilePath = args[3].trim();
				}
			}
		}
	}

	public static boolean SaveToFile(Object obj) {
		String FileName = "";
		try {
			File savefile = new File("");
			JFileChooser chooser = new JFileChooser(savefile);
			chooser.setDialogTitle("保存到文件");
			chooser.setSelectedFile(savefile);
			// chooser.setFileFilter(new FlwFilter(".flw"));
			int returnVal = chooser.showSaveDialog(Const.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				FileName = chooser.getCurrentDirectory() + "\\"
						+ chooser.getSelectedFile().getName();
				if (!FileName.endsWith(".flw")) {
					FileName = FileName + ".flw";
				}
				// 显示对话框
			} else {
				return false;
			}
		} catch (Exception E) {
			System.out.println(E.toString());
			return false;
		}
		return SaveToFile(FileName, obj);
	}

	public static boolean SaveToFile(String FileName, Object obj) {
		boolean result = false;
		try {
			FileOutputStream fos = new FileOutputStream(FileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			fos.close();
			result = true;
		} catch (Exception e) {
			Log.RecLog(Log.ERROR, "写文件出错:" + e);
		}
		return result;
	}

	/**
	 * 取文件名，不带路径
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtactFileName(String filename) {
		int pos = 0;
		for (int i = filename.length(); i > 0; i--) {

			String sep = filename.substring(i - 1, i);
			// System.out.println("sep=="+sep+" "+File.separator);
			if (sep.equals(File.separator)) {

				// System.out.println("filename.charAt(i)=="+filename.substring(i-1,i));
				pos = i;
				break;
			}
		}

		String file = filename.substring(pos, filename.length());
		// System.out.println("file=="+file);
		return file;
	}

	public static String OpenFile() {

		String FileName = "";
		try {
			File savefile = new File("");
			JFileChooser chooser = new JFileChooser(savefile);
			chooser.setDialogTitle("选择文件");
			chooser.setSelectedFile(savefile);
			// chooser.setFileFilter(new FlwFilter(".flw"));
			int returnVal = chooser.showOpenDialog(Const.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				FileName = chooser.getCurrentDirectory() + "\\"
						+ chooser.getSelectedFile().getName();
				// 显示对话框
			} else {
				return null;
			}
		} catch (Exception E) {
			System.out.println(E.toString());
			return null;
		}
		return FileName;
	}

	public static Object ReadFromFile() {
		String FileName = "";
		try {
			File savefile = new File("");
			JFileChooser chooser = new JFileChooser(savefile);
			chooser.setDialogTitle("从文件加载");
			chooser.setSelectedFile(savefile);
			// chooser.setFileFilter(new FlwFilter(".flw"));
			int returnVal = chooser.showOpenDialog(Const.frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				FileName = chooser.getCurrentDirectory() + "\\"
						+ chooser.getSelectedFile().getName();
				// 显示对话框
			} else {
				return null;
			}
		} catch (Exception E) {
			System.out.println(E.toString());
			return null;
		}
		return ReadFromFile(FileName);
	}

	public static Object ReadFromFile(String FileName) {
		Object obj = null;
		try {
			FileInputStream fis = new FileInputStream(FileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			Log.RecLog(Log.ERROR, "读文件出错:" + e);
		}
		return obj;
	}

	public static String getExtFile(String fileName) {
		String extFile = "file.gif";
		int pos = 0;
		for (int i = fileName.length(); i > 0; i--) {
			if (fileName.charAt(i - 1) == '.') {
				pos = i;
				break;
			}
		}
		System.out.println("pos  " + pos);
		if (pos > 0) {
			String extname = fileName.substring(pos, fileName.length());
			System.out.println("extname  " + extname);
			// word
			if (extname.equalsIgnoreCase("doc")) {
				extFile = "word.gif";

			}
			// excel

			// pdf
			if (extname.equalsIgnoreCase("pdf")) {
				extFile = "pdf.gif";

			}
			// rar
			if (extname.equalsIgnoreCase("rar")
					|| extname.equalsIgnoreCase("zip")) {
				extFile = "rar.gif";

			}
			// jpg
			if (extname.equalsIgnoreCase("jpg")
					|| extname.equalsIgnoreCase("jpeg")) {
				extFile = "jpg.gif";

			}
			// note

			// txt
			if (extname.equalsIgnoreCase("txt")) {
				extFile = "txt.gif";

			}
		}

		return extFile;
	}

	/*
	 * public static void WriteLog(UserInfo info, String msg) { String fileName =
	 * Const.LogFilePath + info.getUSER_ID() + ".msg"; File file = new
	 * File(fileName); try { FileWriter fos = new FileWriter(file, true);
	 * fos.append(msg); fos.flush(); fos.close(); } catch (IOException ex) {
	 * ex.printStackTrace(); } }
	 */

	public static String LocalIP() {
		String ip = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			String hostName = addr.getHostName().toString();

			// 获取本机ip
			InetAddress[] ipsAddr = InetAddress.getAllByName(hostName);
			String[] localServers = new String[ipsAddr.length];
			for (int i = 0; i < ipsAddr.length; i++) {
				if (ipsAddr[i] != null) {
					localServers[i] = ipsAddr[i].getHostAddress().toString();
					ip = localServers[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ip;
	}
}