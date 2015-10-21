package com.maven.flow.hibernate.dao;

/**
 * TblId generated by MyEclipse Persistence Tools
 */

public class TblId implements java.io.Serializable {

	// Fields

	private Integer id;

	private String tableName;

	private String fieldName;

	private Integer fvalue;

	// Constructors

	/** default constructor */
	public TblId() {
	}

	/** minimal constructor */
	public TblId(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public TblId(Integer id, String tableName, String fieldName, Integer fvalue) {
		this.id = id;
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.fvalue = fvalue;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFvalue() {
		return this.fvalue;
	}

	public void setFvalue(Integer fvalue) {
		this.fvalue = fvalue;
	}

}