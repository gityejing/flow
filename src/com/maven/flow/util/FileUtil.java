/**
 * ��������	2006-6-2
 * ����			�Ž���
 * �ļ���		WebContextFileSystem.java
 * ��Ȩ			CopyRight (c) 2006
 * ����˵��	�ṩ����WebContext/WEB-INF/filesĿ¼�µ��ļ��洢��������
 */
package com.maven.flow.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * ContextPath�ڱ����ļ�ϵͳ�е�ʵ��·�����ò�����ApplicationInitializer�ڳ�ʼ��ʱ���� �ò���ָ����ϵͳ�ļ������
	 * ContextPath/WEB-INF/filesĿ¼��
	 */
	private static String fileRoot = null;

	private static ResourceConfig config = ResourceConfig
			.getConfig("costagency.properties");

	static {
		// �������ļ��л�ȡϵͳ����ļ��ĸ�Ŀ¼
		fileRoot = config.getStringConfig("server_file_root",
				"C:/costagency/files");
	}

	public static String getFileRoot() {
		return fileRoot;
	}

	/**
	 * �����ݱ��浽�ļ��У����ָ����Ŀ¼�����ڣ����Զ�����Ŀ¼���ļ���ʹ�õ�ǰʱ�䱣֤Ψһ�ԡ��ļ�����ʹ�ú�׺����ȡ�ļ�ʱ��Ҫ����ļ�������
	 * 
	 * @param data
	 *            ���ݣ��������null���򴴽�һ���հ׵��ļ�
	 * @param dir
	 *            Ҫ�����ļ���Ŀ¼��Ŀ¼����WebContext/WEB-INF/files
	 * @return �������ļ�·���������ļ��к��ļ�����
	 * @param suffix
	 *            �ļ��ĺ�׺�������û�д����׺������ʹ�ú�׺��
	 * @throws Exception
	 */
	public static String saveToFile(byte[] data, String dir, String suffix)
			throws Exception {
		return saveToFile(data, dir, suffix, null);
	}

	/**
	 * 
	 * �����ݱ��浽�ļ��У����ָ����Ŀ¼�����ڣ����Զ�����Ŀ¼���ļ���ʹ�õ�ǰʱ�䱣֤Ψһ�ԡ��ļ�����ʹ�ú�׺����ȡ�ļ�ʱ��Ҫ����ļ�������
	 * 
	 * @param data
	 *            ���ݣ��������null���򴴽�һ���հ׵��ļ�
	 * @param dir
	 *            Ҫ�����ļ���Ŀ¼��Ŀ¼����WebContext/WEB-INF/files
	 * @return �������ļ�·���������ļ��к��ļ�����
	 * @param suffix
	 *            �ļ��ĺ�׺�������û�д����׺������ʹ�ú�׺��
	 * @param fileName
	 *            �ļ�����
	 * @return
	 * @throws Exceptioni
	 */
	public static String saveToFile(byte[] data, String dir, String suffix,
			String fileName) throws Exception {
		if (fileRoot == null) {
			log.error("�ļ���Ŀ¼û������");
			throw new Exception("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}

		long currentTime = System.currentTimeMillis();
		String newFileName = String.valueOf(currentTime);

		if (suffix != null && suffix.trim().length() > 0) {
			if (suffix.startsWith(".")) {
				newFileName += suffix;
			} else {
				newFileName += "." + suffix;
			}
		}

		// д���ļ�
		updateFile(data, dir, newFileName, fileName);

		// �����ļ���
		return dir + "/" + newFileName;
	}

	public static void downloadFile(String dir, String filename,
			HttpServletResponse response) throws Exception {

		String filePath = dir + "/" + filename;
		boolean b = FileUtil.checkFileExist(filename);
		if (!b) {
			throw new Exception("�����ڸ��ļ���");
		}
		File file = new File(filePath);
		InputStream is = new FileInputStream(file);
		OutputStream os = response.getOutputStream(); // get the
		// outputstream
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(os);
		filename = java.net.URLEncoder.encode(filename, "UTF-8");
		// encoding for Chinese
		filename = new String(filename.getBytes("UTF-8"), "gb2312");
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);
		int bytesRead = 0;
		byte[] buffer = new byte[1024 * 8];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead); // buffer read
		}
		bos.flush();
		bis.close();
		bos.close();
		is.close();
		os.close();
	}

	/**
	 * ���Ϊһ���µ��ļ�,�������ļ�����
	 * 
	 * @param dir
	 *            String
	 * @param suffix
	 *            String
	 * @param fileName
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String saveAsToFile(String dir, String suffix, String fileName)
			throws Exception {
		if (fileRoot == null) {
			log.error("�ļ���Ŀ¼û������");
			throw new Exception("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}

		long currentTime = System.currentTimeMillis();
		String newFileName = String.valueOf(currentTime);

		if (suffix != null && suffix.trim().length() > 0) {
			if (suffix.startsWith(".")) {
				newFileName += suffix;
			} else {
				newFileName += "." + suffix;
			}
		}
		byte[] data = loadFromFile(fileName);
		// д���ļ�
		updateFile(data, dir, newFileName, fileName);
		// �����ļ���
		return dir + "/" + newFileName;
	}

	/**
	 * ���Ϊһ���µ��ļ�,�������ļ�����
	 * 
	 * @param dir
	 *            String
	 * @param suffix
	 *            String
	 * @param fileName
	 *            String
	 * @return String
	 * @throws Exception
	 */
	public static String saveAsToFile(String dir, String suffix,
			String fileName, String descFileName) throws Exception {
		if (fileRoot == null) {
			log.error("�ļ���Ŀ¼û������");
			throw new Exception("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}
		// long currentTime = System.currentTimeMillis();
		String newFileName = descFileName;

		if (suffix != null && suffix.trim().length() > 0) {
			newFileName = newFileName.replaceAll("\\" + suffix, "");
			if (suffix.startsWith(".")) {
				newFileName += suffix;
			} else {
				newFileName += "." + suffix;
			}
		}
		byte[] data = loadFromFile(fileName);
		// д���ļ�
		updateFile(data, dir, newFileName, fileName);
		// �����ļ���
		return dir + "/" + newFileName;
	}

	/**
	 * �����ļ����ݣ�����ļ������ڣ��򴴽��µ��ļ�
	 * 
	 * @param data
	 * @param dir
	 * @param fileName
	 * @param decName
	 * @throws Exception
	 */
	public static void updateFile(byte[] data, String dir, String fileName,
			String decName) throws Exception {

		// ���Ŀ¼�����ڣ�������Ŀ¼
		File dirs = new File(fileRoot + "/" + dir);
		if (!dirs.exists() || !dirs.isDirectory()) {
			if (!dirs.mkdirs()) {
				throw new Exception("�޷������ļ�Ŀ¼��" + dirs.getAbsolutePath());
			}
		}
		// ���û�����ݣ��򴴽�һ���յ��ļ�
		if (data == null) {
			data = new byte[0];
		}

		// д�ļ�

		File file = new File(dirs, fileName.replaceFirst(fileRoot, ""));
		// ����ļ��Ѿ����ڣ�����ɾ���ļ�
		if (file.exists()) {
			file.delete();
		} else {
			if (!file.createNewFile()) {
				throw new Exception("�޷������ļ���" + file.getName());
			}
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(data);
		out.close();

		// ��¼�ļ�������Ϣ
		if (isGeneratedFile(fileName) && decName != null
				&& decName.trim().length() > 0) {
			logFileInfo(getGeneratedTime(fileName), decName);
		}
	}

	/**
	 * ���ļ�ϵͳ�ж�ȡ�ļ������ֽ�����ķ�ʽ����
	 * 
	 * @param path
	 *            �ļ���·��
	 * @return �ļ����ֽ�����
	 * @throws Exception
	 *             �ļ������ڻ��߷���IO����
	 */
	public static byte[] loadFromFile(String path) throws Exception {
		if (fileRoot == null) {
			log.error("�ļ���Ŀ¼û������");
			throw new Exception("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("�ļ�·��Ϊ��");
			throw new Exception("�޷����ļ� '" + path + "' �ж�ȡ����");
		}

		File file = new File(fileRoot, path.replaceAll(fileRoot, ""));
		if (!file.exists() || !file.isFile()) {
			log.error("�ļ� [" + path + "] ������");
			throw new Exception("�޷����ļ� '" + path + "' �ж�ȡ���ݣ��ļ�������");
		}

		if (!file.canRead()) {
			log.error("�ļ����ܶ�ȡ");
			throw new Exception("�޷����ļ� '" + path + "' �ж�ȡ���ݣ��ļ����ܶ�");
		}

		// ���ļ�����װ���ļ�
		FileInputStream in = new FileInputStream(file);
		byte[] datas = new byte[(int) file.length()];
		in.read(datas);
		// �ر��ļ���
		in.close();

		return datas;
	}

	public static File loadFile(String path) throws Exception {
		if (fileRoot == null) {
			log.error("�ļ���Ŀ¼û������");
			throw new Exception("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("�ļ�·��Ϊ��");
			throw new Exception("�޷����ļ� '" + path + "' �ж�ȡ����");
		}

		File file = new File(fileRoot, path.replaceAll(fileRoot, ""));
		if (!file.exists() || !file.isFile()) {
			log.error("�ļ� [" + path + "] ������");
			throw new Exception("�޷����ļ� '" + path + "' �ж�ȡ���ݣ��ļ�������");
		}

		return file;
	}

	/**
	 * ɾ��ָ��·�����ļ�
	 * 
	 * @param path
	 *            Ҫɾ�����ļ�·��
	 * @return ɾ���ɹ�����true��ʧ�ܷ���false
	 * @throws Exception
	 *             ɾ�����̳��ִ���
	 */
	public static boolean deleteFile(String path) {
		if (fileRoot == null) {
			log.info("�ļ�������·��û��ָ��������������ϵͳ�����������Ȼ���ڣ������������");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("�ļ�·��Ϊ��");
			return true;
		}

		/*
		 * //�������ɵ��ļ���ɾ�� if (!isGeneratedFile(path)) { return true; }
		 */

		File file = new File(fileRoot, path);
		if (!file.exists() || !file.isFile()) {
			log.error("�ļ� [" + path + "] ������");
			return true;
		}

		return file.delete();
	}

	/**
	 * ����WebContextPath�ڱ����ļ�ϵͳ��·��
	 * 
	 * @param root
	 *            WebContextPath�ڱ����ļ�ϵͳ��·��
	 */
	/*
	 * public static void setLocalRoot(String root) { fileRoot = root + "/" +
	 * "WEB-INF" + "/" + "files"; }
	 */

	/**
	 * ��ȡָ��Ŀ¼�µ��ļ��б������ļ�����
	 * 
	 * @param dir
	 *            ָ����Ŀ¼����Ŀ¼�������ContextPath��WEB-INF/filesĿ¼��
	 * @param exts
	 *            Ҫ�г����ļ�����չ�����飬��չ�����ڸ������еĲ����г�����ָ����չ���봫��null���߳���Ϊ0������
	 * @return dirĿ¼�·���ָ����չ�����ļ������б�
	 */
	public static String[] listFiles(String dir, final String[] exts) {
		File dirf = new File(fileRoot, dir);
		if (exts != null && exts.length > 0) {
			return dirf.list(new FilenameFilter() {
				public boolean accept(File f, String name) {
					for (int i = 0; i < exts.length; i++) {
						if (name.toLowerCase().endsWith(exts[i].toLowerCase())) {
							return true;
						}
					}
					return false;
				}
			});
		} else {
			return dirf.list();
		}
	}

	/**
	 * ��ȡ�ļ��ĺ�׺��
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		if (fileName == null) {
			return "";
		}
		int index = fileName.lastIndexOf(".");
		if (index == -1) {
			return "";
		}
		return fileName.substring(index);
	}

	/**
	 * ��ȡԭʼ���ļ�����
	 * 
	 * @param newFileName
	 *            �µ��ļ������ļ�������ʹ��saveToFile���أ����򽫷���newFileName
	 * @return ���û����ص���Ϣ���򷵻�newFileName
	 */
	public static String getOriginalFileName(String newFileName) {
		if (newFileName == null || newFileName.trim().length() == 0) {
			return newFileName;
		}
		// �����ļ����ƣ�ȡ���һ����
		int index = -1;
		// �����з�б�ܻ�����б��
		newFileName = newFileName.replaceAll("\\\\", "/");
		index = newFileName.lastIndexOf("/");
		if (index > 0) {
			newFileName = newFileName.substring(index + 1);
		}
		// ȥ����׺��
		index = newFileName.lastIndexOf(".");
		if (index > 0) {
			newFileName = newFileName.substring(0, index);
		}
		long time = -1;
		try {
			time = Long.parseLong(newFileName);
		} catch (Exception ex) {
			log.error("�ļ����Ʋ��Ϸ����޷���ȡ�����Ϣ", ex);
			return newFileName;
		}
		String oldFileName = getFileInfo(time);
		if (oldFileName == null) {
			return newFileName;
		} else {
			return oldFileName;
		}
	}

	/**
	 * ���ݴ����ʱ���ȡ�ļ���Ϣ�ļ�������ļ������ڣ��򴴽�һ�����ļ�ÿ�´���һ��
	 * 
	 * @param time
	 * @return
	 */
	private static File getInfoFile(long time) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		String fileName = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)
				+ ".info";
		// ��Ϣ�ļ������ system/fileinfoĿ¼��
		File dir = new File(getFileRoot() + "/system/fileinfo");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		File f = new File(dir, fileName);
		// �ļ��������򴴽�һ���µ��ļ�
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	/**
	 * ��¼�ļ�������Ϣ
	 * 
	 * @param time
	 * @param fileName
	 */
	private static void logFileInfo(long time, String fileName) {
		try {
			// ��ȡ�����ļ�����
			if (fileName == null || fileName.trim().length() == 0) {
				return;
			}
			fileName = fileName.replaceAll("\\\\", "/");
			int index = fileName.lastIndexOf("/");
			if (index > 0) {
				fileName = fileName.substring(index + 1);
			}
			File f = getInfoFile(time);

			Properties p = new Properties();
			p.load(new FileInputStream(f));
			p.setProperty("" + time, ByteUtil.objectBase64Encode(fileName));
			p.store(new FileOutputStream(f), "File Info");

			// RandomAccessFile raf = new RandomAccessFile(f, "rw");
			// raf.skipBytes((int) f.length());
			// raf
			// .write((time + "=" + ByteUtil.objectBase64Encode(fileName) +
			// "\r\n")
			// .getBytes());
			// raf.close();
		} catch (Exception ex) {
			log.error("��¼�ļ���Ϣʧ��", ex);
		}
	}

	/**
	 * ��ȡ�ļ�������Ϣ
	 * 
	 * @param time
	 * @return
	 */
	private static String getFileInfo(long time) {
		try {
			File f = getInfoFile(time);
			FileInputStream in = new FileInputStream(f);
			Properties p = new Properties();
			p.load(in);
			in.close();
			String fileName = p.getProperty(String.valueOf(time));
			fileName = (String) ByteUtil.objectBase64Decode(fileName);
			p.clear();
			return fileName;
		} catch (Exception ex) {
			log.error("��ȡ�ļ���Ϣʧ��", ex);
			return String.valueOf(time);
		}
	}

	/**
	 * �ж��ļ��Ƿ��Զ����ɵ�
	 */
	private static boolean isGeneratedFile(String filePath) {
		if (filePath == null) {
			return false;
		}
		filePath = filePath.replaceAll("\\\\", "/");
		int index = filePath.lastIndexOf("/");
		if (index != -1) {
			filePath = filePath.substring(index + 1);
		}
		index = filePath.lastIndexOf(".");
		if (index != -1) {
			filePath = filePath.substring(0, index);
		}
		try {
			Long.parseLong(filePath);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * ��ȡ�ļ�����ʱ��
	 * 
	 * @param filePath
	 * @return
	 */
	private static long getGeneratedTime(String filePath) {
		if (filePath == null) {
			return -1;
		}
		filePath = filePath.replaceAll("\\\\", "/");
		int index = filePath.lastIndexOf("/");
		if (index != -1) {
			filePath = filePath.substring(index + 1);
		}
		index = filePath.lastIndexOf(".");
		if (index != -1) {
			filePath = filePath.substring(0, index);
		}
		return Long.parseLong(filePath);
	}

	/**
	 * ��һ���ַ���ת����һ��Unicode�ַ���
	 * 
	 * @param strText
	 * @return
	 * @throws Exception
	 */
	private static String toUnicode(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;

		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				strHex = Integer.toHexString(intAsc);
				strRet = strRet + "\\u" + strHex;
			} else {
				strRet = strRet + c;
			}
		}
		return strRet;
	}

	/**
	 * ���ָ�����ļ��Ƿ����
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static boolean checkFileExist(String filePath) throws Exception {
		if (filePath.startsWith("//")) {
			System.out.println("start with..................// ");
			filePath = filePath.substring(1, filePath.length());
		}
		File f = new File(fileRoot + "/" + filePath);
		return f.isFile() && f.exists();
	}

	/**
	 * ����·�����ƻ�ȡ�ļ�����
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String getFileName(String filePath) throws Exception {
		int idx = filePath.lastIndexOf("/");
		if (idx != -1) {
			return filePath.substring(idx + 1);
		} else {
			idx = filePath.lastIndexOf("\\");
			if (idx != -1) {
				return filePath.substring(idx + 1);
			} else {
				return filePath;
			}
		}
	}

	public static void main(String[] args) {
		String path = "member/picture/noaudit.jpg";
		String path1 = "member/picture/11254587.jpg";

		System.out.println(isGeneratedFile(path));
		System.out.println(isGeneratedFile(path1));
	}
}
