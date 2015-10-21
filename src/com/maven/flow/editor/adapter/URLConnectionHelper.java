/**
 * @(#)URLConnectionHelper.java 2007-6-8
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

import com.maven.flow.util.ByteUtil;

/**
 * 提供URLConnection的一些操作功能
 *
 * @author      kinz
 * @version     1.0 2007-6-8
 * @since       JDK1.5
 */

public class URLConnectionHelper {

	/**
	 * 将一个对象发送到指定的url，url应该指向一个servlet
	 * @param url
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static ResultObject sendRequest(String url, Serializable obj)
			throws Exception {
		//获取数据
		byte[] datas = null;
		if (obj != null) {
			datas = ByteUtil.objectBase64Encode(obj).getBytes();
		} else {
			datas = new byte[0];
		}

		//进行连接
		URLConnection con = new URL(url).openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);

		//设置相关属性
		con.setUseCaches(false);
		con.setRequestProperty("Content-type", "application/octet-stream");
		con.setRequestProperty("Content-length", "" + datas.length);

		//发送对象
		OutputStream out = con.getOutputStream();
		out.write(datas);
		out.flush();
		out.close();

		//DataInputStream in = new DataInputStream(con.getInputStream());
		InputStream in = con.getInputStream();
		int c = -1;

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while ((c = in.read()) != -1) {
			baos.write(c);
		}

		in.close();

		Object result = ByteUtil.objectBase64Decode(new String(baos
				.toByteArray()));

		if (result instanceof Exception) {
			throw (Exception) result;
		} else if (result instanceof ResultObject) {
			return (ResultObject) result;
		} else {
			throw new Exception("不能处理的返回结果: " + result.getClass().getName());
		}
	}
}
