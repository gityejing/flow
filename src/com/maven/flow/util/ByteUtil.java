/**
 * @(#)ByteUtil.java 2007-6-8
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.util;

/**
 * 提供字节相关操作的功能
 *
 * @author      kinz
 * @version     1.0 2007-6-8
 * @since       JDK1.5
 */

public class ByteUtil {

	/**
	 * 把对象转成base64编码
	 * @param object
	 * @return
	 */
	public static String objectBase64Encode(Object object) {
		try {
			java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
			java.io.ObjectOutputStream objout = new java.io.ObjectOutputStream(
					out);
			objout.writeObject(object);
			byte[] byteArray = out.toByteArray();
			sun.misc.BASE64Encoder base64En = new sun.misc.BASE64Encoder();
			return base64En.encode(byteArray);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把base64编码转成对象
	 * @param str
	 * @return
	 */
	public static Object objectBase64Decode(String str) {
		try {
			sun.misc.BASE64Decoder base64De = new sun.misc.BASE64Decoder();
			byte[] byteArray = base64De.decodeBuffer(str);
			java.io.ByteArrayInputStream byteIn = new java.io.ByteArrayInputStream(
					byteArray);
			java.io.ObjectInputStream objIn = new java.io.ObjectInputStream(
					byteIn);
			return objIn.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
