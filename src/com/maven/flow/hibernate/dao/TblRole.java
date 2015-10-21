package com.maven.flow.hibernate.dao;

/**
 * TblRole generated by MyEclipse Persistence Tools
 */

public class TblRole implements java.io.Serializable {

	// Fields

	private Long froleSn;

	private String froleName;

	private String froleDesc;

	private String froleFunction;

	private String froleCode;

	// Constructors

	/** default constructor */
	public TblRole() {
	}

	/** minimal constructor */
	public TblRole(Long froleSn) {
		this.froleSn = froleSn;
	}

	/** full constructor */
	public TblRole(Long froleSn, String froleName, String froleDesc,
			String froleFunction, String froleCode) {
		this.froleSn = froleSn;
		this.froleName = froleName;
		this.froleDesc = froleDesc;
		this.froleFunction = froleFunction;
		this.froleCode = froleCode;
	}

	// Property accessors

	public Long getFroleSn() {
		return this.froleSn;
	}

	public void setFroleSn(Long froleSn) {
		this.froleSn = froleSn;
	}

	public String getFroleName() {
		return this.froleName;
	}

	public void setFroleName(String froleName) {
		this.froleName = froleName;
	}

	public String getFroleDesc() {
		return this.froleDesc;
	}

	public void setFroleDesc(String froleDesc) {
		this.froleDesc = froleDesc;
	}

	public String getFroleFunction() {
		return this.froleFunction;
	}

	public void setFroleFunction(String froleFunction) {
		this.froleFunction = froleFunction;
	}

	public String getFroleCode() {
		return this.froleCode;
	}

	public void setFroleCode(String froleCode) {
		this.froleCode = froleCode;
	}

}