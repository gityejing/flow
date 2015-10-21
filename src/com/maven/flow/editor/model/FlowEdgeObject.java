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
	
	private String conditionClass;//����������
	
	private String isConditionPath="0";//�Ƿ���������·��.  ����������ʱ,���Զ�һЩĿ��·��.
	
	private String conditionExpress;//�������ʽ
	
	private String finishCondition;//���·��������
	
	private String isBack="0";//�Ƿ��ǻ��˲���.
	
	private String isRouteAsynchronism="0";//�Ƿ����첽���衣
	
	
	
	{
		//
		this.updatePropertyDescription("name", "·������");

		//�������
		this.addProperty("description", "·������", FlowProperty.STRING, 0, 0, true);
		this.addProperty("displaytext", "·����ʾ����", FlowProperty.STRING, 0, 2);
	
		//        this.addProperty("positionurl", "·��ҳ����Ϣ", FlowProperty.OPTION);

		this.addProperty("beginprocess", "��ʼ����", FlowProperty.STRING, false, 0, 3);
		this.addProperty("endprocess", "��������", FlowProperty.STRING, false, 0, 4);//��������.
		
		this.addProperty("isRouteAsynchronism", "�Ƿ����첽����", FlowProperty.OPTION,5,0,true);
		
		this.addProperty("isBack", "�Ƿ��ǻ���·��", FlowProperty.OPTION,5,0,true);
		
		
		this.addProperty("isConditionPath", "����·������", FlowProperty.OPTION,5,0,true);
		this.addProperty("conditionClass", "�������ʽ", FlowProperty.OPTION, 5, 0, true);
		this.addProperty("finishCondition", "���·��������", FlowProperty.OPTION, 5, 0, true);
		
		//this.addProperty("conditionClass", "����������", FlowProperty.OPTION,5,0,true);
		
		
		this.addProperty("subFlowPath", "������·��", FlowProperty.OPTION, 0, 2);
		
		
		
		//this.addListValueAdapter("conditionClass", new FlowPathClass());
		
		
		this.addListValue("isRouteAsynchronism", new KeyValueObject[] { new KeyValueObject("0", "��"),
				new KeyValueObject("1", "��") });
		
		this.addListValue("isBack", new KeyValueObject[] { new KeyValueObject("0", "��"),
				new KeyValueObject("1", "��") });
		
		this.addListValue("isConditionPath", new KeyValueObject[] { new KeyValueObject("0", "������·��"),
				new KeyValueObject("1", "������·��") });	
		
		this.addListValue("subFlowPath", new KeyValueObject[] { new KeyValueObject(new Integer(0), "��"),
				new KeyValueObject(new Integer(1), "��") });
		this.addListValueAdapter("conditionClass", new FlowPathClass());
		
		this.addListValueAdapter("finishCondition", new FinishClass());
		
		//����б�ֵ
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
				return "��";
			} else {
				return this.getBeginProcess().getName();
			}
		} else if ("endprocess".equals(propertyName)) {
			if (this.getEndProcess() == null) {
				return "��";
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

		//��ʼ��·������
		this.setName(this.getInitEdgeName());
	}

	private String getSourceName(DefaultPort port) {
		return String.valueOf(((DefaultGraphCell) port.getParent()).getUserObject());
	}

	private String getTargetName(DefaultPort port) {
		return String.valueOf(((DefaultGraphCell) port.getParent()).getUserObject());
	}

	/**
	 *  ��ȡ��ʼ��·������
	 * @return
	 */
	private String getInitEdgeName() {
		DefaultPort source = (DefaultPort) edge.getSource();
		DefaultPort target = (DefaultPort) edge.getTarget();
		if (source == null) {
			if (target == null) {
				return "·��";
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
	 * ��ȡ·����ʼ����
	 * @return
	 */
	public FlowElementObject getBeginProcess() {
		//��ȡ��ʼԪ��
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
	 * ��ȡ·����������
	 * @return
	 */
	public FlowElementObject getEndProcess() {
		//��ȡ����������Ϣ
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
		 * ��ȡ�Զ�������ֽ��������Ϣ 
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
		 * ��ȡ�Զ�������ֽ��������Ϣ 
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