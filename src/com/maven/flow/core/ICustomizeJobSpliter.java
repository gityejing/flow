package com.maven.flow.core;

import com.maven.flow.editor.bean.JobBase;
import com.maven.flow.editor.bean.MiniJob;
import com.maven.flow.hibernate.dao.TblProcess;
import com.maven.flow.instance.hibernate.dao.TblJobBase;
import com.maven.flow.instance.hibernate.dao.TblMiniJob;


public interface ICustomizeJobSpliter extends IBaseLogic{

    /**
     * ʹ���Զ�������������зֽ�
     * @param process ���̲���
     * @param task ����ʵ��
     * @return һϵ��MiniJob���������
     * @throws Exception
     */
    TblMiniJob[] splitJob(TblProcess process, TblJobBase jobBase) throws Exception;
    
    
    public int findNextProcessPeopleCount(TblProcess process, TblJobBase jobBase) throws Exception;
    
    /**
     * ��ȡ����ֽ��������
     */
    String getSpliterName();

    /**
     * �Զ��������û���JOB
     * @param users String[] �û�ID����
     */
    public void setEmployees(String users[]);
    
    
    TblMiniJob[] splitJobUseObject(TblProcess process, TblJobBase jobBase,Object o) throws Exception;
    
}
