/**
 * @(#)PagesAdapter.java 2007-6-7
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.adapter.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.util.ResourceConfig;

/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-7
 * @since       JDK1.5
 */

public class PagesAdapter implements KeyValueObjectAdapter,Serializable {

    public static final int TYPE_VIEW = 0;
    public static final int TYPE_HANDLE = 1;
    public static final int TYPE_START = 2;
    public static final int TYPE_MULTIJOB = 3;
    public static final int TYPE_ALLOWSPLIT = 4;
    public static final int SPLITPPROCESSHANDLE=5;
    
    private int type = -1;

    public PagesAdapter(int type){
        this.type = type;
    }

    public KeyValueObject[] getKeyValueObjects() {
    	ResourceConfig config = ResourceConfig.getConfig("floweditor.properties");
    	String[] configs = config.getStringGroupConfig("pages");
    	if(configs == null || configs.length == 0)
    		return new KeyValueObject[0];
    	List tmpList = new ArrayList();
    	for (int i = 0; i < configs.length; i++) {
			String[] tmp = configs[i].split(" ");
			if(tmp.length < 3)
				continue;

			//�ж�����
			if(String.valueOf(this.type).equals(tmp[1])){
				tmpList.add(new KeyValueObject(tmp[2],tmp[0]));
			}
		}

    	return (KeyValueObject[]) tmpList.toArray(new KeyValueObject[tmpList.size()]);
    	/*
        switch(type){
            case TYPE_HANDLE:
                return new KeyValueObject[] {
                    new KeyValueObject("costagency/projectBase.do?method=plan", "�������ҳ��"),
                    new KeyValueObject("costagency/projectBase.do?method=process", "�����˴���ҳ��"),
                    new KeyValueObject("costagency/projectIncomeDetail.do?method=add","������ҳ��")
                };
            case TYPE_VIEW:
                return new KeyValueObject[]{
                    new KeyValueObject("costagency/projectBase.do?method=viewInfo","������Ϣ�鿴"),
                    new KeyValueObject("costagency/projectIncomeDetail.do?method=view","������鿴")
                };
            case TYPE_START:
                return new KeyValueObject[]{
                    new KeyValueObject("costagency/projectBase.do?method=view", "��Ŀ�Ǽ�ҳ��")
                };
            default:
                return new KeyValueObject[0];
        }
        */
    }

}
