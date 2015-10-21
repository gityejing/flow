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
 * 流程发布器，桥接流程编辑器以及系统流程引擎 
 * 1、检查流程合法性
 * 2、发布流程到系统中 
 *
 * @author      Kinz
 * @version     1.0 2007-06-07
 * @since       JDK1.5
 */

public class FlowDeployer {

	//	private Log log = LogFactory.getLog(this.getClass());//日志记录 
	WorkFlowService workFlowServiec=new WorkFlowService();
	private WorkFlowEditor editor = null;//编辑器界面的引用 

	public FlowDeployer(WorkFlowEditor editor) {
		this.editor = editor;
	}

	/**
	 * 发布流程
	 * @param graph
	 */
	public void deployFlow(FlowGraph graph) {
		new DeployThread(graph).start();
	}

	private void doDeploy(FlowGraph graph) {

		if (graph == null) {
			Log.RecLog(Log.MESSAGE,"请打开要发布的流程");
			return;
		}

		if(graph.getGraphInfo().getValidStatus() == 1){
			//已经发布过，询问一下
			if (!Log.ShowYNMsg(Const.frame,"提示信息","流程[" + graph + "]已经发布，是否重新发布？点击确定将会重新发布该流程")) {
				editor.messageView.warn("流程[" + graph + "]的发布被取消");
				return;
			} else {
				editor.messageView.warn("重新发布流程[" + graph + "]");
			}
		} else {
			/*
			 if (!editor.confirm("要发布流程[" + graph + "]吗？点击确定进行发布")) {
			 editor.messageView.warn("流程[" + graph + "]的发布被取消");
			 return;
			 }
			 */
		}
		
	
		TblApplication application = new FlowApplicationParser().parse(graph);//解释图形.
        Integer appId=new Integer(""+application.getAppId());

		try {
			
			   
			    //真正发布并保存到数据库中
			    editor.messageView.clearMessage();
			    //先清空
			    
			    boolean flag=workFlowServiec.deploy(application);
                if (flag)
                {
				//发布成功
				editor.messageView.info("流程已经成功发布，流程ID为：" + appId);

				graph.getGraphInfo().setDeployed(true);
				graph.getGraphInfo().setAppId(appId.longValue());
				graph.getGraphInfo().setPublishTime(new Date());
				graph.getGraphInfo().setValidStatus(1);
				graph.getGraphInfo().setReleaseStatus(1);

				//设置发布者姓名
				graph.getGraphInfo().setPublisher(""+SystemGloable.CurUserInfo.getFemployeeSn());
				graph.getGraphInfo().setPublisherName(SystemGloable.CurUserInfo.getFemployeeName());
				graph.getGraphInfo().setDeployed(true);

				//流程发布成功后，进行保存
				FlowGraphManager.saveGraph(graph);//保存当前的流程.

				FlowGraphManager.getEditor().strutcView.updateGraphNode(graph);
				FlowGraphManager.setCurrentGraph(graph);
                }
                else
                {
                	editor.messageView.info("流程发布失败，流程ID为：" + appId);	
                }

		} catch (Exception ex) {
			ex.printStackTrace();
			editor.messageView.error("发布流程发生错误，信息：" + ex.getMessage());
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