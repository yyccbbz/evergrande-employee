package com.baomidou.springwind.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.baomidou.framework.controller.SuperController;
import com.baomidou.framework.mail.MailHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.common.utils.DateUtil;
import com.baomidou.springwind.common.view.SpringMvcExcelView;
import com.baomidou.springwind.excel.ExcelContext;
import com.baomidou.springwind.excel.parsing.ExcelHeader;
import com.baomidou.springwind.service.IPrivilegeService;
import com.baomidou.springwind.service.IUserService;
import com.baomidou.springwind.common.http.HttpAPIService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础控制器
 * </p>
 *
 * @author hubin
 * @Date 2016-04-13
 */
public class BaseController extends SuperController implements HandlerInterceptor {

    @Value("${base_http_url}")
    protected String base_http_url;

    @Resource
    private HttpAPIService httpAPIService;

    @Autowired
    protected MailHelper mailHelper;

    @Autowired
    protected IUserService userService;

    @Autowired
    protected IPrivilegeService privilegeService;

    @Autowired
    protected ExcelContext excelContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        /*
		 * 方法调用后调用该方法，返回数据给请求页
		 */
        if (isLegalView(modelAndView)) {
            modelAndView.addObject("currentUser", userService.selectById(getCurrentUserId()));
            modelAndView.addObject("menuList", privilegeService.selectMenuVOByUserId(getCurrentUserId()));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * 判断是否为合法的视图地址
     * <p>
     *
     * @param modelAndView spring 视图对象
     * @return boolean
     */
    protected boolean isLegalView(ModelAndView modelAndView) {
        boolean legal = false;
        if (modelAndView != null) {
            String viewUrl = modelAndView.getViewName();
            if (viewUrl != null && viewUrl.contains("redirect:")) {
                legal = false;
            } else {
                legal = true;
            }
        }
        return legal;
    }

    /**
     * http请求数据
     *
     * @param id
     * @return
     */
    protected String parseHttpJsonResult(String id) {

        String day = request.getParameter("day");
        String today = DateUtil.thisDate();
        String str = "";

        if (StringUtils.isEmpty(day)) {
            day = today;
        }
//http://ds.idc.xiwanglife.com/dataservice/getconfig.do?id=188&employee_name=?&idcard=?&mobile_phone=?&ems_id=?&employee_id=?
        if (StringUtils.isNotEmpty(id)) {
            String url = base_http_url + id + "&date=" + day;
//            System.err.println("url =" + url);
            try {
                str = httpAPIService.doGet(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONArray values = JSON.parseObject(str, Feature.OrderedField).getJSONObject("details")
                    .getJSONObject("list").getJSONArray("values");
            // 首页，申请，电审，初审，复审，终审，审批汇总--24+2
            String[] strarr1 = {"171", "172", "165", "166", "167", "164", "173"};
            // 签约，放款--24+1
            //String[] strarr2 = {"174","101"};
            // 信审专员--24+0
            //String[] strarr3 = {"100"};

            // 如果当天，那么要看小时数
            if (today.equals(day) && Arrays.asList(strarr1).contains(id) && values != null && values.size() > 0) {
                String thisTime = DateUtil.thisTime();
                String s = thisTime.split(":")[0];
                int num = 24 - Integer.parseInt(s);
                for (int i = 1; i < num; i++) {
                    values.remove(0);
                }
            }
            return values.toJSONString();
        }
        return str;
    }

    /**
     * <p>
     * 转换为 bootstrap-table 需要的分页格式 JSON
     * </p>
     *
     * @param page 分页对象
     * @return
     */
    protected String jsonPage(Page<?> page) {
        JSONObject jo = new JSONObject();
        jo.put("total", page.getTotal());
        jo.put("rows", page.getRecords());
        return toJson(jo);
    }

    @Override
    protected <T> Page<T> getPage(int size) {
        int _size = size, _index = 1;
        if (request.getParameter("_size") != null) {
            _size = Integer.parseInt(request.getParameter("_size"));
        }
        if (request.getParameter("_index") != null) {
            int _offset = Integer.parseInt(request.getParameter("_index"));
            _index = _offset / _size + 1;
        }
        return new Page<T>(_index, _size);
    }

    protected String booleanToString(boolean rlt) {
        return rlt ? "true" : "false";
    }

    protected ModelAndView exportExcel(String id, List<?> beans, ExcelHeader header, List<String> fields, String excelName) {

        /**1.执行你的业务逻辑获取数据，使用ExcelContent生成Workbook，需要四个参数:
         *
         * ①id 配置ID
         * ②beans 配置class对应的List
         * ③header 导出之前,在标题前面做出一些额外的操作,比如增加文档描述等,可以为null
         * ④fields 指定Excel导出的字段(bean对应的字段名称),可以为null
         */
        Workbook workbook = null;
        try {
            workbook = excelContext.createExcel(id, beans, null, fields);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**2.跳转到Excel下载视图*/
        ModelAndView view = new ModelAndView("springMvcExcelView");
        view.addObject(SpringMvcExcelView.EXCEL_NAME, excelName + com.baomidou.springwind.common.utils.DateUtil.getCurrentTime());
        view.addObject(SpringMvcExcelView.EXCEL_WORKBOOK, workbook);
        view.addObject(SpringMvcExcelView.EXCEL_EMPTY_MESSAGE, excelName + " 没有相关数据可以导出");
        return view;
    }


    /**
     * <p>
     * 上传文件存放目录
     * </p>
     */
    protected static String getSaveDir() {
        StringBuffer filePath = new StringBuffer("/opt/upload");
        filePath.append(File.separator);
//        filePath.append(DateUtil.format(new Date(), "yyyy-MM"));
        filePath.append(DateUtil.date2Str(new Date(), "yyyy-MM"));
        filePath.append(File.separator);
        File file = new File(filePath.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * form表单提交 Date类型数据绑定
     * <功能详细描述>
     *
     * @param binder
     * @see [类、类#方法、类#成员]
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
