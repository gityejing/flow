/**
 * @(#)ResultObject.java 2007-6-9
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.adapter;

import java.io.Serializable;
import java.util.List;

/**
 * ��װApplet��������˵�����������Ϣ
 *
 * @author      kinz
 * @version     1.0 2007-6-9
 * @since       JDK1.5
 */

public class ResultObject implements Serializable {

	/**
	 * ������ɹ�
	 */
	public static final int SUCCESS = 0;

	/**
	 * �����ʧ��
	 */
	public static final int FAILED = 2;

	private int result = -1;

	private Object object = null;//�����Ϣ

	private List messages = null;//��ص���Ϣ

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
