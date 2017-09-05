package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.utils.StringUtil;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.springwind.entity.MarketingQuery;
import com.baomidou.springwind.service.support.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 员工贷营销查询表 前端控制器
 * </p>
 *
 * @author CuiCan
 * @since 2017-07-13
 */
@Controller
@RequestMapping("/queryMarketing")
public class QueryMarketingController extends BaseController {

    private final String BASE_HTTP_URL = "http://10.127.3.101:8080/dataservice/getconfig.do?id=189";

    private final static int MAX_POST_SIZE = 30 * 1024 * 1024;

    @Autowired
    private HttpAPIService httpAPIService;

    /**
     * excel导出相关
     */
    @Value("${queryMarketing.excelName}")
    private String excelName;
    @Value("${queryMarketing.excelId}")
    private String excelId;
    @Value("${queryMarketing.fields}")
    private String excelFields;

    /**
     * 页面跳转
     */
    @Permission("5001")
    @RequestMapping("/list")
    public String list() {
        return "/queryMarketing/list";
    }

    @Permission("5001")
    @RequestMapping("/search")
    public String search(Model model) {
        return "/queryMarketing/search";
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
//            System.out.println("values = " + values);

            MarketingQuery info = JSONObject.parseObject(values.get(0).toString(), MarketingQuery.class);

            model.addAttribute("user", info);
        }
        return "/queryMarketing/edit";
    }


    /**
     * CRUD
     * http://ds.idc.xiwanglife.com/dataservice/getconfig.do?id=189
     * &employee_name=?&idcard=?&mobile_phone=?&ems_id=?&employee_id=?
     */
    @ResponseBody
    @Permission("5001")
    @RequestMapping(value = "/getUserList")
    public String getUserList(@RequestParam("_search") String _search) {

//        System.err.println("筛选条件 formData =" + _search);

        Page<MarketingQuery> page = getPage();
        int current = page.getCurrent();
        int size = page.getSize();

        MarketingQuery info = new MarketingQuery();
        if (StringUtil.isNotEmpty(_search)) {
            info = JSONObject.parseObject(_search, MarketingQuery.class);
        }

        String url = BASE_HTTP_URL + "&employee_name=" + StringUtil.getStrEmpty(info.getEmployee_name())
                + "&idcard=" + StringUtil.getStrEmpty(info.getIdcard())
                + "&mobile_phone=" + StringUtil.getStrEmpty(info.getMobile_phone())
                + "&ems_id=" + StringUtil.getStrEmpty(info.getEms_id())
                + "&employee_id=" + StringUtil.getStrEmpty(info.getEmployee_id());
//        System.err.println("url =" + url);

        String dataStr = "";
        try {
            dataStr = httpAPIService.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("dataStr = " + dataStr);

        JSONArray values = JSON.parseObject(dataStr, Feature.OrderedField).getJSONObject("details")
                .getJSONObject("list").getJSONArray("values");
//        System.out.println("values = " + values);

        List<MarketingQuery> list = JSONObject.parseArray(values.toJSONString(), MarketingQuery.class);
//        System.out.println("list = " + list);

        //处理分页
        List<MarketingQuery> subList = null;
        if (list != null && list.size() > 0) {
            // 最后一页判断
            if (current == list.size() % size) {
                subList = list.subList((current - 1) * size, list.size());
            }else {
                subList = list.subList((current - 1) * size, current * size - 1);
            }
        }
        page.setTotal(list.size());
        page.setRecords(subList);
        return jsonPage(page);
    }

    /**
     * Excel导出列表
     *
     * @return
     */
    @Permission("5001")
    @RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
    public ModelAndView downloadExcel() {

        String flag = request.getParameter("flag");
        if (StringUtil.isNotEmpty(flag) && flag.equals("0")) {

            String excelFields = "employee_name,idcard,mobile_phone,ems_id,employee_id,entry_date,quit_date,company_name,service_status";
            List<String> fields = Arrays.asList(excelFields.split(","));
            List<MarketingQuery> beans = new ArrayList<>();
            MarketingQuery e = new MarketingQuery();
            e.setEmployee_name("必填");
            e.setIdcard("必填");
            e.setMobile_phone("选填");
            e.setEms_id("选填");
            e.setEmployee_id("选填");
            e.setEntry_date("选填");
            e.setQuit_date("必填");
            e.setCompany_name("选填");
            e.setService_status("已离职或已申请离职");
            beans.add(e);
            return super.exportExcel(excelId, beans, null, fields, "模板");
        }

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
        System.err.println("values = " + values);

        List<EmployeeInfo> beans = JSONObject.parseArray(values.toJSONString(), EmployeeInfo.class);

        return super.exportExcel(excelId, beans, null, fields, excelName);
    }
}
