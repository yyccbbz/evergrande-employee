package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.framework.upload.UploadFile;
import com.baomidou.framework.upload.UploadMsg;
import com.baomidou.framework.upload.UploadMultipartRequest;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.http.HttpResult;
import com.baomidou.springwind.common.utils.StringUtil;
import com.baomidou.springwind.entity.EmployeeInfo;
import com.baomidou.springwind.excel.result.ExcelImportResult;
import com.baomidou.springwind.service.IEmployeeInfoService;
import com.baomidou.springwind.service.support.HttpAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

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

    private final static int MAX_POST_SIZE = 30 * 1024 * 1024;


    @Autowired
    private IEmployeeInfoService employeeInfoService;

    @Autowired
    private HttpAPIService httpAPIService;

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
     * sms
     */
    @Value("${sms.mobiles}")
    private String mobiles;
    @Value("${sms.content}")
    private String content;

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
//            System.out.println("values = " + values);

            EmployeeInfo info = JSONObject.parseObject(values.get(0).toString(),EmployeeInfo.class);

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

//        System.err.println("筛选条件 formData =" + _search);

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

        List<EmployeeInfo> list = JSONObject.parseArray(values.toJSONString(), EmployeeInfo.class);
//        System.out.println("list = " + list);

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
        if (employee != null) {
            employee.setCreate_time(new Date());
            employee.setUpdate_time(employee.getCreate_time());
            rlt = employeeInfoService.insert(employee);
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

        String flag = request.getParameter("flag");
        if (StringUtil.isNotEmpty(flag) && flag.equals("0")) {

            String excelFields = "employee_name,idcard,mobile_phone,ems_id,employee_id,entry_date,quit_date,company_name,service_status";
            List<String> fields = Arrays.asList(excelFields.split(","));
            List<EmployeeInfo> beans = new ArrayList<>();
            EmployeeInfo e = new EmployeeInfo();
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
//        System.err.println("values = " + values);

        List<EmployeeInfo> beans = JSONObject.parseArray(values.toJSONString(), EmployeeInfo.class);

        return super.exportExcel(excelId, beans, null, fields, excelName);
    }

    /**
     * Excel导入
     */
    @ResponseBody
    @Permission("5003")
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    public UploadMsg uploadExcelFile() {

//        assignReportImportUserService.deleteAll();

        UploadMsg msg = new UploadMsg();
        try {
            UploadMultipartRequest umr = new UploadMultipartRequest(request, getSaveDir(), MAX_POST_SIZE);
            umr.setFileHeaderExts("504b03.xlsx");
            umr.upload();
            Enumeration<?> files = umr.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                UploadFile cf = umr.getUploadFile(name);
                if (cf != null) {
                    /**
                     * 上传成功
                     */
                    if (cf.isSuccess()) {
                        msg.setSuccess(true);
                        msg.setUrl(cf.getFileUrl());
                        msg.setSize(cf.getSize());
                    }
                    msg.setMsg(cf.getUploadCode().desc());
                    /**读取Excel内容，进行写表*/
//                    Excel excel = new Excel();
//                    excel.setExcelName(cf.getOriginalFileName());
//                    excel.setExcelRealName(cf.getFilesystemName());
//                    excel.setExcelRealPath(cf.getFileUrl());
//                    excel.setUid(0L);
//                    excel.setCreateTime(new Date());
//                    excelService.insert(excel);

                    FileInputStream excelStream = new FileInputStream(cf.getFileUrl());
                    ExcelImportResult readExcel = excelContext.readExcel(excelId, excelStream);
                    List<EmployeeInfo> listBean = readExcel.getListBean();

                    employeeInfoService.insertBatch(listBean);
                }
            }
        } catch (IOException e) {
            logger.error("uploadFile error. ", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("msg = " + toJson(msg));
        return msg;

    }


    @ResponseBody
    @Permission("5001")
    @RequestMapping("addTestData")
    public String addTestData() {
        ArrayList<EmployeeInfo> list = new ArrayList<>();
        Boolean b = employeeInfoService.insertBatch(list);
        return b.toString();
    }


    @ResponseBody
    @Permission("5001")
    @RequestMapping("send")
    public String sendSms() {
//        String ip = "118.145.22.172";
//        String port = "9888";
//        String userId = "3047";
//        String Password = "HDjf_170901";
//        String mobiles = "13681691680,18018558787,13918010283,13585952427,13918487436,18637010312";
//        String content = "尊敬的用户，这是一条北京东方般若短信平台的测试短信，如果收到此信息，说明群发成功。";

        String base_url = "http://118.145.22.172:9888/smsservice/SendSMS?UserId=3047&Password=HDjf_170901&";

        String result = "";
        HttpResult httpResult = null;

        /**
         * http://118.145.22.172:9888/smsservice/SendSMS?UserId=xxx&Password=xxx&Mobiles=13121848041&Content=hello&SenderAddr=123
         */
        try {
            content = URLEncoder.encode(content, "GBK");
            System.out.println("content = " + content);

            String url = base_url + "&Mobiles=" + mobiles + "&Content=" + content;
            System.out.println("url = " + url);

//            result = httpAPIService.doGetGBK(url);
            httpResult = httpAPIService.doPost(url);
            System.out.println("result = " + result);
            System.out.println("httpResult = " + httpResult.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toJson(httpResult);
    }



}
