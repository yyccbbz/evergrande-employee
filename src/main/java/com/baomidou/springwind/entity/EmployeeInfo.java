package com.baomidou.springwind.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 员工贷款信息表
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-11
 */
@TableName("employee_info")
public class EmployeeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 姓名
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
     * 入职时间
     */
	@TableField("entry_date")
	private String entryDate;
    /**
     * 离职时间
     */
	@TableField("quit_date")
	private String quitDate;
    /**
     * 服务年限
     */
	@TableField("service_years")
	private BigDecimal serviceYears;
    /**
     * 公司名
     */
	@TableField("company_name")
	private String companyName;
    /**
     * 在职状态
     */
	@TableField("service_status")
	private String serviceStatus;
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
     * 离职管控方式
     */
	@TableField("leave_control")
	private String leaveControl;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date updateTime;


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

	public BigDecimal getServiceYears() {
		return serviceYears;
	}

	public void setServiceYears(BigDecimal serviceYears) {
		this.serviceYears = serviceYears;
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

	public String getLeaveControl() {
		return leaveControl;
	}

	public void setLeaveControl(String leaveControl) {
		this.leaveControl = leaveControl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
