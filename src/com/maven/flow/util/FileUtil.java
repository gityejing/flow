/**
 * 创建日期	2006-6-2
 * 作者			张金凌
 * 文件名		WebContextFileSystem.java
 * 版权			CopyRight (c) 2006
 * 功能说明	提供基于WebContext/WEB-INF/files目录下的文件存储操作功能
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
	 * ContextPath在本地文件系统中的实际路径，该参数由ApplicationInitializer在初始化时设置 该参数指定了系统文件存放于
	 * ContextPath/WEB-INF/files目录下
	 */
	private static String fileRoot = null;

	private static ResourceConfig config = ResourceConfig
			.getConfig("costagency.properties");

	static {
		// 从配置文件中获取系统相关文件的根目录
		fileRoot = config.getStringConfig("server_file_root",
				"C:/costagency/files");
	}

	public static String getFileRoot() {
		return fileRoot;
	}

	/**
	 * 将数据保存到文件中，如果指定的目录不存在，会自动创建目录。文件名使用当前时间保证唯一性。文件名不使用后缀，读取文件时需要清楚文件的类型
	 * 
	 * @param data
	 *            数据，如果传入null，则创建一个空白的文件
	 * @param dir
	 *            要保存文件的目录，目录基于WebContext/WEB-INF/files
	 * @return 保存后的文件路径，包括文件夹和文件名称
	 * @param suffix
	 *            文件的后缀名，如果没有传入后缀名，则不使用后缀名
	 * @throws Exception
	 */
	public static String saveToFile(byte[] data, String dir, String suffix)
			throws Exception {
		return saveToFile(data, dir, suffix, null);
	}

	/**
	 * 
	 * 将数据保存到文件中，如果指定的目录不存在，会自动创建目录。文件名使用当前时间保证唯一性。文件名不使用后缀，读取文件时需要清楚文件的类型
	 * 
	 * @param data
	 *            数据，如果传入null，则创建一个空白的文件
	 * @param dir
	 *            要保存文件的目录，目录基于WebContext/WEB-INF/files
	 * @return 保存后的文件路径，包括文件夹和文件名称
	 * @param suffix
	 *            文件的后缀名，如果没有传入后缀名，则不使用后缀名
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws Exceptioni
	 */
	public static String saveToFile(byte[] data, String dir, String suffix,
			String fileName) throws Exception {
		if (fileRoot == null) {
			log.error("文件根目录没有设置");
			throw new Exception("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
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

		// 写入文件
		updateFile(data, dir, newFileName, fileName);

		// 返回文件名
		return dir + "/" + newFileName;
	}

	public static void downloadFile(String dir, String filename,
			HttpServletResponse response) throws Exception {

		String filePath = dir + "/" + filename;
		boolean b = FileUtil.checkFileExist(filename);
		if (!b) {
			throw new Exception("不存在该文件！");
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
	 * 另存为一个新的文件,并返回文件名称
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
			log.error("文件根目录没有设置");
			throw new Exception("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
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
		// 写入文件
		updateFile(data, dir, newFileName, fileName);
		// 返回文件名
		return dir + "/" + newFileName;
	}

	/**
	 * 另存为一个新的文件,并返回文件名称
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
			log.error("文件根目录没有设置");
			throw new Exception("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
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
		// 写入文件
		updateFile(data, dir, newFileName, fileName);
		// 返回文件名
		return dir + "/" + newFileName;
	}

	/**
	 * 更新文件数据，如果文件不存在，则创建新的文件
	 * 
	 * @param data
	 * @param dir
	 * @param fileName
	 * @param decName
	 * @throws Exception
	 */
	public static void updateFile(byte[] data, String dir, String fileName,
			String decName) throws Exception {

		// 如果目录不存在，将创建目录
		File dirs = new File(fileRoot + "/" + dir);
		if (!dirs.exists() || !dirs.isDirectory()) {
			if (!dirs.mkdirs()) {
				throw new Exception("无法创建文件目录：" + dirs.getAbsolutePath());
			}
		}
		// 如果没有数据，则创建一个空的文件
		if (data == null) {
			data = new byte[0];
		}

		// 写文件

		File file = new File(dirs, fileName.replaceFirst(fileRoot, ""));
		// 如果文件已经存在，则先删除文件
		if (file.exists()) {
			file.delete();
		} else {
			if (!file.createNewFile()) {
				throw new Exception("无法创建文件：" + file.getName());
			}
		}
		FileOutputStream out = new FileOutputStream(file);
		out.write(data);
		out.close();

		// 记录文件名称信息
		if (isGeneratedFile(fileName) && decName != null
				&& decName.trim().length() > 0) {
			logFileInfo(getGeneratedTime(fileName), decName);
		}
	}

	/**
	 * 从文件系统中读取文件，以字节数组的方式返回
	 * 
	 * @param path
	 *            文件的路径
	 * @return 文件的字节数组
	 * @throws Exception
	 *             文件不存在或者发生IO错误
	 */
	public static byte[] loadFromFile(String path) throws Exception {
		if (fileRoot == null) {
			log.error("文件根目录没有设置");
			throw new Exception("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("文件路径为空");
			throw new Exception("无法从文件 '" + path + "' 中读取数据");
		}

		File file = new File(fileRoot, path.replaceAll(fileRoot, ""));
		if (!file.exists() || !file.isFile()) {
			log.error("文件 [" + path + "] 不存在");
			throw new Exception("无法从文件 '" + path + "' 中读取数据，文件不存在");
		}

		if (!file.canRead()) {
			log.error("文件不能读取");
			throw new Exception("无法从文件 '" + path + "' 中读取数据，文件不能读");
		}

		// 打开文件流并装载文件
		FileInputStream in = new FileInputStream(file);
		byte[] datas = new byte[(int) file.length()];
		in.read(datas);
		// 关闭文件流
		in.close();

		return datas;
	}

	public static File loadFile(String path) throws Exception {
		if (fileRoot == null) {
			log.error("文件根目录没有设置");
			throw new Exception("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("文件路径为空");
			throw new Exception("无法从文件 '" + path + "' 中读取数据");
		}

		File file = new File(fileRoot, path.replaceAll(fileRoot, ""));
		if (!file.exists() || !file.isFile()) {
			log.error("文件 [" + path + "] 不存在");
			throw new Exception("无法从文件 '" + path + "' 中读取数据，文件不存在");
		}

		return file;
	}

	/**
	 * 删除指定路径的文件
	 * 
	 * @param path
	 *            要删除的文件路径
	 * @return 删除成功返回true，失败返回false
	 * @throws Exception
	 *             删除过程出现错误
	 */
	public static boolean deleteFile(String path) {
		if (fileRoot == null) {
			log.info("文件上下文路径没有指定，请重新启动系统，如果问题仍然存在，请检查相关配置");
		}
		if (path == null || path.trim().length() == 0) {
			log.error("文件路径为空");
			return true;
		}

		/*
		 * //不是生成的文件不删除 if (!isGeneratedFile(path)) { return true; }
		 */

		File file = new File(fileRoot, path);
		if (!file.exists() || !file.isFile()) {
			log.error("文件 [" + path + "] 不存在");
			return true;
		}

		return file.delete();
	}

	/**
	 * 设置WebContextPath在本地文件系统的路径
	 * 
	 * @param root
	 *            WebContextPath在本地文件系统的路径
	 */
	/*
	 * public static void setLocalRoot(String root) { fileRoot = root + "/" +
	 * "WEB-INF" + "/" + "files"; }
	 */

	/**
	 * 获取指定目录下的文件列表，返回文件名称
	 * 
	 * @param dir
	 *            指定的目录，该目录必需基于ContextPath的WEB-INF/files目录下
	 * @param exts
	 *            要列出的文件的扩展名数组，扩展名不在该数组中的不予列出，不指定扩展名请传入null或者长度为0的数组
	 * @return dir目录下符合指定扩展名的文件名称列表
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
	 * 获取文件的后缀名
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
	 * 获取原始的文件名称
	 * 
	 * @param newFileName
	 *            新的文件名，文件名必需使用saveToFile返回，否则将返回newFileName
	 * @return 如果没有相关的信息，则返回newFileName
	 */
	public static String getOriginalFileName(String newFileName) {
		if (newFileName == null || newFileName.trim().length() == 0) {
			return newFileName;
		}
		// 解析文件名称，取最后一部分
		int index = -1;
		// 将所有反斜杠换成正斜杠
		newFileName = newFileName.replaceAll("\\\\", "/");
		index = newFileName.lastIndexOf("/");
		if (index > 0) {
			newFileName = newFileName.substring(index + 1);
		}
		// 去掉后缀名
		index = newFileName.lastIndexOf(".");
		if (index > 0) {
			newFileName = newFileName.substring(0, index);
		}
		long time = -1;
		try {
			time = Long.parseLong(newFileName);
		} catch (Exception ex) {
			log.error("文件名称不合法，无法获取相关信息", ex);
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
	 * 根据传入的时间获取文件信息文件，如果文件不存在，则创建一个，文件每月创建一个
	 * 
	 * @param time
	 * @return
	 */
	private static File getInfoFile(long time) throws Exception {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time);
		String fileName = c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)
				+ ".info";
		// 信息文件存放于 system/fileinfo目录下
		File dir = new File(getFileRoot() + "/system/fileinfo");
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdirs();
		}
		File f = new File(dir, fileName);
		// 文件不存在则创建一个新的文件
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

	/**
	 * 记录文件名称信息
	 * 
	 * @param time
	 * @param fileName
	 */
	private static void logFileInfo(long time, String fileName) {
		try {
			// 获取最后的文件名称
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
			log.error("记录文件信息失败", ex);
		}
	}

	/**
	 * 获取文件名称信息
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
			log.error("获取文件信息失败", ex);
			return String.valueOf(time);
		}
	}

	/**
	 * 判断文件是否自动生成的
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
	 * 获取文件创建时间
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
	 * 将一个字符串转换成一个Unicode字符串
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
	 * 检查指定的文件是否存在
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
	 * 根据路径名称获取文件名称
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
