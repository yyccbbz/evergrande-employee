package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 员工贷营销查询表
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-13
 */
@TableName("query_marketing")
public class QueryMarketing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 员工姓名
     */
	@TableField("employee_name")
	private String employeeName;
    /**
     * 身份证号
     */
	private String idcard;
    /**
     * 手机号
     */
	@TableField("mobile_phone")
	private String mobilePhone;
    /**
     * EMS号
     */
	@TableField("ems_id")
	private String emsId;
    /**
     * 员工编码
     */
	@TableField("employee_id")
	private String employeeId;
    /**
     * 性别
     */
	private String sex;
    /**
     * 年龄
     */
	private String age;
    /**
     * 入职日期
     */
	@TableField("entry_date")
	private String entryDate;
    /**
     * 离职日期
     */
	@TableField("quit_date")
	private String quitDate;
    /**
     * 是否VIP
     */
	@TableField("is_vip")
	private String isVip;
    /**
     * 服务年限
     */
	@TableField("grp_service_years")
	private String grpServiceYears;
    /**
     * 公司名称
     */
	@TableField("company_name")
	private String companyName;
    /**
     * 在职状态
     */
	@TableField("service_status")
	private String serviceStatus;
    /**
     * 特殊名单类型
     */
	@TableField("special_title")
	private String specialTitle;
    /**
     * 是否特殊名单
     */
	@TableField("is_special")
	private String isSpecial;
    /**
     * 贷款申请流水号
     */
	private String serialno;
    /**
     * 贷款申请日期
     */
	private String inputtime;
    /**
     * 贷款状态
     */
	private String phasename;
    /**
     * 贷款剩余应还本金
     */
	@TableField("loan_unfinish_amt")
	private BigDecimal loanUnfinishAmt;
    /**
     * 员工住址
     */
	private String familyAddress;
    /**
     * 员工收入情况
     */
	private String salary;
    /**
     * 申请金额
     */
	@TableField("apply_amt")
	private BigDecimal applyAmt;
    /**
     * 审批完成时间
     */
	@TableField("app_endtime")
	private String appEndtime;
    /**
     * 是否审批拒绝
     */
	@TableField("is_app_reject")
	private String isAppReject;
    /**
     * 审批拒绝原因
     */
	@TableField("app_reject_reason")
	private String appRejectReason;
    /**
     * 审批金额
     */
	@TableField("approval_amt")
	private String approvalAmt;
    /**
     * 签约时间
     */
	private String signdate;
    /**
     * 签约金额
     */
	@TableField("sign_amt")
	private String signAmt;
    /**
     * 放款时间
     */
	private String putoutdate;
    /**
     * 放款金额
     */
	private String putoutamt;
    /**
     * 贷款类型（期数）
     */
	private String businessterm;
    /**
     * 费率-期初服务费
     */
	@TableField("businessrate_cut")
	private String businessrateCut;
    /**
     * 费率-年利率
     */
	@TableField("businessrate_rate")
	private String businessrateRate;
    /**
     * 费率-月管理费
     */
	@TableField("businessrate_fee")
	private String businessrateFee;
    /**
     * 费率-罚息
     */
	@TableField("businessrate_fin")
	private String businessrateFin;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmsId() {
		return emsId;
	}

	public void setEmsId(String emsId) {
		this.emsId = emsId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}

	public String getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(String quitDate) {
		this.quitDate = quitDate;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getGrpServiceYears() {
		return grpServiceYears;
	}

	public void setGrpServiceYears(String grpServiceYears) {
		this.grpServiceYears = grpServiceYears;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getSpecialTitle() {
		return specialTitle;
	}

	public void setSpecialTitle(String specialTitle) {
		this.specialTitle = specialTitle;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getInputtime() {
		return inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getPhasename() {
		return phasename;
	}

	public void setPhasename(String phasename) {
		this.phasename = phasename;
	}

	public BigDecimal getLoanUnfinishAmt() {
		return loanUnfinishAmt;
	}

	public void setLoanUnfinishAmt(BigDecimal loanUnfinishAmt) {
		this.loanUnfinishAmt = loanUnfinishAmt;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public BigDecimal getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(BigDecimal applyAmt) {
		this.applyAmt = applyAmt;
	}

	public String getAppEndtime() {
		return appEndtime;
	}

	public void setAppEndtime(String appEndtime) {
		this.appEndtime = appEndtime;
	}

	public String getIsAppReject() {
		return isAppReject;
	}

	public void setIsAppReject(String isAppReject) {
		this.isAppReject = isAppReject;
	}

	public String getAppRejectReason() {
		return appRejectReason;
	}

	public void setAppRejectReason(String appRejectReason) {
		this.appRejectReason = appRejectReason;
	}

	public String getApprovalAmt() {
		return approvalAmt;
	}

	public void setApprovalAmt(String approvalAmt) {
		this.approvalAmt = approvalAmt;
	}

	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}

	public String getSignAmt() {
		return signAmt;
	}

	public void setSignAmt(String signAmt) {
		this.signAmt = signAmt;
	}

	public String getPutoutdate() {
		return putoutdate;
	}

	public void setPutoutdate(String putoutdate) {
		this.putoutdate = putoutdate;
	}

	public String getPutoutamt() {
		return putoutamt;
	}

	public void setPutoutamt(String putoutamt) {
		this.putoutamt = putoutamt;
	}

	public String getBusinessterm() {
		return businessterm;
	}

	public void setBusinessterm(String businessterm) {
		this.businessterm = businessterm;
	}

	public String getBusinessrateCut() {
		return businessrateCut;
	}

	public void setBusinessrateCut(String businessrateCut) {
		this.businessrateCut = businessrateCut;
	}

	public String getBusinessrateRate() {
		return businessrateRate;
	}

	public void setBusinessrateRate(String businessrateRate) {
		this.businessrateRate = businessrateRate;
	}

	public String getBusinessrateFee() {
		return businessrateFee;
	}

	public void setBusinessrateFee(String businessrateFee) {
		this.businessrateFee = businessrateFee;
	}

	public String getBusinessrateFin() {
		return businessrateFin;
	}

	public void setBusinessrateFin(String businessrateFin) {
		this.businessrateFin = businessrateFin;
	}

}
