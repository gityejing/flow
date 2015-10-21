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

			//判断类型
			if(String.valueOf(this.type).equals(tmp[1])){
				tmpList.add(new KeyValueObject(tmp[2],tmp[0]));
			}
		}

    	return (KeyValueObject[]) tmpList.toArray(new KeyValueObject[tmpList.size()]);
    	/*
        switch(type){
            case TYPE_HANDLE:
                return new KeyValueObject[] {
                    new KeyValueObject("costagency/projectBase.do?method=plan", "任务分配页面"),
                    new KeyValueObject("costagency/projectBase.do?method=process", "经办人处理页面"),
                    new KeyValueObject("costagency/projectIncomeDetail.do?method=add","财务处理页面")
                };
            case TYPE_VIEW:
                return new KeyValueObject[]{
                    new KeyValueObject("costagency/projectBase.do?method=viewInfo","任务信息查看"),
                    new KeyValueObject("costagency/projectIncomeDetail.do?method=view","财务处理查看")
                };
            case TYPE_START:
                return new KeyValueObject[]{
                    new KeyValueObject("costagency/projectBase.do?method=view", "项目登记页面")
                };
            default:
                return new KeyValueObject[0];
        }
        */
    }

}
