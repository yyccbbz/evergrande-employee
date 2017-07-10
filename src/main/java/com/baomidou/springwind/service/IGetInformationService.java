package com.baomidou.springwind.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.GetInformation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 获客信息 服务类
 * </p>
 *
 * @author CuiCan
 * @since 2017-06-15
 */
public interface IGetInformationService extends IService<GetInformation> {

    Page<GetInformation> selectPageByParams(Page<GetInformation> page, GetInformation getInformation);

    void deleteAll();
}
