package com.maven.flow.editor.bean;

/**
 * @(#)EmployeeInfo.java 2007-6-11
 * CopyRight 2007 Ulinktek Co.Ltd. All rights reseved
 *
 */

import java.io.Serializable;
import java.util.Date;

/**
 * ��װԱ��������Ϣ������
 * 
 * @author jumty
 * @version 1.02007-6-11
 * @since JDK1.4
 */
public class EmployeeInfo implements Serializable {

	private long employeeID = 0; // Ա�����

	private int logonFlag = 0; // ��¼�Ƿ����:0:����,1:����

	private String employeeNO = null; // Ա������

	private String employeeFamilyName = null; // Ա����

	private String employeeGivenName = null; // Ա����

	private String employeeIDNO = null; // Ա�����֤����

	private int employeeGender = 0; // Ա���Ա�:0:��,1:Ů

	private Date employeeBirthday = null; // ��������

	private String employeeJiguan = null; // ����

	private int employeeMarry = 0; // ����״��:0:δ��,1:�ѻ�

	private String employeeHomeAddress = null; // ��ͥסַ

	private int employeeZhengzhimianmao = 0; // ������ò:0:��ͨ,1:��Ա,2:��Ա,3:��������

	private int employeeWenhuachengdu = 0; // �Ļ��̶�:0:Сѧ,1:����,2:����,3:��ר,4:��,5:˶ʿ,6:��ʿ

	private String employeeWaiyushuiping = null; // ����ˮƽ

	private String employeeZhicheng = null; // ְ��

	private String employeeContanctPhone = null; // ��ϵ�绰

	private String employeeMobie = null; // �ֻ�

	private String employeeOfifcePhone = null; // �칫�ҵ绰

	private String employeeEmail = null; // �ʼ���ַ

	private String employeeHomePhone = null; // ��ͥ�绰

	private String employeePosition = null; // ְ��

	private String userPassword = null; // Ա����¼����

	private int partnerFlag = -1; // �ⲿԱ����� 0:�ڲ�Ա��, 1:�ⲿԱ��

	private int deleteFlag = 0; // ɾ�����

	private String employeeIkey; // Ա��IKey��Ϣ

	private String employeeFolk; // ����

	private String training; // ��ѵ

	private String certificate; // ֤��

	private String kaoHe; // ����

	private String jiangCheng; // ����

	private String departmentName; // ��������

	private Date leaveDate = null;

	private String fullName;

	// ��������չ����
	private long departmentID; // ��������id

	private int userFlag;

	private String deptIds;

	// add by qqk date:2007-11-26
	private String regAddr;// �������ڵ�.

	private String empType;// ��������

	private String shortNum;// �̺�.

	private String extensionNum;// �ֻ���

	private String fax;// ����.

	private String joinType;// ��ְ��ʽ

	private String graSchool;// ��ҵѧԺ����.

	private String resume;// ���˼���.

	private String techTitle;// ְ��

	private String zhuChang;// ע�����.

	private String officeNumber2;// �칫�绰2

	private String grade;// ����

	private String comeDate;// ��ְʱ��;

	private String salaryValue;// �¶Ȼ�������

	private String monthBonus;// �¶Ƚ���

	private String subsidy;// ����

	private double allAll;// �籣,�����ܺ�.

	private double allAggregateUnit;// �籣[��λ����]

	private double allAggregatePerson;// �籣[���˲���]

	// add by lzm::::2008-02-18
	private String residenceType;// ��������

	private String engageType;// Ա��״̬

	private int birthMonth;// �����·�

	private String ascription;// ����

	private String joinDate;// ��ְ����

	private int contractYear;// ǩ����ͬ����

	private String contractNum;// ��ͬ���

	private Date nextContractDate;// �´ζ���ͬʱ��

	private String technicalPost;// ְ�Ƽ���

	private String speciality;// רҵ

	private String specialityDate;// ȡ��ʱ��

	private double zhuChangCost;// פ����

	private String individualPrise;// ���˽���

	private String extraField1;// �����ֶ�1

	private String extraField2;// �����ֶ�2

	private String schoolSpeciality;// ��ѧרҵ.

	private double baseMoney;// ��������..

	private String alltblEmployeeCertificate;

	private String certificateDate;

	private String canInteLogin;// �Ƿ�����������¼.

	private String bangLanIP;// �Ƿ������IP����¼.

	private String lanIp;// ����IP

	// add by lwh::::2008-03-18

	private String ChBirthday;

	private int scount;

	private int xcount;

	private int zaotuiCount;// ������

	private int chidaoCount;// �ٵ���.

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
	 * @return ���� employeeBirthday��
	 */
	public Date getEmployeeBirthday() {
		return employeeBirthday;
	}

	/**
	 * @param employeeBirthday
	 *            Ҫ���õ� employeeBirthday��
	 */
	public void setEmployeeBirthday(Date employeeBirthday) {
		this.employeeBirthday = employeeBirthday;
	}

	/**
	 * @return ���� employeeContanctPhone��
	 */
	public String getEmployeeContanctPhone() {
		return employeeContanctPhone;
	}

	/**
	 * @param employeeContanctPhone
	 *            Ҫ���õ� employeeContanctPhone��
	 */
	public void setEmployeeContanctPhone(String employeeContanctPhone) {
		this.employeeContanctPhone = employeeContanctPhone;
	}

	/**
	 * @return ���� employeeEmail��
	 */
	public String getEmployeeEmail() {
		return employeeEmail;
	}

	/**
	 * @param employeeEmail
	 *            Ҫ���õ� employeeEmail��
	 */
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	/**
	 * @return ���� employeeFamilyName��
	 */
	public String getEmployeeFamilyName() {
		return employeeFamilyName;
	}

	/**
	 * @param employeeFamilyName
	 *            Ҫ���õ� employeeFamilyName��
	 */
	public void setEmployeeFamilyName(String employeeFamilyName) {
		this.employeeFamilyName = employeeFamilyName;
	}

	/**
	 * @return ���� employeeGender��
	 */
	public int getEmployeeGender() {
		return employeeGender;
	}

	/**
	 * @param employeeGender
	 *            Ҫ���õ� employeeGender��
	 */
	public void setEmployeeGender(int employeeGender) {
		this.employeeGender = employeeGender;
	}

	/**
	 * @return ���� employeeGivenName��
	 */
	public String getEmployeeGivenName() {
		return employeeGivenName;
	}

	/**
	 * @param employeeGivenName
	 *            Ҫ���õ� employeeGivenName��
	 */
	public void setEmployeeGivenName(String employeeGivenName) {
		this.employeeGivenName = employeeGivenName;
	}

	/**
	 * @return ���� employeeHomeAddress��
	 */
	public String getEmployeeHomeAddress() {
		return employeeHomeAddress;
	}

	/**
	 * @param employeeHomeAddress
	 *            Ҫ���õ� employeeHomeAddress��
	 */
	public void setEmployeeHomeAddress(String employeeHomeAddress) {
		this.employeeHomeAddress = employeeHomeAddress;
	}

	/**
	 * @return ���� employeeHomePhone��
	 */
	public String getEmployeeHomePhone() {
		return employeeHomePhone;
	}

	/**
	 * @param employeeHomePhone
	 *            Ҫ���õ� employeeHomePhone��
	 */
	public void setEmployeeHomePhone(String employeeHomePhone) {
		this.employeeHomePhone = employeeHomePhone;
	}

	/**
	 * @return ���� employeeID��
	 */
	public long getEmployeeID() {
		return employeeID;
	}

	/**
	 * @param employeeID
	 *            Ҫ���õ� employeeID��
	 */
	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * @return ���� employeeIDNO��
	 */
	public String getEmployeeIDNO() {
		return employeeIDNO;
	}

	/**
	 * @param employeeIDNO
	 *            Ҫ���õ� employeeIDNO��
	 */
	public void setEmployeeIDNO(String employeeIDNO) {
		this.employeeIDNO = employeeIDNO;
	}

	/**
	 * @return ���� employeeJiguan��
	 */
	public String getEmployeeJiguan() {
		return employeeJiguan;
	}

	/**
	 * @param employeeJiguan
	 *            Ҫ���õ� employeeJiguan��
	 */
	public void setEmployeeJiguan(String employeeJiguan) {
		this.employeeJiguan = employeeJiguan;
	}

	/**
	 * @return ���� employeeMarry��
	 */
	public int getEmployeeMarry() {
		return employeeMarry;
	}

	/**
	 * @param employeeMarry
	 *            Ҫ���õ� employeeMarry��
	 */
	public void setEmployeeMarry(int employeeMarry) {
		this.employeeMarry = employeeMarry;
	}

	/**
	 * @return ���� employeeMobie��
	 */
	public String getEmployeeMobie() {
		return employeeMobie;
	}

	/**
	 * @param employeeMobie
	 *            Ҫ���õ� employeeMobie��
	 */
	public void setEmployeeMobie(String employeeMobie) {
		this.employeeMobie = employeeMobie;
	}

	/**
	 * @return ���� employeeNO��
	 */
	public String getEmployeeNO() {
		return employeeNO;
	}

	/**
	 * @param employeeNO
	 *            Ҫ���õ� employeeNO��
	 */
	public void setEmployeeNO(String employeeNO) {
		this.employeeNO = employeeNO;
	}

	/**
	 * @return ���� employeeOfifcePhone��
	 */
	public String getEmployeeOfifcePhone() {
		return employeeOfifcePhone;
	}

	/**
	 * @param employeeOfifcePhone
	 *            Ҫ���õ� employeeOfifcePhone��
	 */
	public void setEmployeeOfifcePhone(String employeeOfifcePhone) {
		this.employeeOfifcePhone = employeeOfifcePhone;
	}

	/**
	 * @return ���� employeePosition��
	 */
	public String getEmployeePosition() {
		return employeePosition;
	}

	/**
	 * @param employeePosition
	 *            Ҫ���õ� employeePosition��
	 */
	public void setEmployeePosition(String employeePosition) {
		this.employeePosition = employeePosition;
	}

	/**
	 * @return ���� employeeWaiyushuiping��
	 */
	public String getEmployeeWaiyushuiping() {
		return employeeWaiyushuiping;
	}

	/**
	 * @param employeeWaiyushuiping
	 *            Ҫ���õ� employeeWaiyushuiping��
	 */
	public void setEmployeeWaiyushuiping(String employeeWaiyushuiping) {
		this.employeeWaiyushuiping = employeeWaiyushuiping;
	}

	/**
	 * @return ���� employeeWenhuachengdu��
	 */
	public int getEmployeeWenhuachengdu() {
		return employeeWenhuachengdu;
	}

	/**
	 * @param employeeWenhuachengdu
	 *            Ҫ���õ� employeeWenhuachengdu��
	 */
	public void setEmployeeWenhuachengdu(int employeeWenhuachengdu) {
		this.employeeWenhuachengdu = employeeWenhuachengdu;
	}

	/**
	 * @return ���� employeeZhengzhimianmao��
	 */
	public int getEmployeeZhengzhimianmao() {
		return employeeZhengzhimianmao;
	}

	/**
	 * @param employeeZhengzhimianmao
	 *            Ҫ���õ� employeeZhengzhimianmao��
	 */
	public void setEmployeeZhengzhimianmao(int employeeZhengzhimianmao) {
		this.employeeZhengzhimianmao = employeeZhengzhimianmao;
	}

	/**
	 * @return ���� employeeZhicheng��
	 */
	public String getEmployeeZhicheng() {
		return employeeZhicheng;
	}

	/**
	 * @param employeeZhicheng
	 *            Ҫ���õ� employeeZhicheng��
	 */
	public void setEmployeeZhicheng(String employeeZhicheng) {
		this.employeeZhicheng = employeeZhicheng;
	}

	/**
	 * @return ���� logonFlag��
	 */
	public int getLogonFlag() {
		return logonFlag;
	}

	/**
	 * @param logonFlag
	 *            Ҫ���õ� logonFlag��
	 */
	public void setLogonFlag(int logonFlag) {
		this.logonFlag = logonFlag;
	}

	/**
	 * @return ���� partnerFlag��
	 */
	public int getPartnerFlag() {
		return partnerFlag;
	}

	/**
	 * @param partnerFlag
	 *            Ҫ���õ� partnerFlag��
	 */
	public void setPartnerFlag(int partnerFlag) {
		this.partnerFlag = partnerFlag;
	}

	/**
	 * @return ���� userPassword��
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            Ҫ���õ� userPassword��
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
