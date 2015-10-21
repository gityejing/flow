/**
 * @(#)Message.java 2007-6-8
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.io.Serializable;

/**
 * 封装消息视图显示的信息
 *
 * @author      kinz
 * @version     1.0 2007-6-8
 * @since       JDK1.5
 */

public class Message implements Serializable {

	public static final int TYPE_INFO = 0;

	public static final int TYPE_WARN = 1;

	public static final int TYPE_ERROR = 2;

	private String group = "";

	private String title = "";

	private String message = "";

	private int type = TYPE_INFO;

	/**
	 * 创建一个消息对象
	 * @param type
	 * @param message
	 * @return
	 */
	public static Message createMessage(int type,String message){
		Message msg = new Message();
		
		msg.setType(type);
		msg.setMessage(message);
		
		return msg;
	}
	
	/**
	 * 创建一个INFO类型的消息对象
	 * @param message
	 * @return
	 */
	public static Message createInfoMessage(String message){
		return createMessage(TYPE_INFO,message);
	}
	
	/**
	 * 创建一个WARN类型的消息对象
	 * @param message
	 * @return
	 */
	public static Message createWarnMessage(String message){
		return createMessage(TYPE_WARN,message);
	}
	
	/**
	 * 创建一个ERROR类型的消息对象
	 * @param message
	 * @return
	 */
	public static Message createErrorMessage(String message){
		return createMessage(TYPE_ERROR,message);
	}
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
