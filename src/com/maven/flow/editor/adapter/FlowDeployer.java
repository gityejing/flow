/**
 * @(#)FlowDeployer.java 2007-06-07
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */

package com.maven.flow.editor.adapter;

import java.util.Date;

import com.maven.flow.editor.SystemGloable;
import com.maven.flow.editor.ui.FlowGraph;
import com.maven.flow.editor.ui.FlowGraphManager;
import com.maven.flow.editor.ui.WorkFlowEditor;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.service.WorkFlowService;
import com.maven.flow.util.Const;
import com.maven.flow.util.Log;

/**
 * ���̷��������Ž����̱༭���Լ�ϵͳ�������� 
 * 1��������̺Ϸ���
 * 2���������̵�ϵͳ�� 
 *
 * @author      Kinz
 * @version     1.0 2007-06-07
 * @since       JDK1.5
 */

public class FlowDeployer {

	//	private Log log = LogFactory.getLog(this.getClass());//��־��¼ 
	WorkFlowService workFlowServiec=new WorkFlowService();
	private WorkFlowEditor editor = null;//�༭����������� 

	public FlowDeployer(WorkFlowEditor editor) {
		this.editor = editor;
	}

	/**
	 * ��������
	 * @param graph
	 */
	public void deployFlow(FlowGraph graph) {
		new DeployThread(graph).start();
	}

	private void doDeploy(FlowGraph graph) {

		if (graph == null) {
			Log.RecLog(Log.MESSAGE,"���Ҫ����������");
			return;
		}

		if(graph.getGraphInfo().getValidStatus() == 1){
			//�Ѿ���������ѯ��һ��
			if (!Log.ShowYNMsg(Const.frame,"��ʾ��Ϣ","����[" + graph + "]�Ѿ��������Ƿ����·��������ȷ���������·���������")) {
				editor.messageView.warn("����[" + graph + "]�ķ�����ȡ��");
				return;
			} else {
				editor.messageView.warn("���·�������[" + graph + "]");
			}
		} else {
			/*
			 if (!editor.confirm("Ҫ��������[" + graph + "]�𣿵��ȷ�����з���")) {
			 editor.messageView.warn("����[" + graph + "]�ķ�����ȡ��");
			 return;
			 }
			 */
		}
		
	
		TblApplication application = new FlowApplicationParser().parse(graph);//����ͼ��.
        Integer appId=new Integer(""+application.getAppId());

		try {
			
			   
			    //�������������浽���ݿ���
			    editor.messageView.clearMessage();
			    //�����
			    
			    boolean flag=workFlowServiec.deploy(application);
                if (flag)
                {
				//�����ɹ�
				editor.messageView.info("�����Ѿ��ɹ�����������IDΪ��" + appId);

				graph.getGraphInfo().setDeployed(true);
				graph.getGraphInfo().setAppId(appId.longValue());
				graph.getGraphInfo().setPublishTime(new Date());
				graph.getGraphInfo().setValidStatus(1);
				graph.getGraphInfo().setReleaseStatus(1);

				//���÷���������
				graph.getGraphInfo().setPublisher(""+SystemGloable.CurUserInfo.getFemployeeSn());
				graph.getGraphInfo().setPublisherName(SystemGloable.CurUserInfo.getFemployeeName());
				graph.getGraphInfo().setDeployed(true);

				//���̷����ɹ��󣬽��б���
				FlowGraphManager.saveGraph(graph);//���浱ǰ������.

				FlowGraphManager.getEditor().strutcView.updateGraphNode(graph);
				FlowGraphManager.setCurrentGraph(graph);
                }
                else
                {
                	editor.messageView.info("���̷���ʧ�ܣ�����IDΪ��" + appId);	
                }

		} catch (Exception ex) {
			ex.printStackTrace();
			editor.messageView.error("�������̷���������Ϣ��" + ex.getMessage());
		}
	}

	class DeployThread extends Thread {
		FlowGraph graph = null;

		DeployThread(FlowGraph graph) {
			this.graph = graph;
		}

		public void run() {
			doDeploy(graph);
		}
	}
}