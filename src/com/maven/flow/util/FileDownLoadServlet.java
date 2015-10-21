/**
 * @(#)FileDownLoadServlet.java 2007-6-22
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 * 
 */
package com.maven.flow.util;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileDownLoadServlet extends HttpServlet {

	private Log log = LogFactory.getLog(this.getClass());

	public void init(ServletConfig config) {
		//		 log.info("初始化WebContextFileSystem...");
		//	        String contextLocation = config.getServletContext().getRealPath("");
		//	        FileUtil.setLocalRoot(contextLocation);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String file = request.getParameter("file");
		try {
			byte[] data = FileUtil.loadFromFile(file);
			if (type != null && type.trim().equalsIgnoreCase("download")) {
				//获取文件名称
				int index = file.lastIndexOf("/");
				String fileName = null;
				if (index != -1) {
					fileName = file.substring(index + 1, file.length());
				} else {
					fileName = file;
				}
				//String fileName = FileUtil.getOriginalFileName(file);
				response.setContentType("application/x");
				response.setHeader("Content-disposition", "attachment; filename=" + StringUtil.toUtf8String(fileName));
			}
			response.getOutputStream().write(data);
		} catch (Exception ex) {
			log.debug("", ex);
			//request.setAttribute("message", "无法打开文件，可能文件已经被删除或者文件已经损坏");
			request.setAttribute("exception", new Exception("无法打开文件，可能文件已经被删除或者文件已经损坏"));
			request.getRequestDispatcher("/costagency/error.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
