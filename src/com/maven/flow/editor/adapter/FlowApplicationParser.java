/**
 * @(#)FlowApplicationParser.java 2007-6-7
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 *
 */
package com.maven.flow.editor.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;

import com.maven.flow.editor.model.ApprovalProcessObject;
import com.maven.flow.editor.model.EndElement;
import com.maven.flow.editor.model.FlowEdgeObject;
import com.maven.flow.editor.model.FlowElementObject;
import com.maven.flow.editor.model.GraphElement;
import com.maven.flow.editor.model.HandleProcessObject;
import com.maven.flow.editor.model.KeyValueObject;
import com.maven.flow.editor.model.ProcessElementObject;
import com.maven.flow.editor.model.StartElement;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.hibernate.dao.TblAppRoute;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.hibernate.dao.TblProcessAccess;

/**
 * 流程转换器，负责将流程图转换成引擎对应的流程信息
 * 
 * @author kinz
 * @version 1.0 2007-6-7
 * @since JDK1.5
 */

public class FlowApplicationParser {

	// private Log log = LogFactory.getLog(this.getClass());//日志记录

	private Map docFoldersMap = new HashMap();// 存储所有的文档目录结构：docInfo=docFolder

	private Map docFolderAccessMap = new HashMap();//

	public TblApplication parse(FlowGraph graph) {
		TblApplication application = new TblApplication();

		// 获取流程的基本信息
		this.getApplicationInfo(graph, application);
		// 获取流程监控授权信息
		this.getMonitorAccessInfo(graph, application);
		// 获取流程路径信息
		this.getProcessAndRouteInfo(graph, application);
		// 更新步骤文档目录权限的对应目录信息
		// this.updateProcessDocFolderAccessInfo(application);

		return application;
	}

	/**
	 * 转换流程基本信息
	 * 
	 * @param graph
	 * @param application
	 */
	private void getApplicationInfo(FlowGraph graph, TblApplication application) {
		GraphElement info = graph.getGraphInfo();

		application.setAppId(new Integer("" + info.getAppId()));// 流程ID
		application.setAppName(info.getName());// 流程名称
		application.setAppDesc(info.getDescription());// 流程描述
		application.setAppMonitorRule(new Integer(info.getMonitorRule()));// 流程监控授权规则
		application.setAppCreateTime(new Date());// 创建时间
		application.setAppReleaseTime(new Date());// 发布时间
		
	}

	/**
	 * 获取流程监控权限信息
	 * 
	 * @param graph
	 * @param application
	 */
	private void getMonitorAccessInfo(FlowGraph graph,
			TblApplication application) {
		GraphElement info = graph.getGraphInfo();
		List monitorAccesses = new ArrayList();

		List roles = info.getMonitorAccessRoles();
		List users = info.getMonitorAccessUsers();
		/*
		 * // 先处理角色 for (Iterator it = roles.iterator(); it.hasNext();) {
		 * ApplicationMonitorAccess access = new ApplicationMonitorAccess();
		 * access.setRoleId(((Long) ((KeyValueObject) it.next()).getKey())
		 * .longValue());
		 * access.setRoleType(ApplicationMonitorAccess.ROLE_TYPE_ROLE);
		 * 
		 * monitorAccesses.add(access); } // 再处理用户 for (Iterator it =
		 * users.iterator(); it.hasNext();) { ApplicationMonitorAccess access =
		 * new ApplicationMonitorAccess(); access.setRoleId(((Long)
		 * ((KeyValueObject) it.next()).getKey()) .longValue());
		 * access.setRoleType(ApplicationMonitorAccess.ROLE_TYPE_USER);
		 * 
		 * monitorAccesses.add(access); }
		 * 
		 * application.setMonitorAccesses(monitorAccesses);
		 */
	}

	/**
	 * 获取步骤信息 需要处理： 1、步骤基本信息 2、步骤权限信息 3、步骤文档结构信息
	 * 
	 * @param graph
	 * @param application
	 */
	private TblProcess getProcessInfo(FlowElementObject feo) {
		if (feo == null || !(feo instanceof ProcessElementObject))
			return null;

		ProcessElementObject info = (ProcessElementObject) feo;

		TblProcess process = null;
		// 先判断步骤类型，并创建不同的实体
		if (info instanceof StartElement) {
			// 开始类型的步骤
			process = this.getStartProcessInfo((StartElement) info);
		} else if (info instanceof ApprovalProcessObject) {
			process = this.getApprovalProcessInfo((ApprovalProcessObject) info);
		} else if (info instanceof HandleProcessObject) {
			process = this.getHandleProcessInfo((HandleProcessObject) info);
		} else if (info instanceof EndElement) {
			process = new TblProcess();
			process.setProcessType(new Integer(TblProcess.TYPE_END));
		} else {
			process = new TblProcess();
		}

		// 处理基本信息
		process.setProcessCompleteCheckRule(new Integer(info.getCompleteRule()));
		process
				.setProcessCompleteRuleCustomize(info
						.getCustomizeCompleteRule());
		process.setProcessDesc(info.getDescription());
		process.setProcessDisplayText(info.getDisplayText());
		process.setProcessDocFolderAccessRule(new Integer(info.getDocFolder()));
		process.setProcessJobSplitCustomize(info.getCustomizeTaskRule());
		process.setProcessJobSplitRule(new Integer(info.getTaskRule()));
		process.setProcessName(info.getName());
		process.setProcessPositionInfo(info.getPositionUrl());
		process.setProcessRouteRule(new Integer(info.getRouteRule()));

		// 2008-08-13新加入的内容:
		process.setIsfirstSubFlow(new Integer(info.getIsfirstSubFlow()));
		process.setIsWaitForSubFlow(new Integer(info.getIsWaitForSubFlow()));
		process.setIfcanSplitFlow(new Integer(info.getIfcanSplitFlow()));
		process.setProcessState(info.getProcessState());

		// 2008-08-15新加入的内容.
		process.setSplitType(new Integer(info.getSplitType()));
		process.setSplitProcessHandle(info.getSplitProcessHandle());

		// 2008-08-20新加入的内容
		process.setIsSubFlowStep(new Integer(info.getIssubFlowStep()));

		// 2008-08-21新加入的内容.
		process.setInnerStep(new Integer(info.getInnerStep()));

		// 2008-08-22新加入的内容.
		process.setStep(new Integer(info.getStep()));
		process.setUniteStep(new Integer(info.getUniteStep()));

		// 2008-09-19
		process.setStepState(info.getStepState());

		// 处理权限信息
		this.getProcessAccessInfo(info, process);
		// 处理文档目录结构信息
		// this.getProcessDocFolders(info, process);

		// 处理步骤目录权限
		/*
		 * List folderAccessInfos = info.getDocFolderAccess(); List folderAccess =
		 * new ArrayList(); for (Iterator it = folderAccessInfos.iterator();
		 * it.hasNext();) { ProcessDocFolderAccessInfo pdaf =
		 * (ProcessDocFolderAccessInfo) it .next(); ProcessDocFolderAccess a =
		 * new ProcessDocFolderAccess();
		 * a.setDeleteFolderFlag(pdaf.getDeleteFlag());
		 * a.setViewDocFlag(pdaf.getViewFlag());
		 * a.setOperateDocFlag(pdaf.getOperateFlag());
		 * a.setAddFolderFlag(pdaf.getAddFolderFlag());
		 * a.setUpdateFolderFlag(pdaf.getUpdateFolderFlag());
		 * 
		 * folderAccess.add(a);
		 * 
		 * this.docFolderAccessMap.put(a, pdaf.getFolder()); }
		 * process.setDocFolderAccess(folderAccess);
		 */
		return process;
	}

	/**
	 * 获取开始类的步骤信息
	 * 
	 * @param info
	 * @return
	 */
	private TblProcess getStartProcessInfo(StartElement info) {
		TblProcess p = new TblProcess();

		p.setProcessType(new Integer(TblProcess.TYPE_START));

		p.setHandleURL(info.getHandlePageUrl());

		return p;
	}

	/**
	 * 获取审批类的步骤信息
	 * 
	 * @param info
	 * @return
	 */
	private TblProcess getApprovalProcessInfo(ApprovalProcessObject info) {
		TblProcess p = new TblProcess();

		p.setProcessType(new Integer(TblProcess.TYPE_APPROVAL));

		p.setViewURL(info.getViewPageUrl());
		p.setHandleURL(info.getHandlePageUrl());
		p.setMultiJobHandle(new Integer(""+info.getMultiJobHandle()));
		return p;
	}

	/**
	 * 获取操作类的步骤信息
	 * 
	 * @param info
	 * @return
	 */
	private TblProcess getHandleProcessInfo(HandleProcessObject info) {
		TblProcess p = new TblProcess();

		p.setProcessType(new Integer(TblProcess.TYPE_HANDLE));

		p.setViewURL(info.getViewPageUrl());
		p.setHandleURL(info.getHandlePageUrl());
		p.setMultiJobHandle(new Integer(""+info.getMultiJobHandle()));
		return p;
	}

	/**
	 * 获取路径信息
	 * 
	 * @param graph
	 * @param application
	 */
	private void getProcessAndRouteInfo(FlowGraph graph,
			TblApplication application) {
		HashMap processMap = new HashMap();

		// 先处理所有的步骤信息
		Object[] cells = graph.getGraphLayoutCache().getCells(false, true,
				false, false);
		List processes = new ArrayList();
		for (int i = 0; i < cells.length; i++) {
			// 只处理DefaultGraphCell对象
			if (!(cells[i] instanceof DefaultGraphCell))
				continue;

			DefaultGraphCell cell = (DefaultGraphCell) cells[i];
			Object o = cell.getUserObject();
			// 只处理ProcessElementObject
			if (!(o instanceof FlowElementObject))
				continue;

			FlowElementObject feo = (FlowElementObject) o;

			TblProcess p = this.getProcessInfo(feo);

			processes.add(p);
			// 添加到映射
			processMap.put(feo, p);
		}

		// 获取所有的路径信息
		Object[] edges = graph.getGraphLayoutCache().getCells(false, false,
				false, true);

		List routes = new ArrayList();

		for (int i = 0; i < edges.length; i++) {
			// 只处理Edge对象
			if (!(edges[i] instanceof DefaultEdge))
				continue;
			DefaultEdge e = (DefaultEdge) edges[i];

			// 只处理FlowEdgeObject
			Object o = e.getUserObject();
			if (!(o instanceof FlowEdgeObject))
				continue;

			FlowEdgeObject feo = (FlowEdgeObject) o;

			// 开始设置相关信息
			TblAppRoute r = new TblAppRoute();

			r.setRouteDesc(feo.getDescription());
			r.setRouteName(feo.getName());
			r.setRouteDisplayText(feo.getDisplayText());
			r.setRoutePositionInfo(feo.getPositionUrl());
			r.setSubFlowPath(new Integer(feo.getSubFlowPath()));

			// 处理开始步骤
			if (processMap.containsKey(feo.getBeginProcess())) {
				r.setBeginProcess((TblProcess) processMap.get(feo
						.getBeginProcess()));
			} else {
				// Process p = this.getProcessInfo(feo.getBeginProcess());
				// r.setBeginProcess(p);
				// processMap.put(feo.getBeginProcess(), p);
			}

			// 处理结束步骤
			if (processMap.containsKey(feo.getEndProcess())) {
				r.setEndProcess((TblProcess) processMap
						.get(feo.getEndProcess()));
			} else {
				// Process p = this.getProcessInfo(feo.getEndProcess());
				// r.setEndProcess(p);
				// processMap.put(feo.getEndProcess(), p);
			}

			routes.add(r);
		}

		application.setAppRoutes(routes);
		application.setProcesses(processes);

		processMap.clear();
	}

	/**
	 * 获取步骤权限信息
	 * 
	 * @param info
	 * @param process
	 */
	private void getProcessAccessInfo(ProcessElementObject info,
			TblProcess process) {
		List roles = info.getAccessRoles();// 角色权限
		List users = info.getAccessUsers();// 员工权限

		List pAccess = new ArrayList();

		// 先处理角色
		for (Iterator it = roles.iterator(); it.hasNext();) {
			TblProcessAccess access = new TblProcessAccess();
			Long ll=(((Long) ((KeyValueObject) it.next())
					.getKey()));
			access
					.setRoleId(new Integer(ll.intValue()));
			access.setRoleType(new Integer(TblProcessAccess.ROLE_TYPE_ROLE));

			pAccess.add(access);
		}

		// 再处理用户
		for (Iterator it = users.iterator(); it.hasNext();) {
			TblProcessAccess access = new TblProcessAccess();
			Integer i=new Integer(Long.parseLong(((KeyValueObject) it.next()).getKey()+"")+"");
			access.setRoleId(i);
			access.setRoleType(new Integer(TblProcessAccess.ROLE_TYPE_USER));

			pAccess.add(access);
		}

		process.setProcessAccesses(pAccess);
	}

}
