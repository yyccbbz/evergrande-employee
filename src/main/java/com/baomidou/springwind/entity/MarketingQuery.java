package com.baomidou.springwind.entity;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Author: CuiCan
 * Date: 2017-8-21
 * Time: 11:13
 * Description: 营销查询员工信息
 */
public class MarketingQuery implements Serializable {

    private static final long serialVersionUID = 5284885265336290241L;

    /**
     * 姓名
     */
    private String employee_name;
    /**
     * 身份证号
     */
    private String idcard;
    /**
     * 手机号
     */
    private String mobile_phone;
    /**
     * EMS号
     */
    private String ems_id;
    /**
     * 员工编码
     */
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
    private String entry_date;
    /**
     * 离职时间
     */
    private String quit_date;
    /**
     * 是否VIP
     */
    private String is_vip;
    /**
     * 服务年限
     */
    private String grp_service_years;
    /**
     * 公司名
     */
    private String company_name;
    /**
     * 在职状态
     */
    private String service_status;
    /**
     * 特殊名单类型
     */
    private String special_title;
    /**
     * 是否特殊名单
     */
    private String is_special;
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
    private String loan_unfinish_amt;
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
    private String businesssumasapply_amt;
    /**
     * 审批完成时间
     */
    private String app_endtime;
    /**
     * 是否审批拒绝
     */
    private String is_app_reject;
    /**
     * 审批拒绝原因
     */
    private String app_reject_reason;
    /**
     * 审批金额
     */
    private String approval_amt;
    /**
     * 签约时间
     */
    private String signdate;
    /**
     * 签约金额
     */
    private String sign_amt;
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
    private String businessrate_cut;
    /**
     * 费率-年利率
     */
    private String businessrate_rate;
    /**
     * 费率-月管理费
     */
    private String businessrate_fee;
    /**
     * 费率-罚息
     */
    private String businessrate_fin;


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

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getGrp_service_years() {
        return grp_service_years;
    }

    public void setGrp_service_years(String grp_service_years) {
        this.grp_service_years = grp_service_years;
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

    public String getSpecial_title() {
        return special_title;
    }

    public void setSpecial_title(String special_title) {
        this.special_title = special_title;
    }

    public String getIs_special() {
        return is_special;
    }

    public void setIs_special(String is_special) {
        this.is_special = is_special;
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

    public String getLoan_unfinish_amt() {
        return loan_unfinish_amt;
    }

    public void setLoan_unfinish_amt(String loan_unfinish_amt) {
        this.loan_unfinish_amt = loan_unfinish_amt;
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

    public String getBusinesssumasapply_amt() {
        return businesssumasapply_amt;
    }

    public void setBusinesssumasapply_amt(String businesssumasapply_amt) {
        this.businesssumasapply_amt = businesssumasapply_amt;
    }

    public String getApp_endtime() {
        return app_endtime;
    }

    public void setApp_endtime(String app_endtime) {
        this.app_endtime = app_endtime;
    }

    public String getIs_app_reject() {
        return is_app_reject;
    }

    public void setIs_app_reject(String is_app_reject) {
        this.is_app_reject = is_app_reject;
    }

    public String getApp_reject_reason() {
        return app_reject_reason;
    }

    public void setApp_reject_reason(String app_reject_reason) {
        this.app_reject_reason = app_reject_reason;
    }

    public String getApproval_amt() {
        return approval_amt;
    }

    public void setApproval_amt(String approval_amt) {
        this.approval_amt = approval_amt;
    }

    public String getSigndate() {
        return signdate;
    }

    public void setSigndate(String signdate) {
        this.signdate = signdate;
    }

    public String getSign_amt() {
        return sign_amt;
    }

    public void setSign_amt(String sign_amt) {
        this.sign_amt = sign_amt;
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

    public String getBusinessrate_cut() {
        return businessrate_cut;
    }

    public void setBusinessrate_cut(String businessrate_cut) {
        this.businessrate_cut = businessrate_cut;
    }

    public String getBusinessrate_rate() {
        return businessrate_rate;
    }

    public void setBusinessrate_rate(String businessrate_rate) {
        this.businessrate_rate = businessrate_rate;
    }

    public String getBusinessrate_fee() {
        return businessrate_fee;
    }

    public void setBusinessrate_fee(String businessrate_fee) {
        this.businessrate_fee = businessrate_fee;
    }

    public String getBusinessrate_fin() {
        return businessrate_fin;
    }

    public void setBusinessrate_fin(String businessrate_fin) {
        this.businessrate_fin = businessrate_fin;
    }
}
