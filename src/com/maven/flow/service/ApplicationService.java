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
 * ��ΪApplicationLogic�Լ�ApplicationAction���м�㣬�������߼���ֻ���� Action��Logic֮��Ĵ���
 * 
 * @author kinz
 * @version 1.0 2007-6-6
 * @since JDK1.4
 */

public class ApplicationService implements IBaseService {

	// �������ڱ���ʱ����

	// �����߼��Ķ���
	public TblApplicationDAO appDAO = new TblApplicationDAO();

	/**
	 * �洢���̻�����Ϣ
	 * 
	 * @param application
	 * @throws Exception
	 */
	public void createApplication(TblApplication application) throws Exception {
		// �������е��ã������κ�����
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
