/**
 * @(#)StringUtil.java 2007-6-19
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 *
 */
package com.maven.flow.util;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 *
 * @author    jumty
 * @version   1.0 2007-6-19
 * @since      JDK1.4
 */
public class StringUtil {

	private static Log log = LogFactory.getLog(StringUtil.class);

	public static boolean isNull(String str){
        if(str == null)
            return true;
        if(str.trim().length() == 0)
            return true;
        return false;
    }
	/**
	 * 转换成UTF8编码
	 *
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= '\377') {
				sb.append(c);
			} else {
				byte b[];
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0) {
						k += 256;
					}
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 匹配pattern，使用正则表达式
	 *
	 * @param str
	 * @param patterns
	 * @return
	 */
	public static boolean matchPatter(String str, String[] patterns) {
		if (str == null || patterns == null)
			return false;
		for (int i = 0; i < patterns.length; i++) {
			log.debug("matching [" + str + "  -->  " + patterns[i] + "]");
			if (patterns[i] != null && Pattern.matches(patterns[i], str))
				return true;
		}
		return false;
	}

	/**
	 * 将一个字符串转换成一个Unicode字符串
	 * @param strText
	 * @return
	 * @throws Exception
	 */
	public static String toUnicode(String strText) throws Exception {
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

}
