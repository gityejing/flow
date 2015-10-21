/**
 * @(#)EndElement.java 2007-5-30
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;


/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-5-30
 * @since       JDK1.4
 */

public class EndElement extends ProcessElementObject {
	private String beforeHandleClass;// 前置处理器;
	
    {
    	this.removeAllProperty();//该元素需要删除其它元素。
    	
        this.addProperty("name", "名称", FlowProperty.STRING,0,0,true);   
        this.setName("结束");
        
		this.addProperty("beforeHandleClass", "前置处理器", FlowProperty.OPTION, 5,
				0, true);
		
		this.addListValueAdapter("beforeHandleClass", new BeforeHandleClass());
		
        this.updatePropertyDescription("name","步骤名称");
    }
    
    
    public String getBeforeHandleClass() {
		return beforeHandleClass;
	}

	public void setBeforeHandleClass(String beforeHandleClass) {
		this.beforeHandleClass = beforeHandleClass;
	}

	public String getIconResource() {
        return "resources/end_small.gif";
    }

    public String getImageResource() {
        return "resources/end_large.gif";
    }

    public Object getValue(String propertyName) {
        return super.getValue(propertyName);
    }

    public void setValue(String propertyName, Object value) {
        super.setValue(propertyName,value);
    }

}