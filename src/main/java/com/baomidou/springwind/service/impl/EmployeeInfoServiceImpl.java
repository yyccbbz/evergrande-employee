package com.baomidou.springwind.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.springwind.mapper.EmployeeInfoMapper;
import com.baomidou.springwind.service.IEmployeeInfoService;
import com.baomidou.springwind.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工贷款信息表 服务实现类
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-11
 */
@Service
public class EmployeeInfoServiceImpl extends BaseServiceImpl<EmployeeInfoMapper, EmployeeInfo> implements IEmployeeInfoService {

    @Override
    public Page<EmployeeInfo> selectPageByParams(Page<EmployeeInfo> page, EmployeeInfo employeeInfo) {
        return null;
    }
}
