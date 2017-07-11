package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 员工贷款信息表 服务类
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-11
 */
public interface IEmployeeInfoService extends IService<EmployeeInfo> {

    Page<EmployeeInfo> selectPageByParams(Page<EmployeeInfo> page, EmployeeInfo employeeInfo);
}
