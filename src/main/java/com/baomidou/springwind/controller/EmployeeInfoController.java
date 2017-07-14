package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.utils.StringUtil;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.springwind.service.IEmployeeInfoService;
import com.baomidou.springwind.service.support.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    private final String BASE_HTTP_URL = "http://ds.idc.xiwanglife.com/dataservice/getconfig.do?id=188";

    @Autowired
    private HttpAPIService httpAPIService;

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
        return "/employeeInfo/search";
    }

    @Permission("5001")
    @RequestMapping("/edit")
    public String edit(Model model, String idcard) {
        if (StringUtil.isNotEmpty(idcard)) {
            String url = BASE_HTTP_URL + "&employee_name=" + "&idcard=" + idcard
                    + "&mobile_phone=" + "&ems_id=" + "&employee_id=";
            String dataStr = "";
            try {
                dataStr = httpAPIService.doGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONArray values = JSON.parseObject(dataStr, Feature.OrderedField).getJSONObject("details")
                    .getJSONObject("list").getJSONArray("values");
            System.out.println("values = " + values);

            //TODO
            Object o = values.get(0);
            System.out.println("o = " + o);

            EmployeeInfo info = (EmployeeInfo) JSONObject.parse(values.get(0).toString());

//            EmployeeInfo info = null;
//            Iterator<Object> it = values.iterator();
//            while(it.hasNext()){
//                JSONObject value = (JSONObject) it.next();
//                System.out.println("value = " + value.toString());
//                info = (EmployeeInfo) JSONObject.parse(value.toString());
//            }

            model.addAttribute("user", info);
        }
        return "/employeeInfo/edit";
    }


    /**
     * CRUD
     * http://ds.idc.xiwanglife.com/dataservice/getconfig.do?id=188
     * &employee_name=&idcard=&mobile_phone=&ems_id=&employee_id=
     */
    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/getUserList")
    public String getUserList(@RequestParam("_search") String _search) {

        System.err.println("筛选条件 formData =" + _search);

        Page<EmployeeInfo> page = getPage();
        int current = page.getCurrent();
        int size = page.getSize();

        EmployeeInfo info = new EmployeeInfo();
        if (StringUtil.isNotEmpty(_search)) {
            info = JSONObject.parseObject(_search, EmployeeInfo.class);
        }

        String url = BASE_HTTP_URL + "&employee_name=" + StringUtil.getStrEmpty(info.getEmployee_name())
                                   + "&idcard=" + StringUtil.getStrEmpty(info.getIdcard())
                                   + "&mobile_phone=" + StringUtil.getStrEmpty(info.getMobile_phone())
                                   + "&ems_id=" + StringUtil.getStrEmpty(info.getEms_id())
                                   + "&employee_id=" + StringUtil.getStrEmpty(info.getEmployee_id());
        System.err.println("url =" + url);

        String dataStr = "";
        try {
            dataStr = httpAPIService.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("dataStr = " + dataStr);

        JSONArray values = JSON.parseObject(dataStr, Feature.OrderedField).getJSONObject("details")
                .getJSONObject("list").getJSONArray("values");
        System.out.println("values = " + values);

        List<EmployeeInfo> list = JSONObject.parseArray(values.toJSONString(), EmployeeInfo.class);
        System.out.println("list = " + list);

        //处理分页
        List<EmployeeInfo> subList = null;
        if(list != null && list.size() >0){
            subList = list.subList((current - 1) * size, current * size - 1);
        }
        page.setTotal(list.size());
        page.setRecords(subList);
        return jsonPage(page);
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("/editUser")
    public String editUser(EmployeeInfo employee) {
        boolean rlt = false;
//        if (employee != null) {
//            if (employee.getId() != null) {
//                rlt = employeeInfoService.updateById(employee);
//            } else {
//                employee.setCreateTime(new Date());
//                employee.setUpdateTime(employee.getCreateTime());
//                rlt = employeeInfoService.insert(employee);
//            }
//        }
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
        String url = BASE_HTTP_URL + "&employee_name=" + "&idcard=" + "&mobile_phone=" + "&ems_id=" + "&employee_id=";
        String dataStr = "";
        try {
            dataStr = httpAPIService.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray values = JSON.parseObject(dataStr, Feature.OrderedField).getJSONObject("details")
                .getJSONObject("list").getJSONArray("values");
        System.out.println("values = " + values);

        List<EmployeeInfo> beans = JSONObject.parseArray(values.toJSONString(), EmployeeInfo.class);

        return super.exportExcel(excelId, beans, null, fields, excelName);
    }

    @ResponseBody
    @Permission("5001")
    @RequestMapping("addTestData")
    public String addTestData() {
        ArrayList<EmployeeInfo> list = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            EmployeeInfo u = new EmployeeInfo();
//            u.setEmployeeName("张三"+i);
//            u.setIdcard(RandomStringUtils.randomNumeric(18));
//            u.setMobilePhone(RandomStringUtils.randomNumeric(11));
//            u.setEmsId(RandomStringUtils.randomNumeric(9));
//            u.setEmployeeId(RandomStringUtils.randomNumeric(10));
//            u.setSex(RandomStringUtils.random(1, new char[]{'2', '1'}));
//            u.setAge(RandomStringUtils.randomNumeric(2));
//            u.setEntryDate(DateUtil.date2Str(DateUtil.randomDate("2017-01-01", "2017-03-12"),DateUtil.DEFAULT_DATE_FORMAT));
//            u.setQuitDate(DateUtil.date2Str(DateUtil.randomDate("2017-04-01", "2017-07-12"),DateUtil.DEFAULT_DATE_FORMAT));
//            u.setServiceYears(new BigDecimal(RandomStringUtils.randomNumeric(2)));
//            u.setCompanyName("恒大子公司"+i);
//            if (i%3 ==0) {
//                u.setServiceStatus("在职");
//            } else if (i%3 ==1) {
//                u.setServiceStatus("离职");
//            } else {
//                u.setServiceStatus("试用期");
//            }
//            u.setPhasename(StringUtils.EMPTY);
//            u.setLoanUnfinishAmt(new BigDecimal(RandomStringUtils.randomNumeric(4)));
//            u.setLeaveControl(StringUtils.EMPTY);
//            u.setCreateTime(new Date());
//            u.setUpdateTime(u.getCreateTime());
//
//            list.add(u);
//            System.err.println(u);
//        }
        Boolean b = employeeInfoService.insertBatch(list);
        return b.toString();
    }

}
