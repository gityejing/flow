package com.maven.flow.editor.bean;

/**
 * @(#)EmployeeInfo.java 2007-6-11
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 *
 */

import java.io.Serializable;
import java.util.Date;

/**
 * 封装员工资料信息的数据
 * 
 * @author jumty
 * @version 1.02007-6-11
 * @since JDK1.4
 */
public class EmployeeInfo implements Serializable {

	private long employeeID = 0; // 员工编号

	private int logonFlag = 0; // 登录是否禁用:0:正常,1:禁用

	private String employeeNO = null; // 员工工号

	private String employeeFamilyName = null; // 员工姓

	private String employeeGivenName = null; // 员工名

	private String employeeIDNO = null; // 员工身份证号码

	private int employeeGender = 0; // 员工性别:0:男,1:女

	private Date employeeBirthday = null; // 出生年月

	private String employeeJiguan = null; // 籍贯

	private int employeeMarry = 0; // 婚姻状况:0:未婚,1:已婚

	private String employeeHomeAddress = null; // 家庭住址

	private int employeeZhengzhimianmao = 0; // 政治面貌:0:普通,1:党员,2:团员,3:民主党派

	private int employeeWenhuachengdu = 0; // 文化程度:0:小学,1:初中,2:高中,3:大专,4:大本,5:硕士,6:博士

	private String employeeWaiyushuiping = null; // 外语水平

	private String employeeZhicheng = null; // 职称

	private String employeeContanctPhone = null; // 联系电话

	private String employeeMobie = null; // 手机

	private String employeeOfifcePhone = null; // 办公室电话

	private String employeeEmail = null; // 邮件地址

	private String employeeHomePhone = null; // 家庭电话

	private String employeePosition = null; // 职务

	private String userPassword = null; // 员工登录密码

	private int partnerFlag = -1; // 外部员工标记 0:内部员工, 1:外部员工

	private int deleteFlag = 0; // 删除标记

	private String employeeIkey; // 员工IKey信息

	private String employeeFolk; // 民族

	private String training; // 培训

	private String certificate; // 证书

	private String kaoHe; // 考核

	private String jiangCheng; // 奖惩

	private String departmentName; // 部门名字

	private Date leaveDate = null;

	private String fullName;

	// 以下是扩展属性
	private long departmentID; // 所属部门id

	private int userFlag;

	private String deptIds;

	// add by qqk date:2007-11-26
	private String regAddr;// 户口所在地.

	private String empType;// 所属类型

	private String shortNum;// 短号.

	private String extensionNum;// 分机号

	private String fax;// 传真.

	private String joinType;// 入职方式

	private String graSchool;// 毕业学院名称.

	private String resume;// 个人简历.

	private String techTitle;// 职称

	private String zhuChang;// 注场情况.

	private String officeNumber2;// 办公电话2

	private String grade;// 级别

	private String comeDate;// 入职时间;

	private String salaryValue;// 月度基本工资

	private String monthBonus;// 月度奖金

	private String subsidy;// 补贴

	private double allAll;// 社保,个人总和.

	private double allAggregateUnit;// 社保[单位部分]

	private double allAggregatePerson;// 社保[个人部分]

	// add by lzm::::2008-02-18
	private String residenceType;// 户口性质

	private String engageType;// 员工状态

	private int birthMonth;// 出生月份

	private String ascription;// 归属

	private String joinDate;// 入职日期

	private int contractYear;// 签定合同年数

	private String contractNum;// 合同编号

	private Date nextContractDate;// 下次定合同时间

	private String technicalPost;// 职称级别

	private String speciality;// 专业

	private String specialityDate;// 取得时间

	private double zhuChangCost;// 驻场费

	private String individualPrise;// 个人奖项

	private String extraField1;// 备用字段1

	private String extraField2;// 备用字段2

	private String schoolSpeciality;// 所学专业.

	private double baseMoney;// 基本工资..

	private String alltblEmployeeCertificate;

	private String certificateDate;

	private String canInteLogin;// 是否允许外网登录.

	private String bangLanIP;// 是否绑定内网IP来登录.

	private String lanIp;// 内网IP

	// add by lwh::::2008-03-18

	private String ChBirthday;

	private int scount;

	private int xcount;

	private int zaotuiCount;// 早退数

	private int chidaoCount;// 迟到数.

	private int subCompanyID;

	private String subCompanyName;

	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public int getSubCompanyID() {
		return subCompanyID;
	}

	public void setSubCompanyID(int subCompanyID) {
		this.subCompanyID = subCompanyID;
	}

	public int getChidaoCount() {
		return chidaoCount;
	}

	public void setChidaoCount(int chidaoCount) {
		this.chidaoCount = chidaoCount;
	}

	public int getZaotuiCount() {
		return zaotuiCount;
	}

	public void setZaotuiCount(int zaotuiCount) {
		this.zaotuiCount = zaotuiCount;
	}

	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}

	public int getXcount() {
		return xcount;
	}

	public void setXcount(int xcount) {
		this.xcount = xcount;
	}

	public String getBangLanIP() {
		return bangLanIP;
	}

	public void setBangLanIP(String bangLanIP) {
		this.bangLanIP = bangLanIP;
	}

	public String getCanInteLogin() {
		return canInteLogin;
	}

	public void setCanInteLogin(String canInteLogin) {
		this.canInteLogin = canInteLogin;
	}

	public String getLanIp() {
		return lanIp;
	}

	public void setLanIp(String lanIp) {
		this.lanIp = lanIp;
	}

	public String getAlltblEmployeeCertificate() {
		return alltblEmployeeCertificate;
	}

	public void setAlltblEmployeeCertificate(String alltblEmployeeCertificate) {
		this.alltblEmployeeCertificate = alltblEmployeeCertificate;
	}

	public String getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(String certificateDate) {
		this.certificateDate = certificateDate;
	}

	public double getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(double baseMoney) {
		this.baseMoney = baseMoney;
	}

	public String getSchoolSpeciality() {
		return schoolSpeciality;
	}

	public void setSchoolSpeciality(String schoolSpeciality) {
		this.schoolSpeciality = schoolSpeciality;
	}

	public String getAscription() {
		return ascription;
	}

	public void setAscription(String ascription) {
		this.ascription = ascription;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public int getContractYear() {
		return contractYear;
	}

	public void setContractYear(int contractYear) {
		this.contractYear = contractYear;
	}

	public String getEngageType() {
		return engageType;
	}

	public void setEngageType(String engageType) {
		this.engageType = engageType;
	}

	public String getExtraField1() {
		return extraField1;
	}

	public void setExtraField1(String extraField1) {
		this.extraField1 = extraField1;
	}

	public String getExtraField2() {
		return extraField2;
	}

	public void setExtraField2(String extraField2) {
		this.extraField2 = extraField2;
	}

	public String getIndividualPrise() {
		return individualPrise;
	}

	public void setIndividualPrise(String individualPrise) {
		this.individualPrise = individualPrise;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public Date getNextContractDate() {
		return nextContractDate;
	}

	public void setNextContractDate(Date nextContractDate) {
		this.nextContractDate = nextContractDate;
	}

	public String getResidenceType() {
		return residenceType;
	}

	public void setResidenceType(String residenceType) {
		this.residenceType = residenceType;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getSpecialityDate() {
		return specialityDate;
	}

	public void setSpecialityDate(String specialityDate) {
		this.specialityDate = specialityDate;
	}

	public String getTechnicalPost() {
		return technicalPost;
	}

	public void setTechnicalPost(String technicalPost) {
		this.technicalPost = technicalPost;
	}

	public double getZhuChangCost() {
		return zhuChangCost;
	}

	public void setZhuChangCost(double zhuChangCost) {
		this.zhuChangCost = zhuChangCost;
	}

	public double getAllAggregatePerson() {
		return allAggregatePerson;
	}

	public void setAllAggregatePerson(double allAggregatePerson) {
		this.allAggregatePerson = allAggregatePerson;
	}

	public double getAllAggregateUnit() {
		return allAggregateUnit;
	}

	public void setAllAggregateUnit(double allAggregateUnit) {
		this.allAggregateUnit = allAggregateUnit;
	}

	public double getAllAll() {
		return allAll;
	}

	public void setAllAll(double allAll) {
		this.allAll = allAll;
	}

	public String getMonthBonus() {
		return monthBonus;
	}

	public void setMonthBonus(String monthBonus) {
		this.monthBonus = monthBonus;
	}

	public String getSalaryValue() {
		return salaryValue;
	}

	public void setSalaryValue(String salaryValue) {
		this.salaryValue = salaryValue;
	}

	public String getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(String subsidy) {
		this.subsidy = subsidy;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getComeDate() {
		return comeDate;
	}

	public void setComeDate(String comeDate) {
		this.comeDate = comeDate;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public String getExtensionNum() {
		return extensionNum;
	}

	public void setExtensionNum(String extensionNum) {
		this.extensionNum = extensionNum;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGraSchool() {
		return graSchool;
	}

	public void setGraSchool(String graSchool) {
		this.graSchool = graSchool;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getOfficeNumber2() {
		return officeNumber2;
	}

	public void setOfficeNumber2(String officeNumber2) {
		this.officeNumber2 = officeNumber2;
	}

	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getShortNum() {
		return shortNum;
	}

	public void setShortNum(String shortNum) {
		this.shortNum = shortNum;
	}

	public String getTechTitle() {
		return techTitle;
	}

	public void setTechTitle(String techTitle) {
		this.techTitle = techTitle;
	}

	public String getZhuChang() {
		return zhuChang;
	}

	public void setZhuChang(String zhuChang) {
		this.zhuChang = zhuChang;
	}

	/**
	 * @return 返回 employeeBirthday。
	 */
	public Date getEmployeeBirthday() {
		return employeeBirthday;
	}

	/**
	 * @param employeeBirthday
	 *            要设置的 employeeBirthday。
	 */
	public void setEmployeeBirthday(Date employeeBirthday) {
		this.employeeBirthday = employeeBirthday;
	}

	/**
	 * @return 返回 employeeContanctPhone。
	 */
	public String getEmployeeContanctPhone() {
		return employeeContanctPhone;
	}

	/**
	 * @param employeeContanctPhone
	 *            要设置的 employeeContanctPhone。
	 */
	public void setEmployeeContanctPhone(String employeeContanctPhone) {
		this.employeeContanctPhone = employeeContanctPhone;
	}

	/**
	 * @return 返回 employeeEmail。
	 */
	public String getEmployeeEmail() {
		return employeeEmail;
	}

	/**
	 * @param employeeEmail
	 *            要设置的 employeeEmail。
	 */
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	/**
	 * @return 返回 employeeFamilyName。
	 */
	public String getEmployeeFamilyName() {
		return employeeFamilyName;
	}

	/**
	 * @param employeeFamilyName
	 *            要设置的 employeeFamilyName。
	 */
	public void setEmployeeFamilyName(String employeeFamilyName) {
		this.employeeFamilyName = employeeFamilyName;
	}

	/**
	 * @return 返回 employeeGender。
	 */
	public int getEmployeeGender() {
		return employeeGender;
	}

	/**
	 * @param employeeGender
	 *            要设置的 employeeGender。
	 */
	public void setEmployeeGender(int employeeGender) {
		this.employeeGender = employeeGender;
	}

	/**
	 * @return 返回 employeeGivenName。
	 */
	public String getEmployeeGivenName() {
		return employeeGivenName;
	}

	/**
	 * @param employeeGivenName
	 *            要设置的 employeeGivenName。
	 */
	public void setEmployeeGivenName(String employeeGivenName) {
		this.employeeGivenName = employeeGivenName;
	}

	/**
	 * @return 返回 employeeHomeAddress。
	 */
	public String getEmployeeHomeAddress() {
		return employeeHomeAddress;
	}

	/**
	 * @param employeeHomeAddress
	 *            要设置的 employeeHomeAddress。
	 */
	public void setEmployeeHomeAddress(String employeeHomeAddress) {
		this.employeeHomeAddress = employeeHomeAddress;
	}

	/**
	 * @return 返回 employeeHomePhone。
	 */
	public String getEmployeeHomePhone() {
		return employeeHomePhone;
	}

	/**
	 * @param employeeHomePhone
	 *            要设置的 employeeHomePhone。
	 */
	public void setEmployeeHomePhone(String employeeHomePhone) {
		this.employeeHomePhone = employeeHomePhone;
	}

	/**
	 * @return 返回 employeeID。
	 */
	public long getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID
	 *            要设置的 employeeID。
	 */
	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return 返回 employeeIDNO。
	 */
	public String getEmployeeIDNO() {
		return employeeIDNO;
	}

	/**
	 * @param employeeIDNO
	 *            要设置的 employeeIDNO。
	 */
	public void setEmployeeIDNO(String employeeIDNO) {
		this.employeeIDNO = employeeIDNO;
	}

	/**
	 * @return 返回 employeeJiguan。
	 */
	public String getEmployeeJiguan() {
		return employeeJiguan;
	}

	/**
	 * @param employeeJiguan
	 *            要设置的 employeeJiguan。
	 */
	public void setEmployeeJiguan(String employeeJiguan) {
		this.employeeJiguan = employeeJiguan;
	}

	/**
	 * @return 返回 employeeMarry。
	 */
	public int getEmployeeMarry() {
		return employeeMarry;
	}

	/**
	 * @param employeeMarry
	 *            要设置的 employeeMarry。
	 */
	public void setEmployeeMarry(int employeeMarry) {
		this.employeeMarry = employeeMarry;
	}

	/**
	 * @return 返回 employeeMobie。
	 */
	public String getEmployeeMobie() {
		return employeeMobie;
	}

	/**
	 * @param employeeMobie
	 *            要设置的 employeeMobie。
	 */
	public void setEmployeeMobie(String employeeMobie) {
		this.employeeMobie = employeeMobie;
	}

	/**
	 * @return 返回 employeeNO。
	 */
	public String getEmployeeNO() {
		return employeeNO;
	}

	/**
	 * @param employeeNO
	 *            要设置的 employeeNO。
	 */
	public void setEmployeeNO(String employeeNO) {
		this.employeeNO = employeeNO;
	}

	/**
	 * @return 返回 employeeOfifcePhone。
	 */
	public String getEmployeeOfifcePhone() {
		return employeeOfifcePhone;
	}

	/**
	 * @param employeeOfifcePhone
	 *            要设置的 employeeOfifcePhone。
	 */
	public void setEmployeeOfifcePhone(String employeeOfifcePhone) {
		this.employeeOfifcePhone = employeeOfifcePhone;
	}

	/**
	 * @return 返回 employeePosition。
	 */
	public String getEmployeePosition() {
		return employeePosition;
	}

	/**
	 * @param employeePosition
	 *            要设置的 employeePosition。
	 */
	public void setEmployeePosition(String employeePosition) {
		this.employeePosition = employeePosition;
	}

	/**
	 * @return 返回 employeeWaiyushuiping。
	 */
	public String getEmployeeWaiyushuiping() {
		return employeeWaiyushuiping;
	}

	/**
	 * @param employeeWaiyushuiping
	 *            要设置的 employeeWaiyushuiping。
	 */
	public void setEmployeeWaiyushuiping(String employeeWaiyushuiping) {
		this.employeeWaiyushuiping = employeeWaiyushuiping;
	}

	/**
	 * @return 返回 employeeWenhuachengdu。
	 */
	public int getEmployeeWenhuachengdu() {
		return employeeWenhuachengdu;
	}

	/**
	 * @param employeeWenhuachengdu
	 *            要设置的 employeeWenhuachengdu。
	 */
	public void setEmployeeWenhuachengdu(int employeeWenhuachengdu) {
		this.employeeWenhuachengdu = employeeWenhuachengdu;
	}

	/**
	 * @return 返回 employeeZhengzhimianmao。
	 */
	public int getEmployeeZhengzhimianmao() {
		return employeeZhengzhimianmao;
	}

	/**
	 * @param employeeZhengzhimianmao
	 *            要设置的 employeeZhengzhimianmao。
	 */
	public void setEmployeeZhengzhimianmao(int employeeZhengzhimianmao) {
		this.employeeZhengzhimianmao = employeeZhengzhimianmao;
	}

	/**
	 * @return 返回 employeeZhicheng。
	 */
	public String getEmployeeZhicheng() {
		return employeeZhicheng;
	}

	/**
	 * @param employeeZhicheng
	 *            要设置的 employeeZhicheng。
	 */
	public void setEmployeeZhicheng(String employeeZhicheng) {
		this.employeeZhicheng = employeeZhicheng;
	}

	/**
	 * @return 返回 logonFlag。
	 */
	public int getLogonFlag() {
		return logonFlag;
	}

	/**
	 * @param logonFlag
	 *            要设置的 logonFlag。
	 */
	public void setLogonFlag(int logonFlag) {
		this.logonFlag = logonFlag;
	}

	/**
	 * @return 返回 partnerFlag。
	 */
	public int getPartnerFlag() {
		return partnerFlag;
	}

	/**
	 * @param partnerFlag
	 *            要设置的 partnerFlag。
	 */
	public void setPartnerFlag(int partnerFlag) {
		this.partnerFlag = partnerFlag;
	}

	/**
	 * @return 返回 userPassword。
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            要设置的 userPassword。
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public long getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(long departmentID) {
		this.departmentID = departmentID;
	}

	public String getEmployeeFolk() {
		return employeeFolk;
	}

	public void setEmployeeFolk(String employeeFolk) {
		this.employeeFolk = employeeFolk;
	}

	public String getTraining() {
		return training;
	}

	public void setTraining(String training) {
		this.training = training;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getJiangCheng() {
		return jiangCheng;
	}

	public void setJiangCheng(String jiangCheng) {
		this.jiangCheng = jiangCheng;
	}

	public String getKaoHe() {
		return kaoHe;
	}

	public void setKaoHe(String kaoHe) {
		this.kaoHe = kaoHe;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEmployeeIkey() {
		return employeeIkey;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public int getUserFlag() {
		return userFlag;
	}

	public void setEmployeeIkey(String emloyeeIkey) {
		this.employeeIkey = emloyeeIkey;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public void setUserFlag(int userFlag) {
		this.userFlag = userFlag;
	}

	public String getChBirthday() {
		return ChBirthday;
	}

	public void setChBirthday(String chBirthday) {
		ChBirthday = chBirthday;
	}

}
