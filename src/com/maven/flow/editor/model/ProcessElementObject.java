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
 * ���õĲ���Ԫ�ض���.
 * 
 * 
 */

public abstract class ProcessElementObject extends FlowElementObject {

	public static final long serialVersionUID = 1636105772671125840L;

	/**
	 * ���̻��ڵ�����
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
			rootFolder);// �ĵ�Ŀ¼�ṹ�ĸ��ڵ㣬Ŀ¼�������벽������һ��

	private long parentProcessId;// ��������.

	private int isWaitForSubFlow;// �Ƿ�ȴ�ȫ�����������.

	private int ifcanSplitFlow;// �Ƿ���Է�ɢ����.

	private int isfirstSubFlow;// �Ƿ������̵ĳ��ڵ�..

	private String processState;// ����״̬.

	private int splitType;// ��ɢ��ʽ[��,1,���㷢ɢ 2,���෢ɢ]

	private String splitProcessHandle;// ��ɢ������.

	private int issubFlowStep;// �Ƿ��������̵Ĳ���.

	private int innerStep;// �Ƿ��������̵Ĳ���.

	private int step;// ����[���������].

	private int uniteStep;// ���ϲ�����.[�����������]

	private String stepState;// ����״̬

	// private String projectRole;
	private int multiJobHandle;// ��������ʽ

	private String isProcessAsynchronism = "0";// �Ƿ����첽���衣

	private String curProcessHandleMethod = "2";// �˵İڷŷ�ʽ��
												// 1:��ͬ����һ�˺�һ������,��������Ա��ɢ��
												// 2:�����µ�ȫ���˼���ͬһ����:��������Ա����һ��

	private String processGroup;// ������.

	private String beforeHandleClass;// ǰ�ô�����;

	private String afterHandleClass;// ���ô�����;

	private String processCode;// �������

	private String isAgr = "0";// �Ƿ�ԡ���ͬ�⡱�ṩ֧��:::����ͬ���ṩ֧�֣���ζ�ſ�����ͬһ�����������ת��

	private String viewPageUrl = "";// �鿴ҳ��

	private String handlePageUrl = "";// ����ҳ��
	
	private List docFolderAccess = new ArrayList();// �����ĵ�Ŀ¼�ṹȨ��

	{
		// Ϊ���������Ա���Ĭ�ϵ�Ȩ��
		ProcessDocFolderAccessInfo acc = new ProcessDocFolderAccessInfo();
		acc.setFolder(rootFolder);
		acc.setAddFolderFlag(1);
		acc.setDeleteFlag(1);
		acc.setUpdateFolderFlag(1);
		acc.setViewFlag(1);
		acc.setOperateFlag(1);
		docFolderAccess.add(acc);

		this.updatePropertyDescription("name", "��������");
		this.setName("���̲���");

		// �������ֵ
		this.addProperty("description", "��������", FlowProperty.STRING, 0, 0);
		this.addProperty("displaytext", "������ʾ����", FlowProperty.STRING, 0, 1);
		this.addProperty("routerule", "·����������", FlowProperty.OPTION, false, 1,0);
		
		this.addProperty("processCode", "�������", FlowProperty.STRING, 0, 0);
		
		this.addProperty("taskrule", "����ֽ����", FlowProperty.OPTION, 2, 0);
		this.addProperty("customizetaskrule", "�Զ�������ֽ����", FlowProperty.OPTION,
				false, 2, 1);
		this.addProperty("completerule", "��ɲ������", FlowProperty.OPTION, 2, 2);
		this.addProperty("customizecompleterule", "�Զ�����ɹ���",
				FlowProperty.OPTION, false, 2, 3);
		// this.addProperty("positionurl", "����ҳ��·��", FlowProperty.OPTION);
		this.addProperty("accessroles", "����Ȩ�޽�ɫ", FlowProperty.LIST, 3, 0);
		this.addProperty("accessusers", "����Ȩ���û�", FlowProperty.LIST, 3, 1);

		this.addProperty("projectRoles", "��ĿȨ���û�", FlowProperty.LIST, 3, 1);
		
		this.addProperty("huiQianRoles", "��ǩ��", FlowProperty.LIST, 3, 1);
		
		// ����
		// this.addProperty("processdocfolders", "�����ĵ��ṹ",
		// FlowProperty.CUSTOMIZE,4, 0);
		// this.addProperty("processdocfolderaccess",
		// "�����ĵ�Ȩ��",FlowProperty.CUSTOMIZE, 4, 1);
		// this.addProperty("docfolder", "�����ĵ��������", FlowProperty.OPTION, 4, 2);

		this.addProperty("ifcanSplitFlow", "�Ƿ���Է�ɢ������", FlowProperty.OPTION, 5,
				0);

		this.addProperty("issubFlowStep", "�Ƿ��������̵Ĳ���", FlowProperty.OPTION, 5,
				0);

		this.addProperty("isfirstSubFlow", "�Ƿ��������̵ĳ��ڵ�", FlowProperty.OPTION,
				5, 0);

		this.addProperty("step", "�����̼���", FlowProperty.OPTION, 5, 0);

		this.addProperty("uniteStep", "���ϲ��ļ���", FlowProperty.OPTION, 5, 0);

		this.addProperty("isWaitForSubFlow", "�Ƿ������̹��ڵ�", FlowProperty.OPTION,
				5, 0);

		this.addProperty("splitType", "��ɢ��ʽ", FlowProperty.OPTION, 5, 0);

		this
				.addProperty("multiJobHandle", "��������ʽ", FlowProperty.OPTION,
						5, 0);
		// this.addProperty("stepState", "����״̬", FlowProperty.OPTION, 5, 0);
		this.addProperty("splitProcessHandle", "��ɢ����ʽ", FlowProperty.OPTION,
				5, 0, true);

		this.addProperty("curProcessHandleMethod", "�������˵Ĵ���ʽ",
				FlowProperty.OPTION, 5, 0, true);

		this.addProperty("isProcessAsynchronism", "�Ƿ����첽����",
				FlowProperty.OPTION, 5, 0, true);

		this.addProperty("isAgr", "�Ƿ��[��ͬ��]�ṩ֧��", FlowProperty.OPTION, 5, 0,
				true);

		this.addProperty("processGroup", "������", FlowProperty.OPTION, 5, 0,
						true);
		
		// this.addProperty("isAutoFilter", "��һ�����Ƿ��Զ�����",
		// FlowProperty.OPTION,5,0,true);

		// this.addProperty("filterClass", "���������",
		// FlowProperty.OPTION,5,0,true);

		// this.addProperty("speicTriggerClass", "���ⲽ�败����",
		// FlowProperty.OPTION,5,0,true);

		this.addProperty("beforeHandleClass", "ǰ�ô�����", FlowProperty.OPTION, 5,
				0, true);

		this.addProperty("afterHandleClass", "���ô�����", FlowProperty.OPTION, 5,
				0, true);

		// ����б�ֵ
		this.addListValue("taskrule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "Ĭ�Ϲ���"),
				new KeyValueObject(new Integer(1), "�Զ������") });

		this.addListValue("routerule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "ϵͳ����"),
				new KeyValueObject(new Integer(1), "�ֶ���ת") });
		this.addListValue("completerule", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "����Ҫ���"),
				new KeyValueObject(new Integer(1), "��Ҫ���") });
		this.addListValue("docfolder", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "Ĭ�Ϲ���"),
				new KeyValueObject(new Integer(1), "�Զ������") });
		// this.addListValueAdapter("positionurl", new PagesAdapter());
		this.addListValueAdapter("customizetaskrule", new JobSplitersAdapter());// ��ȡ�ļ��µ��Զ��巽ʽ
		this
				.addListValueAdapter("splitProcessHandle",
						new SplitProcessHandle());// ��ȡ�����ļ��ķ�ɢ��ʽ

		this.addListValueAdapter("customizecompleterule",
				new CompleteCheckerAdapter());

		this.addListValue("ifcanSplitFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "������"),
				new KeyValueObject(new Integer(1), "����") });

		this.addListValue("issubFlowStep", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "����"),
				new KeyValueObject(new Integer(1), "��") });

		this.addListValue("isfirstSubFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "����"),
				new KeyValueObject(new Integer(1), "��") });

		this.addListValue("stepState", new KeyValueObject[] {
				new KeyValueObject(new String("1"), "��Ŀ������"),
				new KeyValueObject(new String("2"), "����"),
				new KeyValueObject(new String("3"), "����"),
				new KeyValueObject(new String("4"), "�󶨸�"),
				new KeyValueObject(new String("5"), "�����") });

		this.addListValue("isWaitForSubFlow", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "����"),
				new KeyValueObject(new Integer(1), "��") });

		this.addListValue("step", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "�޼���"),
				new KeyValueObject(new Integer(1), "��һ��"),
				new KeyValueObject(new Integer(2), "�ڶ���"),
				new KeyValueObject(new Integer(3), "������") });

		this.addListValue("uniteStep", new KeyValueObject[] {
				new KeyValueObject(new Integer(0), "���úϲ�"),
				new KeyValueObject(new Integer(1), "��һ��"),
				new KeyValueObject(new Integer(2), "�ڶ���"),
				new KeyValueObject(new Integer(3), "������"),
				new KeyValueObject(new Integer(4), "���ļ�") });

		this.addListValue("splitType", new KeyValueObject[] {
				new KeyValueObject(new Integer(-1), "�޷�ɢ"),
				new KeyValueObject(new Integer(0), "���㷢ɢ"),
				new KeyValueObject(new Integer(1), "���෢ɢ") });

		this.addListValue("multiJobHandle", new KeyValueObject[] {

		new KeyValueObject(new Integer(1), "����ȫ��ͨ��"),
				new KeyValueObject(new Integer(0), "����֮һͨ��")

		});

		this.addListValue("curProcessHandleMethod", new KeyValueObject[] {
				new KeyValueObject("1", "��������Ա��ɢ"),
				new KeyValueObject("2", "��������Ա����һ��") });

		this.addListValue("isProcessAsynchronism", new KeyValueObject[] {
				new KeyValueObject("0", "��"), new KeyValueObject("1", "��") });

		this.addListValue("isAutoFilter", new KeyValueObject[] {
				new KeyValueObject("0", "��"), new KeyValueObject("1", "��") });

		this.addListValue("isAgr", new KeyValueObject[] {
				new KeyValueObject("0", "���ṩ֧��"),
				new KeyValueObject("1", "�ṩ֧��") });

		this.addListValue("processGroup", new KeyValueObject[] {
				new KeyValueObject("0", "��������"),
				new KeyValueObject("1", "��һ��"), new KeyValueObject("2", "�ڶ���"),
				new KeyValueObject("3", "������"), new KeyValueObject("4", "������"),
				new KeyValueObject("5", "������"), new KeyValueObject("6", "������"),
				new KeyValueObject("7", "������"), new KeyValueObject("8", "�ڰ���"),
				new KeyValueObject("9", "�ھ���") });

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
				return "δ����";
			else
				return "�Ѷ���";
		} else if ("processdocfolderaccess".equals(propertyName)) {
			if (this.docFolderAccess == null
					|| this.docFolderAccess.size() == 0)
				return "δ����";
			else
				return "�Ѷ���";
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
			// ����Ҫ���ã��ڱ༭����ʱ��ֱ������
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
	 * ����һ����ȡĿ¼�ṹ������������
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
		 * ��ȡ�Զ�������ֽ��������Ϣ
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
		 * ��ȡ�Զ�������ֽ��������Ϣ
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
		 * ��ȡ�Զ��岽����ɹ�������
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
	 * ��ȡ�Զ�������ֽ��������Ϣ 
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
