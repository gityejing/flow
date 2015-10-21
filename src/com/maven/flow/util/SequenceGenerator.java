/*
 * �������� 2007.04.18
 * ����     kinz
 * �ļ���   MysqlSequenceGenerator.java
 * ��Ȩ     CopyRight (c) 2007
 * ����˵�� ���к�������
 */
package com.maven.flow.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SequenceGenerator {

	private static int step = 1;//���Ӳ���ֵ

	private static HashMap seqs = new HashMap();

	private static BeanHandler handler = new BeanHandler(Sequence.class.getName());

	private static String SEQ_TBL_NAME = "tbl_Sequence";

	private static Log log = LogFactory.getLog(SequenceGenerator.class);

	static {
		//�ڿ�ʼ��ʱ����д������б�
		try {
			//createSequenceTable();
		} catch (Exception e) {
			log.debug("�������б�ʧ�ܣ���Ϣ��" + e.getMessage());
		}
	}
        protected static synchronized long getSequenceOnlyNext(String sequenceName) throws Exception {
            Sequence seq = findSequence(sequenceName);
            if (seq == null) {
                seq = new Sequence();
                seq.setSequenceName(sequenceName);
                seq.setSequenceValue(0);

                addSequenceOnly(seq);
            }
            seq.setSequenceValue(seq.getSequenceValue() + 1);
            return seq.getSequenceValue();
        }
	protected static synchronized long getSequenceNext(String sequenceName) throws Exception {
		Sequence seq = findSequence(sequenceName);
		if (seq == null) {
			seq = new Sequence();
			seq.setSequenceName(sequenceName);
			seq.setSequenceValue(1);

			addSequence(seq);
		}

		seq.setSequenceValue(seq.getSequenceValue() + 1);

		updateSequence(seq);

		return seq.getSequenceValue();
		/*
		Sequence seq = null;
		//�Ȳ鿴�������Ƿ��и����е�����
		if(seqs.containsKey(sequenceName)){
		    seq = (Sequence) seqs.get(sequenceName);
		}else{
		    //��ѯ����ֵ
		    String sql = "select * from "+SEQ_TBL_NAME +" where sequenceName=?";
		    seq = (Sequence) QueryRunner.queryResultSet(sql,new Object[]{sequenceName},new BeanHandler(Sequence.class.getName()));
		    //���û�����У������µ�����
		    if(seq == null){
		        seq = new Sequence();
		        seq.setSequenceName(sequenceName);
		        seq.setSequenceValue(0);
		        addSequence(seq);
		    }else{
		        stepSequence(seq);
		    }
		    seq.setStep(step);

		    seqs.put(sequenceName,seq);
		}

		//�����һ��ֵ�Ѿ���������ֵ�����������
		long nextValue = seq.getNextValue();

		if(seq.shouldStep()){
		    stepSequence(seq);
		}

		return nextValue;
		*/
	}

	private static Sequence findSequence(String sequenceName) throws Exception {
		String sql = "SELECT * FROM " + SEQ_TBL_NAME + " WHERE SequenceName=?";
		return (Sequence) QueryRunner.queryResultSet(sql, new Object[] { sequenceName }, handler);
	}

	private static void createSequenceTable() throws Exception {
		String sql = "CREATE TABLE " + SEQ_TBL_NAME + "(sequenceName varchar(255)," + "sequenceValue numeric(15))";
		QueryRunner.update(sql);
	}

	private static void stepSequence(Sequence seq) throws Exception {
		String sql = "UPDATE " + SEQ_TBL_NAME + " SET sequenceValue=sequenceValue+?" + " WHERE sequenceName=?";
		QueryRunner.update(sql, new Object[] { new Long(step), seq.getSequenceName() });
		seq.step();
	}

	private static void updateSequence(Sequence seq) throws Exception {
		String sql = "UPDATE " + SEQ_TBL_NAME + " SET sequenceValue=?" + " WHERE sequenceName=?";
		QueryRunner.update(sql, new Object[] { new Long(seq.getSequenceValue()), seq.getSequenceName() });
		seq.step();
	}

	/**
	 * �������
	 */
	private static void addSequence(Sequence seq) throws Exception {
		String sql = "insert into " + SEQ_TBL_NAME + " (sequenceName,sequenceValue) values " + " (?,?)";
		QueryRunner.update(sql, new Object[] { seq.getSequenceName(), new Long(step) });
	}

        /**
         * �������
         */
        private static void addSequenceOnly(Sequence seq) throws Exception {
                String sql = "insert into " + SEQ_TBL_NAME + " (sequenceName,sequenceValue) values " + " (?,?)";
                QueryRunner.update(sql, new Object[] { seq.getSequenceName(), new Long(seq.getSequenceValue()) });
	}

	public static void main(String[] args) throws Exception {
		String seqName = "testseq";
		for (int i = 0; i < 29; i++) {
			long nextval = getSequenceNext(seqName);

			System.out.println(nextval);
		}
	}
}
