package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.utils.StringUtil;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.springwind.service.IEmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 员工贷款信息表 前端控制器
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-11
 */
@Controller
@RequestMapping("/employeeInfo")
public class EmployeeInfoController extends BaseController {

    @Autowired
    private IEmployeeInfoService employeeInfoService;

    /**
     * excel导出相关
     */
    @Value("${employeeInfo.excelName}")
    private String excelName;
    @Value("${employeeInfo.excelId}")
    private String excelId;
    @Value("${employeeInfo.fields}")
    private String excelFields;

    /**
     * 页面跳转
     */
    @Permission("5001")
    @RequestMapping("/list")
    public String list() {
        return "/employeeInfo/list";
    }

    @Permission("5001")
    @RequestMapping("/search")
    public String search(Model model) {
//        model.addAttribute("advisors",
//                advisorService.selectList(new EntityWrapper<Advisor>().eq("is_valid", 1)));
        return "/employeeInfo/search";
    }

    @Permission("5001")
    @RequestMapping("/edit")
    public String edit(Model model, Long id) {
        if (id != null) {
            model.addAttribute("user", employeeInfoService.selectById(id));
        }
        return "/employeeInfo/edit";
    }


    /**
     * CRUD
     */
    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/getUserList")
    public String getUserList(@RequestParam("_search") String _search) {

        System.err.println("筛选条件 formData =" + _search);

        Page<EmployeeInfo> userPage = null;
        Page<EmployeeInfo> page = getPage();
        if (StringUtil.isNotEmpty(_search)) {
            EmployeeInfo employeeInfo = JSONObject.parseObject(_search, EmployeeInfo.class);
            userPage = employeeInfoService.selectPageByParams(page, employeeInfo);
        } else {
            userPage = employeeInfoService.selectPage(page,
                    new EntityWrapper<EmployeeInfo>().orderBy("report_date", false));
        }
        return jsonPage(userPage);
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/editUser")
    public String editUser(EmployeeInfo employee) {
        boolean rlt = false;
        if (employee != null) {
            if (employee.getId() != null) {
                rlt = employeeInfoService.updateById(employee);
            } else {
                employee.setCreateTime(new Date());
                employee.setUpdateTime(employee.getCreateTime());
                rlt = employeeInfoService.insert(employee);
            }
        }
        return callbackSuccess(rlt);
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/delUser/{userId}")
    public String delUser(@PathVariable Long userId) {
        Boolean rlt = employeeInfoService.deleteById(userId);
        return rlt.toString();
    }


    /**
     * Excel导出列表
     *
     * @return
     */
    @Permission("5001")
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
    public ModelAndView downloadExcel() {

        List<String> fields = Arrays.asList(excelFields.split(","));
        List<EmployeeInfo> beans = employeeInfoService.selectList(null);
        return super.exportExcel(excelId, beans, null, fields, excelName);
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("addTestData")
    public String addTestData() {
        ArrayList<EmployeeInfo> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            EmployeeInfo u = new EmployeeInfo();
//            u.setMobileNo(RandomStringUtils.randomNumeric(11));
//            u.setMemberNo(RandomStringUtils.randomAlphanumeric(10));
//            u.setUserName(RandomStringUtils.randomAlphabetic(5));
//            u.setUserType(Integer.parseInt(RandomStringUtils.random(1, new char[]{'1', '2', '3'})));
//            u.setReportDate(DateUtil.randomDate("2017-01-01", "2017-05-01"));
//            u.setRegisterTime(DateUtil.randomDate("2017-01-01", "2017-05-01"));
//            u.setIsVipuser(Integer.parseInt(RandomStringUtils.random(1, new char[]{'0', '1'})));
//            u.setVipDate(DateUtil.randomDate("2017-01-01", "2017-05-01"));
//            u.setAdvisorId(Integer.parseInt(RandomStringUtils.randomNumeric(4)));
//            u.setAdvisorName(RandomStringUtils.randomAlphabetic(6));
//            u.setUserMark(RandomStringUtils.randomAlphanumeric(6));
//            u.setIsPerformancePool(Integer.parseInt(RandomStringUtils.random(1, new char[]{'0', '1'})));
            u.setCreateTime(new Date());
            u.setUpdateTime(u.getCreateTime());
            list.add(u);
            System.err.println(u);
        }
        Boolean b = employeeInfoService.insertBatch(list);
        return b.toString();
    }

}
