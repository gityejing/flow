/**
 * @(#)ProcessElementObject.java 2007-06-07
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */

package com.maven.flow.editor.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.maven.flow.core.ICustomizeJobSpliter;
import com.maven.flow.core.ICustomizeProcessCompleteChecker;
import com.maven.flow.core.IEmployeeTypesSpliter;
import com.maven.flow.editor.adapter.CustomizePropertyAdapter;
import com.maven.flow.editor.adapter.KeyValueObjectAdapter;
import com.maven.flow.editor.adapter.impl.ProcessDocPropertyAdapter;
import com.maven.flow.editor.adapter.impl.ProcessFolderAccessPropertyAdapter;
import com.maven.flow.editor.adapter.impl.ProjectRoles;
import com.maven.flow.editor.adapter.impl.RolesAdapter;
import com.maven.flow.editor.adapter.impl.UsersAdapter;
import com.maven.flow.editor.extend.interfaces.ClassNameGetter;
import com.maven.flow.editor.extend.interfaces.IAfterHandleClass;
import com.maven.flow.editor.extend.interfaces.IBeforeHandleClass;
import com.maven.flow.util.ResourceConfig;

/**
 * 
 * 公用的步骤元素对象.
 * 
 * 
 */

public abstract class ProcessElementObject extends FlowElementObject {

	public static final long serialVersionUID = 1636105772671125840L;

	/**
	 * 流程环节的属性
	 */

	private int taskRule = 0;

	private int routeRule = 0;

	private String description = "";

	private String displayText = "";

	private String customizeTaskRule = "";

	private int completeRule = 0;

	private String customizeCompleteRule = "";

	private int docFolder = 0;

	private String positionUrl = "";

	private List accessRoles = new ArrayList();

	private List accessUsers = new ArrayList();

	private List projectRoles = new ArrayList();
	
	private List huiQianRoles = new ArrayList();
	
	private ProcessDocFolderInfo rootFolder = new ProcessDocFolderInfo();

	private DefaultMutableTreeNode docTreeRoot = new DefaultMutableTreeNode(
			rootFolder);// 文档目录结构的根节点，目录的名称与步骤名称一致

	private long parentProcessId;// 父步骤编号.

	private int isWaitForSubFlow;// 是否等待全部子流程完成.

	private int ifcanSplitFlow;// 是否可以发散流程.

	private int isfirstSubFlow;// 是否是流程的初节点..

	private String processState;// 步骤状态.

	private int splitType;// 发散方式[如,1,按点发散 2,按类发散]

	private String splitProcessHandle;// 发散处理类.

	private int issubFlowStep;// 是否是子流程的步骤.

	private int innerStep;// 是否是子流程的步骤.

	private int step;// 级数[针对子流程].

	private int uniteStep;// 被合并级数.[针对有子流程]

	private String stepState;// 步骤状态

	// private String projectRole;
	private int multiJobHandle;// 多任务处理方式

	private String isProcessAsynchronism = "0";// 是否是异步步骤。

	private String curProcessHandleMethod = "2";// 人的摆放方式。
												// 1:相同步骤一人和一个步骤,步骤下人员打散。
												// 2:步骤下的全部人集在同一步骤:步骤下人员集在一起

	private String processGroup;// 步骤组.

	private String beforeHandleClass;// 前置处理器;

	private String afterHandleClass;// 后置处理器;

	private String processCode;// 步骤编码

	private String isAgr = "0";// 是否对“拟同意”提供支持:::对拟同意提供支持，意味着可以在同一步骤里进行流转。

	private String viewPageUrl = "";// 查看页面

	private String handlePageUrl = "";// 处理页面
	
	private List docFolderAccess = new ArrayList();// 步骤文档目录结构权限

	{
		// 为本步骤的人员添加默认的权限
		ProcessDocFolderAccessInfo acc = new ProcessDocFolderAccessInfo();
		acc.setFolder(rootFolder);
		acc.setAddFolderFlag(1);
		acc.setDeleteFlag(1);
		acc.setUpdateFolderFlag(1);
		acc.setViewFlag(1);
		acc.setOperateFlag(1);
		docFolderAccess.add(acc);

		this.updatePropertyDescription("name", "步骤名称");
		this.setName("流程步骤");

		// 添加属性值
		this.addProperty("description", "步骤描述", FlowProperty.STRING, 0, 0);
		this.addProperty("displaytext", "步骤显示名称", FlowProperty.STRING, 0, 1);
		this.addProperty("routerule", "路径解析规则", FlowProperty.OPTION, false, 1,0);
		
		this.addProperty("processCode", "步骤编码", FlowProperty.STRING, 0, 0);
		
		this.addProperty("taskrule", "任务分解规则", FlowProperty.OPTION, 2, 0);
		this.addProperty("customizetaskrule", "自定义任务分解规则", FlowProperty.OPTION,
				false, 2, 1);
		this.addProperty("completerule", "完成步骤规则", FlowProperty.OPTION, 2, 2);
		this.addProperty("customizecompleterule", "自定义完成规则",
				FlowProperty.OPTION, false, 2, 3);
		// this.addProperty("positionurl", "步骤页面路径", FlowProperty.OPTION);
		this.addProperty("accessroles", "步骤权限角色", FlowProperty.LIST, 3, 0);
		this.addProperty("accessusers", "步骤权限用户", FlowProperty.LIST, 3, 1);

		this.addProperty("projectRoles", "项目权限用户", FlowProperty.LIST, 3, 1);
		
		this.addProperty("huiQianRoles", "会签给", FlowProperty.LIST, 3, 1);
		
		// 隐藏
		// this.addProperty("processdocfolders", "步骤文档结构",
		// FlowProperty.CUSTOMIZE,4, 0);
		// this.addProperty("processdocfolderaccess",
		// "步骤文档权限",FlowProperty.CUSTOMIZE, 4, 1);
		// this.addProperty("docfolder", "步骤文档载入规则", FlowProperty.OPTION, 4, 2);

		this.addProperty("ifcanSplitFlow", "是否可以发散子流程", FlowProperty.OPTION, 5,
				0);

		this.addProperty("issubFlowStep", "是否是子流程的步骤", FlowProperty.OPTION, 5,
				0);

		this.addProperty("isfirstSubFlow", "是否是子流程的初节点", FlowProperty.OPTION,
				5, 0);

		this.addProperty("step", "子流程级数", FlowProperty.OPTION, 5, 0);

		this.addProperty("uniteStep", "被合并的级数", FlowProperty.OPTION, 5, 0);

		this.addProperty("isWaitForSubFlow", "是否是流程归结节点", FlowProperty.OPTION,
				5, 0);

		this.addProperty("splitType", "发散方式", FlowProperty.OPTION, 5, 0);

		this
				.addProperty("multiJobHandle", "多任务处理方式", FlowProperty.OPTION,
						5, 0);
		// this.addProperty("stepState", "步骤状态", FlowProperty.OPTION, 5, 0);
		this.addProperty("splitProcessHandle", "发散处理方式", FlowProperty.OPTION,
				5, 0, true);

		this.addProperty("curProcessHandleMethod", "步骤下人的处理方式",
				FlowProperty.OPTION, 5, 0, true);

		this.addProperty("isProcessAsynchronism", "是否是异步步骤",
				FlowProperty.OPTION, 5, 0, true);

		this.addProperty("isAgr", "是否对[拟同意]提供支持", FlowProperty.OPTION, 5, 0,
				true);

		this.addProperty("processGroup", "步骤组", FlowProperty.OPTION, 5, 0,
						true);
		
		// this.addProperty("isAutoFilter", "下一步骤是否自动过滤",
		// FlowProperty.OPTION,5,0,true);

		// this.addProperty("filterClass", "步骤过滤器",
		// FlowProperty.OPTION,5,0,true);

		// this.addProperty("speicTriggerClass", "特殊步骤触发器",
		// FlowProperty.OPTION,5,0,true);

		this.addProperty("beforeHandleClass", "前置处理器", FlowProperty.OPTION, 5,
				0, true);

		this.addProperty("afterHandleClass", "后置处理器", FlowProperty.OPTION, 5,
				0, true);

		// 添加列表值
		this.addListValue("taskrule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "默认规则"),
				new KeyValueObject(new Integer(1), "自定义规则") });

		this.addListValue("routerule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "系统解析"),
				new KeyValueObject(new Integer(1), "手动跳转") });
		this.addListValue("completerule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不需要检查"),
				new KeyValueObject(new Integer(1), "需要检查") });
		this.addListValue("docfolder", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "默认规则"),
				new KeyValueObject(new Integer(1), "自定义规则") });
		// this.addListValueAdapter("positionurl", new PagesAdapter());
		this.addListValueAdapter("customizetaskrule", new JobSplitersAdapter());// 读取文件下的自定义方式
		this
				.addListValueAdapter("splitProcessHandle",
						new SplitProcessHandle());// 读取配制文件的发散方式

		this.addListValueAdapter("customizecompleterule",
				new CompleteCheckerAdapter());

		this.addListValue("ifcanSplitFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不可以"),
				new KeyValueObject(new Integer(1), "可以") });

		this.addListValue("issubFlowStep", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不是"),
				new KeyValueObject(new Integer(1), "是") });

		this.addListValue("isfirstSubFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不是"),
				new KeyValueObject(new Integer(1), "是") });

		this.addListValue("stepState", new KeyValueObject[] {
				new KeyValueObject(new String("1"), "项目进行中"),
				new KeyValueObject(new String("2"), "初稿"),
				new KeyValueObject(new String("3"), "正稿"),
				new KeyValueObject(new String("4"), "审定稿"),
				new KeyValueObject(new String("5"), "已完成") });

		this.addListValue("isWaitForSubFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不是"),
				new KeyValueObject(new Integer(1), "是") });

		this.addListValue("step", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "无级数"),
				new KeyValueObject(new Integer(1), "第一级"),
				new KeyValueObject(new Integer(2), "第二级"),
				new KeyValueObject(new Integer(3), "第三级") });

		this.addListValue("uniteStep", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "不用合并"),
				new KeyValueObject(new Integer(1), "第一级"),
				new KeyValueObject(new Integer(2), "第二级"),
				new KeyValueObject(new Integer(3), "第三级"),
				new KeyValueObject(new Integer(4), "第四级") });

		this.addListValue("splitType", new KeyValueObject[] {
				new KeyValueObject(new Integer(-1), "无发散"),
				new KeyValueObject(new Integer(0), "按点发散"),
				new KeyValueObject(new Integer(1), "按类发散") });

		this.addListValue("multiJobHandle", new KeyValueObject[] {

		new KeyValueObject(new Integer(1), "必须全部通过"),
				new KeyValueObject(new Integer(0), "其中之一通过")

		});

		this.addListValue("curProcessHandleMethod", new KeyValueObject[] {
				new KeyValueObject("1", "步骤下人员打散"),
				new KeyValueObject("2", "步骤下人员集在一起") });

		this.addListValue("isProcessAsynchronism", new KeyValueObject[] {
				new KeyValueObject("0", "否"), new KeyValueObject("1", "是") });

		this.addListValue("isAutoFilter", new KeyValueObject[] {
				new KeyValueObject("0", "否"), new KeyValueObject("1", "是") });

		this.addListValue("isAgr", new KeyValueObject[] {
				new KeyValueObject("0", "不提供支持"),
				new KeyValueObject("1", "提供支持") });

		this.addListValue("processGroup", new KeyValueObject[] {
				new KeyValueObject("0", "不定义组"),
				new KeyValueObject("1", "第一组"), new KeyValueObject("2", "第二组"),
				new KeyValueObject("3", "第三组"), new KeyValueObject("4", "第四组"),
				new KeyValueObject("5", "第五组"), new KeyValueObject("6", "第六组"),
				new KeyValueObject("7", "第七组"), new KeyValueObject("8", "第八组"),
				new KeyValueObject("9", "第九组") });

		// this.addListValueAdapter("filterClass", new FilterClass());

		// this.addListValueAdapter("speicTriggerClass", new
		// SpeicTriggerClass());

		 this.addListValueAdapter("beforeHandleClass", new BeforeHandleClass());

		 this.addListValueAdapter("afterHandleClass", new AfterHandleClass());
		 
	}

	public Object getValue(String propertyName) {
		if ("name".equals(propertyName)) {
			return this.getName();
		}else if ("viewpageurl".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getViewPageUrl());
			return this.viewPageUrl;
		} else if ("handlepageurl".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getHandlePageUrl());
			return this.handlePageUrl;
		} else if ("taskrule".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.taskRule));
			return this.getKeyValueObject(propertyName, new Integer(
					this.taskRule));
		} else if ("routerule".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.routeRule));
			return this.getKeyValueObject(propertyName, new Integer(
					this.routeRule));
		} else if ("description".equals(propertyName)) {
			return this.getDescription();
		} else if ("processCode".equals(propertyName)) {
			return this.getProcessCode();
		}

		else if ("displaytext".equals(propertyName)) {
			return this.getDisplayText();
		} else if ("customizetaskrule".equals(propertyName)) {
			// return this.getCustomizeTaskRule();
			// return this.getKeyValueObjectName(propertyName,
			// this.getCustomizeTaskRule());
			return this.getKeyValueObject(propertyName, this
					.getCustomizeTaskRule());
		} else if ("splitProcessHandle".equals(propertyName)) {
			// return this.getCustomizeTaskRule();
			// return this.getKeyValueObjectName(propertyName,
			// this.getCustomizeTaskRule());
			return this.getKeyValueObject(propertyName, this
					.getSplitProcessHandle());
		}

		else if ("completerule".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.completeRule));
			return this.getKeyValueObject(propertyName, new Integer(
					this.completeRule));
		} else if ("customizecompleterule".equals(propertyName)) {
			// return this.getCustomizeCompleteRule();
			// return this.getKeyValueObjectName(propertyName,
			// this.getCustomizeCompleteRule());
			return this.getKeyValueObject(propertyName, this
					.getCustomizeCompleteRule());
		} else if ("docfolder".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getDocFolder()));
		}

		else if ("isWaitForSubFlow".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getIsWaitForSubFlow()));
		}

		else if ("step".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getStep()));
		}

		else if ("uniteStep".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getUniteStep()));
		}

		else if ("ifcanSplitFlow".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getIfcanSplitFlow()));
		}

		else if ("stepState".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, this.getStepState());
		}

		else if ("issubFlowStep".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getIssubFlowStep()));
		}

		else if ("isfirstSubFlow".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getIsfirstSubFlow()));
		} else if ("splitType".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getSplitType()));
		} else if ("multiJobHandle".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName, new
			// Integer(this.getDocFolder()));
			return this.getKeyValueObject(propertyName, new Integer(this
					.getMultiJobHandle()));
		}

		else if ("isAgr".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getIsAgr());
		}

		else if ("isProcessAsynchronism".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this
					.getIsProcessAsynchronism());
		}

		else if ("curProcessHandleMethod".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this
					.getCurProcessHandleMethod());
		}

		else if ("processGroup".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getProcessGroup());
		} else if ("beforeHandleClass".equals(propertyName)) {
			// return this.getCustomizeTaskRule();
			// return this.getKeyValueObjectName(propertyName,
			// this.getCustomizeTaskRule());
			return this.getKeyValueObject(propertyName, this
					.getBeforeHandleClass());
		}

		else if ("afterHandleClass".equals(propertyName)) {
			// return this.getCustomizeTaskRule();
			// return this.getKeyValueObjectName(propertyName,
			// this.getCustomizeTaskRule());
			return this.getKeyValueObject(propertyName, this
					.getAfterHandleClass());
		}

		else if ("positionurl".equals(propertyName)) {
			// return this.getKeyValueObjectName(propertyName,
			// this.getPositionUrl());
			return this.getKeyValueObject(propertyName, this.getPositionUrl());
		} else if ("accessroles".equals(propertyName)) {
			return this.accessRoles;
		} else if ("accessusers".equals(propertyName)) {
			return this.accessUsers;
		} else if ("projectRoles".equals(propertyName)) {
			return this.projectRoles;
		}
		else if ("huiQianRoles".equals(propertyName)) {
			return this.huiQianRoles;
		}
		

		else if ("processdocfolders".equals(propertyName)) {
			if (this.docTreeRoot.getChildCount() == 0)
				return "未定义";
			else
				return "已定义";
		} else if ("processdocfolderaccess".equals(propertyName)) {
			if (this.docFolderAccess == null
					|| this.docFolderAccess.size() == 0)
				return "未定义";
			else
				return "已定义";
		} else {
			return super.getValue(propertyName);
		}
	}

	public void setValue(String propertyName, Object value) {
		if ("taskrule".equals(propertyName)) {
			this.setTaskRule(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
			this
					.updatePropertyEditAble("customizetaskrule",
							this.taskRule == 1);
			this
					.updatePropertyRequired("customizetaskrule",
							this.taskRule == 1);
		} else if ("viewpageurl".equals(propertyName)) {
			if (value != null) {
				this.viewPageUrl = "" + value;
			}
		} else if ("handlepageurl".equals(propertyName)) {
			if (value != null) {
				this.handlePageUrl = "" + value;
			}
		} else if ("description".equals(propertyName)) {
			this.setDescription(String.valueOf(value));
		} else if ("processCode".equals(propertyName)) {
			this.setProcessCode(String.valueOf(value));
		}

		else if ("displaytext".equals(propertyName)) {
			this.setDisplayText(String.valueOf(value));
		} else if ("routerule".equals(propertyName)) {
			this.setRouteRule(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		} else if ("customizetaskrule".equals(propertyName)) {
			if (value == null)
				this.setCustomizeTaskRule("");
			else
				this.setCustomizeTaskRule((String) ((KeyValueObject) value)
						.getKey());
		} else if ("splitProcessHandle".equals(propertyName)) {
			if (value == null)
				this.setSplitProcessHandle("");
			else
				this.setSplitProcessHandle((String) ((KeyValueObject) value)
						.getKey());
		}

		else if ("completerule".equals(propertyName)) {
			this.setCompleteRule(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
			this.updatePropertyEditAble("customizecompleterule",
					this.completeRule == 1);
			this.updatePropertyRequired("customizecompleterule",
					this.completeRule == 1);
		} else if ("customizecompleterule".equals(propertyName)) {
			if (value == null)
				this.setCustomizeCompleteRule("");
			else
				this.setCustomizeCompleteRule((String) ((KeyValueObject) value)
						.getKey());
		} else if ("docfolder".equals(propertyName)) {
			this.setDocFolder(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		}

		else if ("isWaitForSubFlow".equals(propertyName)) {
			this.setIsWaitForSubFlow(((Integer) ((KeyValueObject) value)
					.getKey()).intValue());
		}

		else if ("step".equals(propertyName)) {
			this.setStep(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		}

		else if ("uniteStep".equals(propertyName)) {
			this.setUniteStep(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		}

		else if ("ifcanSplitFlow".equals(propertyName)) {
			this
					.setIfcanSplitFlow(((Integer) ((KeyValueObject) value)
							.getKey()).intValue());
		}

		else if ("issubFlowStep".equals(propertyName)) {
			this.setIssubFlowStep(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		}

		else if ("stepState".equals(propertyName)) {

			this.setStepState((((KeyValueObject) value).getKey()).toString());

		}

		else if ("isfirstSubFlow".equals(propertyName)) {
			this
					.setIsfirstSubFlow(((Integer) ((KeyValueObject) value)
							.getKey()).intValue());
		}

		else if ("splitType".equals(propertyName)) {
			this.setSplitType(((Integer) ((KeyValueObject) value).getKey())
					.intValue());
		} else if ("multiJobHandle".equals(propertyName)) {
			this
					.setMultiJobHandle(((Integer) ((KeyValueObject) value)
							.getKey()).intValue());
		}

		else if ("isAgr".equals(propertyName)) {
			if (value != null) {
				this.setIsAgr((String) ((KeyValueObject) value).getKey());
			}
		}

		else if ("isProcessAsynchronism".equals(propertyName)) {
			if (value != null) {
				this.setIsProcessAsynchronism((String) ((KeyValueObject) value)
						.getKey());
			}
		}

		else if ("curProcessHandleMethod".equals(propertyName)) {
			if (value != null) {
				this
						.setCurProcessHandleMethod((String) ((KeyValueObject) value)
								.getKey());
			}
		}

		else if ("processGroup".equals(propertyName)) {
			if (value != null) {
				this
						.setProcessGroup((String) ((KeyValueObject) value)
								.getKey());
			}
		} else if ("beforeHandleClass".equals(propertyName)) {
			if (value == null)
				this.setBeforeHandleClass("");
			else
				this.setBeforeHandleClass((String) ((KeyValueObject) value)
						.getKey());
		}

		else if ("afterHandleClass".equals(propertyName)) {
			if (value == null)
				this.setAfterHandleClass("");
			else
				this.setAfterHandleClass((String) ((KeyValueObject) value)
						.getKey());
		}

		else if ("positionurl".equals(propertyName)) {
			if (value != null) {
				this.setPositionUrl((String) ((KeyValueObject) value).getKey());
			}
		} else if ("accessroles".equals(propertyName)) {
			if (value != null && (value instanceof List)) {
				this.accessRoles = (List) value;
			}
		} else if ("accessusers".equals(propertyName)) {
			if (value != null && (value instanceof List)) {
				this.accessUsers = (List) value;
			}
		} else if ("projectRoles".equals(propertyName)) {
			if (value != null && (value instanceof List)) {
				this.projectRoles = (List) value;
			}
		}else if ("huiQianRoles".equals(propertyName)) {
			if (value != null && (value instanceof List)) {
				this.huiQianRoles = (List) value;
			}
		}
		
		

		else if ("processdocfolders".equals(propertyName)) {
			// 不需要设置，在编辑树的时候将直接设置
		} else {
			super.setValue(propertyName, value);
		}
	}

	public String getCustomizeTaskRule() {
		return customizeTaskRule;
	}

	public void setCustomizeTaskRule(String customizeTaskRule) {
		this.customizeTaskRule = customizeTaskRule;
	}

	public String getDescription() {
		return description;
	}

	public String getHandlePageUrl() {
		return handlePageUrl;
	}

	public void setHandlePageUrl(String handlePageUrl) {
		this.handlePageUrl = handlePageUrl;
	}

	public String getViewPageUrl() {
		return viewPageUrl;
	}

	public void setViewPageUrl(String viewPageUrl) {
		this.viewPageUrl = viewPageUrl;
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

	public int getRouteRule() {
		return routeRule;
	}

	public void setRouteRule(int routeRule) {
		this.routeRule = routeRule;
	}

	public int getIssubFlowStep() {
		return issubFlowStep;
	}

	public void setIssubFlowStep(int issubFlowStep) {
		this.issubFlowStep = issubFlowStep;
	}

	public int getTaskRule() {
		return taskRule;
	}

	public void setTaskRule(int taskRule) {
		this.taskRule = taskRule;
	}

	public int getCompleteRule() {
		return completeRule;
	}

	public void setCompleteRule(int completeRule) {
		this.completeRule = completeRule;
	}

	public String getCustomizeCompleteRule() {
		return customizeCompleteRule;
	}

	public void setCustomizeCompleteRule(String customizeCompleteRule) {
		this.customizeCompleteRule = customizeCompleteRule;
	}

	public int getDocFolder() {
		return docFolder;
	}

	public void setDocFolder(int docFolder) {
		this.docFolder = docFolder;
	}

	public String getPositionUrl() {
		return positionUrl;
	}

	public void setPositionUrl(String positionUrl) {
		this.positionUrl = positionUrl;
	}

	public List getAccessRoles() {
		return accessRoles;
	}

	public void setAccessRoles(List accessRoles) {
		this.accessRoles = accessRoles;
	}

	public List getProjectRoles() {
		return projectRoles;
	}

	public void setProjectRoles(List projectRoles) {
		this.projectRoles = projectRoles;
	}

	public List getHuiQianRoles() {
		return huiQianRoles;
	}

	public void setHuiQianRoles(List huiQianRoles) {
		this.huiQianRoles = huiQianRoles;
	}

	public List getAccessUsers() {
		return accessUsers;
	}

	public void setAccessUsers(List accessUsers) {
		this.accessUsers = accessUsers;
	}

	public DefaultMutableTreeNode getDocTreeRoot() {
		return docTreeRoot;
	}

	public void setDocTreeRoot(DefaultMutableTreeNode docTreeRoot) {
		this.docTreeRoot = docTreeRoot;
	}

	public List getDocFolderAccess() {
		return docFolderAccess;
	}

	public void setDocFolderAccess(List docFolderAccess) {
		this.docFolderAccess = docFolderAccess;
	}

	public void setName(String name) {
		rootFolder.setFolderName(name);
		rootFolder.setSubProjectFlag(0);
		super.setName(name);
	}

	/**
	 * 返回一个获取目录结构的属性适配器
	 */
	public CustomizePropertyAdapter getCustomizeAdapter(String propertyName) {
		if ("processdocfolders".equals(propertyName)) {
			return new ProcessDocPropertyAdapter(this);
		} else if ("processdocfolderaccess".equals(propertyName)) {
			return new ProcessFolderAccessPropertyAdapter(this);
		} else {
			return super.getCustomizeAdapter(propertyName);
		}
	}

	public Object[] getListValues(String propertyName) {
		if ("accessroles".equals(propertyName)) {
			return new RolesAdapter().getKeyValueObjects();
		} else if ("accessusers".equals(propertyName)) {
			return new UsersAdapter().getKeyValueObjects();
		} else if ("projectRoles".equals(propertyName)) {

			return new ProjectRoles().getKeyValueObjects();

		}else if ("huiQianRoles".equals(propertyName)) {

			return new ProjectRoles().getKeyValueObjects();

		}
		
		

		else {
			return super.getListValues(propertyName);
		}
	}

	class SplitProcessHandle implements KeyValueObjectAdapter, Serializable {
		
		/**
		 * 获取自定义任务分解规则处理信息
		 */
		public KeyValueObject[] getKeyValueObjects() {
			if (true)
			{
				
				return new KeyValueObject[0]; 
			}
			ResourceConfig cfg = ResourceConfig
					.getConfig("floweditor.properties");
			String[] spliters = cfg.getStringGroupConfig("splitProcessHandle");
			List tmp = new ArrayList();
			for (int i = 0; i < spliters.length; i++) {
				if (spliters[i] == null || spliters[i].trim().length() == 0)
					continue;

				try {
					Class clz = Class.forName(spliters[i].trim());
					Object obj = clz.newInstance();
					if (obj instanceof IEmployeeTypesSpliter) {
						IEmployeeTypesSpliter spliter = (IEmployeeTypesSpliter) obj;

						KeyValueObject kvo = new KeyValueObject(spliters[i]
								.trim(), spliter.getSpliterName());
						tmp.add(kvo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return (KeyValueObject[]) tmp
					.toArray(new KeyValueObject[tmp.size()]);
		}
	}

	class JobSplitersAdapter implements KeyValueObjectAdapter, Serializable {
		/**
		 * 获取自定义任务分解规则处理信息
		 */
		public KeyValueObject[] getKeyValueObjects() {
			if (true)
			{
				
				return new KeyValueObject[0]; 
			}
			
			ResourceConfig cfg = ResourceConfig
					.getConfig("floweditor.properties");
			String[] spliters = cfg.getStringGroupConfig("jobspliter");
			List tmp = new ArrayList();
			for (int i = 0; i < spliters.length; i++) {
				if (spliters[i] == null || spliters[i].trim().length() == 0)
					continue;

				try {
					Class clz = Class.forName(spliters[i].trim());
					Object obj = clz.newInstance();
					if (obj instanceof ICustomizeJobSpliter) {
						ICustomizeJobSpliter spliter = (ICustomizeJobSpliter) obj;
						KeyValueObject kvo = new KeyValueObject(spliters[i]
								.trim(), spliter.getSpliterName());
						tmp.add(kvo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return (KeyValueObject[]) tmp
					.toArray(new KeyValueObject[tmp.size()]);
		}
	}

	class CompleteCheckerAdapter implements KeyValueObjectAdapter, Serializable {
		/**
		 * 获取自定义步骤完成规则检查器
		 */
		public KeyValueObject[] getKeyValueObjects() {
			if (true)
			{
				return new KeyValueObject[0];
			}
			ResourceConfig cfg = ResourceConfig
					.getConfig("floweditor.properties");
			String[] checkers = cfg.getStringGroupConfig("completecheckers");
			List tmp = new ArrayList();
			for (int i = 0; i < checkers.length; i++) {
				if (checkers[i] == null || checkers[i].trim().length() == 0)
					continue;

				try {
					Class clz = Class.forName(checkers[i].trim());
					Object obj = clz.newInstance();
					if (obj instanceof ICustomizeProcessCompleteChecker) {
						ICustomizeProcessCompleteChecker checker = (ICustomizeProcessCompleteChecker) obj;
						KeyValueObject kvo = new KeyValueObject(checkers[i]
								.trim(), checker.getCheckerName());
						tmp.add(kvo);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			return (KeyValueObject[]) tmp
					.toArray(new KeyValueObject[tmp.size()]);
		}
	}

	public int getIfcanSplitFlow() {
		return ifcanSplitFlow;
	}

	public void setIfcanSplitFlow(int ifcanSplitFlow) {
		this.ifcanSplitFlow = ifcanSplitFlow;
	}

	public String getStepState() {
		return stepState;
	}

	public void setStepState(String stepState) {
		this.stepState = stepState;
	}

	public int getIsfirstSubFlow() {
		return isfirstSubFlow;
	}

	public void setIsfirstSubFlow(int isfirstSubFlow) {
		this.isfirstSubFlow = isfirstSubFlow;
	}

	public int getIsWaitForSubFlow() {
		return isWaitForSubFlow;
	}

	public void setIsWaitForSubFlow(int isWaitForSubFlow) {
		this.isWaitForSubFlow = isWaitForSubFlow;
	}

	public long getParentProcessId() {
		return parentProcessId;
	}

	public void setParentProcessId(long parentProcessId) {
		this.parentProcessId = parentProcessId;
	}

	public String getSplitProcessHandle() {
		return splitProcessHandle;
	}

	public void setSplitProcessHandle(String splitProcessHandle) {
		this.splitProcessHandle = splitProcessHandle;
	}

	public String getProcessState() {
		return processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public ProcessDocFolderInfo getRootFolder() {
		return rootFolder;
	}

	public void setRootFolder(ProcessDocFolderInfo rootFolder) {
		this.rootFolder = rootFolder;
	}

	public int getSplitType() {
		return splitType;
	}

	public void setSplitType(int splitType) {
		this.splitType = splitType;
	}

	public int getInnerStep() {
		return innerStep;
	}

	public void setInnerStep(int innerStep) {
		this.innerStep = innerStep;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getUniteStep() {
		return uniteStep;
	}

	public void setUniteStep(int uniteStep) {
		this.uniteStep = uniteStep;
	}

	public int getMultiJobHandle() {
		return multiJobHandle;
	}

	public void setMultiJobHandle(int multiJobHandle) {
		this.multiJobHandle = multiJobHandle;
	}

	public String getAfterHandleClass() {
		return afterHandleClass;
	}

	public void setAfterHandleClass(String afterHandleClass) {
		this.afterHandleClass = afterHandleClass;
	}

	public String getBeforeHandleClass() {
		return beforeHandleClass;
	}

	public void setBeforeHandleClass(String beforeHandleClass) {
		this.beforeHandleClass = beforeHandleClass;
	}

	public String getCurProcessHandleMethod() {
		return curProcessHandleMethod;
	}

	public void setCurProcessHandleMethod(String curProcessHandleMethod) {
		this.curProcessHandleMethod = curProcessHandleMethod;
	}

	public String getIsAgr() {
		return isAgr;
	}

	public void setIsAgr(String isAgr) {
		this.isAgr = isAgr;
	}

	public String getIsProcessAsynchronism() {
		return isProcessAsynchronism;
	}

	public void setIsProcessAsynchronism(String isProcessAsynchronism) {
		this.isProcessAsynchronism = isProcessAsynchronism;
	}
	
	public String getProcessCode() {
		return processCode;
	}
	
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	
	public String getProcessGroup() {
		return processGroup;
	}
	
	public void setProcessGroup(String processGroup) {
		this.processGroup = processGroup;
	}
}

class AfterHandleClass implements KeyValueObjectAdapter, Serializable {
	public KeyValueObject[] getKeyValueObjects() {
		//ResourceConfig cfg = ResourceConfig.getConfig("floweditor.properties");
		String[] spliters =ClassNameGetter.getAfterHandleClass();
		List tmp = new ArrayList();
		for (int i = 0; i < spliters.length; i++) {
			if (spliters[i] == null || spliters[i].trim().length() == 0)
				continue;
			
			try {
				Class clz = Class.forName(spliters[i].trim());
				Object obj = clz.newInstance();
				if (obj instanceof IAfterHandleClass) {
					IAfterHandleClass spliter = (IAfterHandleClass) obj;
					KeyValueObject kvo = new KeyValueObject(spliters[i].trim(), spliter.getHandleClassName());
					tmp.add(kvo);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return (KeyValueObject[]) tmp.toArray(new KeyValueObject[tmp.size()]);
	}
}

class BeforeHandleClass implements KeyValueObjectAdapter, Serializable {
	/**
	 * 获取自定义任务分解规则处理信息 
	 */
	public KeyValueObject[] getKeyValueObjects() {
		//ResourceConfig cfg = ResourceConfig.getConfig("floweditor.properties");
		String[] spliters = ClassNameGetter.getBeforeHandleClass();
		
		List tmp = new ArrayList();
		for (int i = 0; i < spliters.length; i++) {
			if (spliters[i] == null || spliters[i].trim().length() == 0)
				continue;
			
			try {
				Class clz = Class.forName(spliters[i].trim());
				Object obj = clz.newInstance();
				if (obj instanceof IBeforeHandleClass) {
					IBeforeHandleClass spliter = (IBeforeHandleClass) obj;
					KeyValueObject kvo = new KeyValueObject(spliters[i].trim(), spliter.getHandleClassName());
					tmp.add(kvo);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return (KeyValueObject[]) tmp.toArray(new KeyValueObject[tmp.size()]);
	}
}
