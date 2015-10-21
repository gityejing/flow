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
 * ����ת��������������ͼת���������Ӧ��������Ϣ
 * 
 * @author kinz
 * @version 1.0 2007-6-7
 * @since JDK1.5
 */

public class FlowApplicationParser {

	// private Log log = LogFactory.getLog(this.getClass());//��־��¼

	private Map docFoldersMap = new HashMap();// �洢���е��ĵ�Ŀ¼�ṹ��docInfo=docFolder

	private Map docFolderAccessMap = new HashMap();//

	public TblApplication parse(FlowGraph graph) {
		TblApplication application = new TblApplication();

		// ��ȡ���̵Ļ�����Ϣ
		this.getApplicationInfo(graph, application);
		// ��ȡ���̼����Ȩ��Ϣ
		this.getMonitorAccessInfo(graph, application);
		// ��ȡ����·����Ϣ
		this.getProcessAndRouteInfo(graph, application);
		// ���²����ĵ�Ŀ¼Ȩ�޵Ķ�ӦĿ¼��Ϣ
		// this.updateProcessDocFolderAccessInfo(application);

		return application;
	}

	/**
	 * ת�����̻�����Ϣ
	 * 
	 * @param graph
	 * @param application
	 */
	private void getApplicationInfo(FlowGraph graph, TblApplication application) {
		GraphElement info = graph.getGraphInfo();

		application.setAppId(new Integer("" + info.getAppId()));// ����ID
		application.setAppName(info.getName());// ��������
		application.setAppDesc(info.getDescription());// ��������
		application.setAppMonitorRule(new Integer(info.getMonitorRule()));// ���̼����Ȩ����
		application.setAppCreateTime(new Date());// ����ʱ��
		application.setAppReleaseTime(new Date());// ����ʱ��
		
	}

	/**
	 * ��ȡ���̼��Ȩ����Ϣ
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
		 * // �ȴ����ɫ for (Iterator it = roles.iterator(); it.hasNext();) {
		 * ApplicationMonitorAccess access = new ApplicationMonitorAccess();
		 * access.setRoleId(((Long) ((KeyValueObject) it.next()).getKey())
		 * .longValue());
		 * access.setRoleType(ApplicationMonitorAccess.ROLE_TYPE_ROLE);
		 * 
		 * monitorAccesses.add(access); } // �ٴ����û� for (Iterator it =
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
	 * ��ȡ������Ϣ ��Ҫ���� 1�����������Ϣ 2������Ȩ����Ϣ 3�������ĵ��ṹ��Ϣ
	 * 
	 * @param graph
	 * @param application
	 */
	private TblProcess getProcessInfo(FlowElementObject feo) {
		if (feo == null || !(feo instanceof ProcessElementObject))
			return null;

		ProcessElementObject info = (ProcessElementObject) feo;

		TblProcess process = null;
		// ���жϲ������ͣ���������ͬ��ʵ��
		if (info instanceof StartElement) {
			// ��ʼ���͵Ĳ���
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

		// ���������Ϣ
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

		// 2008-08-13�¼��������:
		process.setIsfirstSubFlow(new Integer(info.getIsfirstSubFlow()));
		process.setIsWaitForSubFlow(new Integer(info.getIsWaitForSubFlow()));
		process.setIfcanSplitFlow(new Integer(info.getIfcanSplitFlow()));
		process.setProcessState(info.getProcessState());

		// 2008-08-15�¼��������.
		process.setSplitType(new Integer(info.getSplitType()));
		process.setSplitProcessHandle(info.getSplitProcessHandle());

		// 2008-08-20�¼��������
		process.setIsSubFlowStep(new Integer(info.getIssubFlowStep()));

		// 2008-08-21�¼��������.
		process.setInnerStep(new Integer(info.getInnerStep()));

		// 2008-08-22�¼��������.
		process.setStep(new Integer(info.getStep()));
		process.setUniteStep(new Integer(info.getUniteStep()));

		// 2008-09-19
		process.setStepState(info.getStepState());

		// ����Ȩ����Ϣ
		this.getProcessAccessInfo(info, process);
		// �����ĵ�Ŀ¼�ṹ��Ϣ
		// this.getProcessDocFolders(info, process);

		// ������Ŀ¼Ȩ��
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
	 * ��ȡ��ʼ��Ĳ�����Ϣ
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
	 * ��ȡ������Ĳ�����Ϣ
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
	 * ��ȡ������Ĳ�����Ϣ
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
	 * ��ȡ·����Ϣ
	 * 
	 * @param graph
	 * @param application
	 */
	private void getProcessAndRouteInfo(FlowGraph graph,
			TblApplication application) {
		HashMap processMap = new HashMap();

		// �ȴ������еĲ�����Ϣ
		Object[] cells = graph.getGraphLayoutCache().getCells(false, true,
				false, false);
		List processes = new ArrayList();
		for (int i = 0; i < cells.length; i++) {
			// ֻ����DefaultGraphCell����
			if (!(cells[i] instanceof DefaultGraphCell))
				continue;

			DefaultGraphCell cell = (DefaultGraphCell) cells[i];
			Object o = cell.getUserObject();
			// ֻ����ProcessElementObject
			if (!(o instanceof FlowElementObject))
				continue;

			FlowElementObject feo = (FlowElementObject) o;

			TblProcess p = this.getProcessInfo(feo);

			processes.add(p);
			// ��ӵ�ӳ��
			processMap.put(feo, p);
		}

		// ��ȡ���е�·����Ϣ
		Object[] edges = graph.getGraphLayoutCache().getCells(false, false,
				false, true);

		List routes = new ArrayList();

		for (int i = 0; i < edges.length; i++) {
			// ֻ����Edge����
			if (!(edges[i] instanceof DefaultEdge))
				continue;
			DefaultEdge e = (DefaultEdge) edges[i];

			// ֻ����FlowEdgeObject
			Object o = e.getUserObject();
			if (!(o instanceof FlowEdgeObject))
				continue;

			FlowEdgeObject feo = (FlowEdgeObject) o;

			// ��ʼ���������Ϣ
			TblAppRoute r = new TblAppRoute();

			r.setRouteDesc(feo.getDescription());
			r.setRouteName(feo.getName());
			r.setRouteDisplayText(feo.getDisplayText());
			r.setRoutePositionInfo(feo.getPositionUrl());
			r.setSubFlowPath(new Integer(feo.getSubFlowPath()));

			// ����ʼ����
			if (processMap.containsKey(feo.getBeginProcess())) {
				r.setBeginProcess((TblProcess) processMap.get(feo
						.getBeginProcess()));
			} else {
				// Process p = this.getProcessInfo(feo.getBeginProcess());
				// r.setBeginProcess(p);
				// processMap.put(feo.getBeginProcess(), p);
			}

			// �����������
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
	 * ��ȡ����Ȩ����Ϣ
	 * 
	 * @param info
	 * @param process
	 */
	private void getProcessAccessInfo(ProcessElementObject info,
			TblProcess process) {
		List roles = info.getAccessRoles();// ��ɫȨ��
		List users = info.getAccessUsers();// Ա��Ȩ��

		List pAccess = new ArrayList();

		// �ȴ����ɫ
		for (Iterator it = roles.iterator(); it.hasNext();) {
			TblProcessAccess access = new TblProcessAccess();
			Long ll=(((Long) ((KeyValueObject) it.next())
					.getKey()));
			access
					.setRoleId(new Integer(ll.intValue()));
			access.setRoleType(new Integer(TblProcessAccess.ROLE_TYPE_ROLE));

			pAccess.add(access);
		}

		// �ٴ����û�
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
