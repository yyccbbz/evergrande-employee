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
	private String employee_name;
    /**
     * 身份证号
     */
	private String idcard;
    /**
     * 手机号
     */
	@TableField("mobile_phone")
	private String mobile_phone;
    /**
     * EMS号
     */
	@TableField("ems_id")
	private String ems_id;
    /**
     * 员工编码
     */
	@TableField("employee_id")
	private String employee_id;
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
	private String entry_date;
    /**
     * 离职时间
     */
	@TableField("quit_date")
	private String quit_date;
    /**
     * 服务年限
     */
	@TableField("service_years")
	private BigDecimal service_years;
    /**
     * 公司名
     */
	@TableField("company_name")
	private String company_name;
    /**
     * 在职状态
     */
	@TableField("service_status")
	private String service_status;
    /**
     * 贷款状态
     */
	private String phasename;
    /**
     * 贷款剩余应还本金
     */
	@TableField("loan_unfinish_amt")
	private BigDecimal loan_unfinish_amt;
    /**
     * 离职管控方式
     */
	@TableField("leave_control")
	private String leave_control;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date create_time;
    /**
     * 更新时间
     */
	@TableField("update_time")
	private Date update_time;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile_phone() {
		return mobile_phone;
	}

	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}

	public String getEms_id() {
		return ems_id;
	}

	public void setEms_id(String ems_id) {
		this.ems_id = ems_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
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

	public String getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}

	public String getQuit_date() {
		return quit_date;
	}

	public void setQuit_date(String quit_date) {
		this.quit_date = quit_date;
	}

	public BigDecimal getService_years() {
		return service_years;
	}

	public void setService_years(BigDecimal service_years) {
		this.service_years = service_years;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getService_status() {
		return service_status;
	}

	public void setService_status(String service_status) {
		this.service_status = service_status;
	}

	public String getPhasename() {
		return phasename;
	}

	public void setPhasename(String phasename) {
		this.phasename = phasename;
	}

	public BigDecimal getLoan_unfinish_amt() {
		return loan_unfinish_amt;
	}

	public void setLoan_unfinish_amt(BigDecimal loan_unfinish_amt) {
		this.loan_unfinish_amt = loan_unfinish_amt;
	}

	public String getLeave_control() {
		return leave_control;
	}

	public void setLeave_control(String leave_control) {
		this.leave_control = leave_control;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
}
