/**
 * @(#)ApplicationService.java 2007-6-6
 * CopyRight 2007 Ulinktek Co. Ltd.  All rights reserved
 * 
 */
package com.maven.flow.service;

import com.maven.flow.base.IBaseService;
import com.maven.flow.hibernate.dao.TblApplication;
import com.maven.flow.hibernate.dao.TblApplicationDAO;

/**
 * 作为ApplicationLogic以及ApplicationAction的中间层，不处理逻辑，只负责 Action和Logic之间的传递
 * 
 * @author kinz
 * @version 1.0 2007-6-6
 * @since JDK1.4
 */

public class ApplicationService implements IBaseService {

	// 操作环节表，暂时不用

	// 处理逻辑的对象
	public TblApplicationDAO appDAO = new TblApplicationDAO();

	/**
	 * 存储流程基本信息
	 * 
	 * @param application
	 * @throws Exception
	 */
	public void createApplication(TblApplication application) throws Exception {
		// 仅仅进行调用，不做任何事情
		appDAO.save(application);
	}

	public TblApplication findByAppId(Integer appId) throws Exception {
		return appDAO.findById(appId);
	}

	public void updateApplication(TblApplication app) throws Exception {
		appDAO.attachDirty(app);
	}

	public void deleteApplication(TblApplication app) throws Exception {
		appDAO.delete(app);
	}
	public void deleteyAppId(Integer appId) throws Exception {
		appDAO.deleteyAppId(appId);
	}

	public static void main(String[] args) {
		TblApplication app = new TblApplication();
		app.setAppId(new Integer(1));
		ApplicationService ss = new ApplicationService();
		try {
			ss.createApplication(app);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
