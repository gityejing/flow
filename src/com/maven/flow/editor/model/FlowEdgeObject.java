/**
 * @(#)FlowEdgeObject.java 2007-6-5
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.editor.model;

import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;

import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.extend.interfaces.ClassNameGetter;
import com.maven.flow.editor.extend.interfaces.IFinishCondiction;
import com.maven.flow.editor.extend.interfaces.IFlowPath;


/**
 *
 *
 * @author      kinz
 * @version     1.0 2007-6-5
 * @since       JDK1.4
 */

public class FlowEdgeObject extends FlowElementObject {
	
	private static final long serialVersionUID = 1483107870784967106L;
	
	private DefaultEdge edge = null;

	private String description = "";

	private String displayText = "";

	private int subFlowPath=0;
	
	private String positionUrl = "";
	
	private String conditionClass;//条件处理类
	
	private String isConditionPath="0";//是否是有条件路径.  当条件成熟时,可以多一些目标路径.
	
	private String conditionExpress;//条件表达式
	
	private String finishCondition;//完成路径的条件
	
	private String isBack="0";//是否是回退步骤.
	
	private String isRouteAsynchronism="0";//是否是异步步骤。
	
	
	
	{
		//
		this.updatePropertyDescription("name", "路径名称");

		//添加属性
		this.addProperty("description", "路径描述", FlowProperty.STRING, 0, 0, true);
		this.addProperty("displaytext", "路径显示文字", FlowProperty.STRING, 0, 2);
	
		//        this.addProperty("positionurl", "路径页面信息", FlowProperty.OPTION);

		this.addProperty("beginprocess", "开始步骤", FlowProperty.STRING, false, 0, 3);
		this.addProperty("endprocess", "结束步骤", FlowProperty.STRING, false, 0, 4);//组与排序.
		
		this.addProperty("isRouteAsynchronism", "是否是异步步骤", FlowProperty.OPTION,5,0,true);
		
		this.addProperty("isBack", "是否是回退路径", FlowProperty.OPTION,5,0,true);
		
		
		this.addProperty("isConditionPath", "条件路径属性", FlowProperty.OPTION,5,0,true);
		this.addProperty("conditionClass", "条件表达式", FlowProperty.OPTION, 5, 0, true);
		this.addProperty("finishCondition", "完成路径的条件", FlowProperty.OPTION, 5, 0, true);
		
		//this.addProperty("conditionClass", "条件触发器", FlowProperty.OPTION,5,0,true);
		
		
		this.addProperty("subFlowPath", "子流程路径", FlowProperty.OPTION, 0, 2);
		
		
		
		//this.addListValueAdapter("conditionClass", new FlowPathClass());
		
		
		this.addListValue("isRouteAsynchronism", new KeyValueObject[] { new KeyValueObject("0", "否"),
				new KeyValueObject("1", "是") });
		
		this.addListValue("isBack", new KeyValueObject[] { new KeyValueObject("0", "否"),
				new KeyValueObject("1", "是") });
		
		this.addListValue("isConditionPath", new KeyValueObject[] { new KeyValueObject("0", "无条件路径"),
				new KeyValueObject("1", "有条件路径") });	
		
		this.addListValue("subFlowPath", new KeyValueObject[] { new KeyValueObject(new Integer(0), "否"),
				new KeyValueObject(new Integer(1), "是") });
		this.addListValueAdapter("conditionClass", new FlowPathClass());
		
		this.addListValueAdapter("finishCondition", new FinishClass());
		
		//添加列表值
		//this.addListValue("positionurl", new PagesAdapter()
		//        .getKeyValueObjects());
	}

	public FlowEdgeObject() {
		super();
	}
	
	public Object getValue(String propertyName) {
		if ("description".equals(propertyName)) {
			return this.getDescription();
		} else if ("displaytext".equals(propertyName)) {
			return this.getDisplayText();
		} else if ("positionurl".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getPositionUrl());
		}else if ("subFlowPath".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, new Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this.getSubFlowPath()));
		}
		else if ("isConditionPath".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getIsConditionPath());
		}
		else if ("conditionExpress".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getConditionExpress();
		}

		
		
		else if ("isRouteAsynchronism".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getIsRouteAsynchronism());
			
		}
		else if ("isBack".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getIsBack());
		}else if ("conditionClass".equals(propertyName)) {
			//return this.getCustomizeTaskRule();
			//return this.getKeyValueObjectName(propertyName, this.getCustomizeTaskRule());
			return this.getKeyValueObject(propertyName, this.getConditionClass());
		}
		else if ("finishCondition".equals(propertyName)) {
			//return this.getKeyValueObjectName(propertyName, this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getFinishCondition());
		}
		
		else if ("beginprocess".equals(propertyName)) {
			if (this.getBeginProcess() == null) {
				return "无";
			} else {
				return this.getBeginProcess().getName();
			}
		} else if ("endprocess".equals(propertyName)) {
			if (this.getEndProcess() == null) {
				return "无";
			} else {
				return this.getEndProcess().getName();
			}
		}
		return super.getValue(propertyName);
	}

	public void setValue(String propertyName, Object value) {
		if ("description".equals(propertyName)) {
			this.setDescription(String.valueOf(value));
		} else if ("displaytext".equals(propertyName)) {
			this.setDisplayText(String.valueOf(value));
		}
		else if ("subFlowPath".equals(propertyName)) {
			this.setSubFlowPath(((Integer) ((KeyValueObject) value).getKey()).intValue());
		}
		
		else if ("isConditionPath".equals(propertyName)) {
			if (value != null) {
				this.setIsConditionPath((String)((KeyValueObject) value).getKey());
			}
		}
		else if ("conditionExpress".equals(propertyName)) {
			if (value != null) {
				this.setConditionExpress(String.valueOf(value));
			}
		}
		
		
		else if ("isBack".equals(propertyName)) {
			if (value != null) {
				this.setIsBack((String)((KeyValueObject) value).getKey());
			}
		}
		else if ("isRouteAsynchronism".equals(propertyName)) {
			if (value != null) {
				this.setIsRouteAsynchronism((String)((KeyValueObject) value).getKey());
			}
		}
		else if ("conditionClass".equals(propertyName)) {
			if (value == null)
				this.setConditionClass("");
			else
				this.setConditionClass((String)((KeyValueObject) value).getKey());
		}
		
		else if ("finishCondition".equals(propertyName)) {
			if (value == null)
				this.setFinishCondition("");
			else
				this.setFinishCondition((String)((KeyValueObject) value).getKey());
		}
		
		
		else if ("positionurl".equals(propertyName)) {
			if (value != null)
				this.setPositionUrl((String) ((KeyValueObject) value).getKey());
		} else {
			super.setValue(propertyName, value);
		}
	}

	public FlowEdgeObject(DefaultEdge edge) {
		this.edge = edge;

		//初始化路径名称
		this.setName(this.getInitEdgeName());
	}

	private String getSourceName(DefaultPort port) {
		return String.valueOf(((DefaultGraphCell) port.getParent()).getUserObject());
	}

	private String getTargetName(DefaultPort port) {
		return String.valueOf(((DefaultGraphCell) port.getParent()).getUserObject());
	}

	/**
	 *  获取初始化路径名称
	 * @return
	 */
	private String getInitEdgeName() {
		DefaultPort source = (DefaultPort) edge.getSource();
		DefaultPort target = (DefaultPort) edge.getTarget();
		if (source == null) {
			if (target == null) {
				return "路径";
			} else {
				return "- " + this.getTargetName(target);
			}
		} else {
			if (target == null) {
				return this.getSourceName(source) + " - ";
			} else {
				return "" + this.getSourceName(source) + " - " + this.getTargetName(target) + "";
			}
		}
	}

	public String getIconResource() {
		return null;
	}

	public String getImageResource() {
		return null;
	}

	/**
	 * 获取路径开始步骤
	 * @return
	 */
	public FlowElementObject getBeginProcess() {
		//获取开始元素
		if (this.edge == null)
			return null;
		DefaultPort sourcePort = (DefaultPort) edge.getSource();
		if (sourcePort == null)
			return null;
		Object obj = sourcePort.getParent();
		if (obj instanceof DefaultGraphCell) {
			Object uo = ((DefaultGraphCell) obj).getUserObject();
			if (uo instanceof FlowElementObject) {
				return (FlowElementObject) uo;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public DefaultEdge getEdge() {
		return edge;
	}

	public void setEdge(DefaultEdge edge) {
		this.edge = edge;
	}



	public int getSubFlowPath() {
		return subFlowPath;
	}

	public void setSubFlowPath(int subFlowPath) {
		this.subFlowPath = subFlowPath;
	}

	/**
	 * 获取路径结束步骤
	 * @return
	 */
	public FlowElementObject getEndProcess() {
		//获取结束步骤信息
		if (this.edge == null)
			return null;
		DefaultPort targetPort = (DefaultPort) edge.getTarget();
		if (targetPort == null)
			return null;
		Object obj = targetPort.getParent();
		if (obj instanceof DefaultGraphCell) {
			Object uo = ((DefaultGraphCell) obj).getUserObject();
			if (uo instanceof FlowElementObject) {
				return (FlowElementObject) uo;
			}
		}
		return null;
	}

	public String getPositionUrl() {
		return positionUrl;
	}

	public void setPositionUrl(String positionUrl) {
		this.positionUrl = positionUrl;
	}

	public String getConditionClass() {
		return conditionClass;
	}

	public void setConditionClass(String conditionClass) {
		this.conditionClass = conditionClass;
	}

	public String getConditionExpress() {
		return conditionExpress;
	}

	public void setConditionExpress(String conditionExpress) {
		this.conditionExpress = conditionExpress;
	}

	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public String getIsConditionPath() {
		return isConditionPath;
	}

	public void setIsConditionPath(String isConditionPath) {
		this.isConditionPath = isConditionPath;
	}

	public String getIsRouteAsynchronism() {
		return isRouteAsynchronism;
	}

	public String getFinishCondition() {
		return finishCondition;
	}

	public void setFinishCondition(String finishCondition) {
		this.finishCondition = finishCondition;
	}

	public void setIsRouteAsynchronism(String isRouteAsynchronism) {
		this.isRouteAsynchronism = isRouteAsynchronism;
	}
	class FlowPathClass implements KeyValueObjectAdapter,java.io.Serializable {
		/**
		 * 获取自定义任务分解规则处理信息 
		 */
		public KeyValueObject[] getKeyValueObjects() {
			//ResourceConfig cfg = ResourceConfig.getConfig("floweditor.properties");
			String[] spliters = ClassNameGetter.getFlowPathsClass();
			List tmp = new ArrayList();
			for (int i = 0; i < spliters.length; i++) {
				if (spliters[i] == null || spliters[i].trim().length() == 0)
					continue;

				try {
					Class clz = Class.forName(spliters[i].trim());
					Object obj = clz.newInstance();
					if (obj instanceof IFlowPath) {
						IFlowPath spliter = (IFlowPath) obj;
						KeyValueObject kvo = new KeyValueObject(spliters[i].trim(), spliter.getFlowConditionName());
						tmp.add(kvo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return (KeyValueObject[]) tmp.toArray(new KeyValueObject[tmp.size()]);
		}
	}
	
	class FinishClass implements KeyValueObjectAdapter,java.io.Serializable {
		/**
		 * 获取自定义任务分解规则处理信息 
		 */
		public KeyValueObject[] getKeyValueObjects() {
			//ResourceConfig cfg = ResourceConfig.getConfig("floweditor.properties");
			String[] spliters = ClassNameGetter.getFinishCondiction();
			List tmp = new ArrayList();
			for (int i = 0; i < spliters.length; i++) {
				if (spliters[i] == null || spliters[i].trim().length() == 0)
					continue;
				
				try {
					Class clz = Class.forName(spliters[i].trim());
					Object obj = clz.newInstance();
					if (obj instanceof IFinishCondiction) {
						IFinishCondiction spliter = (IFinishCondiction) obj;
						KeyValueObject kvo = new KeyValueObject(spliters[i].trim(), spliter.getFlowConditionName());
						tmp.add(kvo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return (KeyValueObject[]) tmp.toArray(new KeyValueObject[tmp.size()]);
		}
	}
}