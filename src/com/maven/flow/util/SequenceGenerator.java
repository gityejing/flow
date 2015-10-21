/*
 * 创建日期 2007.04.18
 * 作者     kinz
 * 文件名   MysqlSequenceGenerator.java
 * 版权     CopyRight (c) 2007
 * 功能说明 序列号生成器
 */
package com.maven.flow.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SequenceGenerator {

	private static int step = 1;//种子步进值

	private static HashMap seqs = new HashMap();

	private static BeanHandler handler = new BeanHandler(Sequence.class.getName());

	private static String SEQ_TBL_NAME = "tbl_Sequence";

	private static Log log = LogFactory.getLog(SequenceGenerator.class);

	static {
		//在开始的时候进行创建序列表
		try {
			//createSequenceTable();
		} catch (Exception e) {
			log.debug("创建序列表失败，信息：" + e.getMessage());
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
		//先查看缓存中是否有该序列的种子
		if(seqs.containsKey(sequenceName)){
		    seq = (Sequence) seqs.get(sequenceName);
		}else{
		    //查询序列值
		    String sql = "select * from "+SEQ_TBL_NAME +" where sequenceName=?";
		    seq = (Sequence) QueryRunner.queryResultSet(sql,new Object[]{sequenceName},new BeanHandler(Sequence.class.getName()));
		    //如果没有序列，插入新的序列
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

		//如果下一个值已经超过步进值，则更新序列
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
	 * 添加序列
	 */
	private static void addSequence(Sequence seq) throws Exception {
		String sql = "insert into " + SEQ_TBL_NAME + " (sequenceName,sequenceValue) values " + " (?,?)";
		QueryRunner.update(sql, new Object[] { seq.getSequenceName(), new Long(step) });
	}

        /**
         * 添加序列
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
