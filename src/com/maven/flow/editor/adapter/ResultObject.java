/**
 * @(#)ResultObject.java 2007-6-9
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import java.io.Serializable;
import java.util.List;

/**
 * 封装Applet与服务器端的请求处理结果信息
 *
 * @author      kinz
 * @version     1.0 2007-6-9
 * @since       JDK1.5
 */

public class ResultObject implements Serializable {

	/**
	 * 结果：成功
	 */
	public static final int SUCCESS = 0;

	/**
	 * 结果：失败
	 */
	public static final int FAILED = 2;

	private int result = -1;

	private Object object = null;//结果信息

	private List messages = null;//相关的消息

	public List getMessages() {
		return messages;
	}

	public void setMessages(List messages) {
		this.messages = messages;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object resultObject) {
		this.object = resultObject;
	}

}
