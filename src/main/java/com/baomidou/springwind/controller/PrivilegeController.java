package com.baomidou.springwind.controller;

import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.Privilege;
import com.baomidou.springwind.entity.User;
import com.baomidou.springwind.service.IPrivilegeService;
import com.baomidou.springwind.service.IRolePrivilegeService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author CuiCan
 * @since 2017-05-17
 */
@Controller
@RequestMapping("/perm/permission")
public class PrivilegeController extends BaseController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private IPrivilegeService privilegeService;

    @Autowired
    private IRolePrivilegeService rolePrivilegeService;

    @Permission("2003")
    @RequestMapping("/list")
    public String list(Model model) {
        return "/permission/list";
    }

    @Permission("2003")
    @RequestMapping("/edit")
    public String edit(Model model, Long id) {
        if (id != null) {
            model.addAttribute("permission", privilegeService.selectById(id));
        }
        return "/permission/edit";
    }

    @ResponseBody
    @Permission("2003")
    @RequestMapping("/editPermission")
    public String editPermission(Privilege permission) {
        boolean rlt = false;
        if (permission != null) {
            if (permission.getId() != null) {
                rlt = privilegeService.updateById(permission);
            } else {
                rlt = privilegeService.insertAllColumn(permission);
            }
        }
        return callbackSuccess(rlt);
    }

    @ResponseBody
    @Permission("2003")
    @RequestMapping("/getPermissionList")
    public String getPermissionList() {
        Page<Privilege> page = getPage();
        return jsonPage(privilegeService.selectPage(page, new EntityWrapper<Privilege>().orderBy("id")));
    }

    @ResponseBody
    @Permission("2003")
    @RequestMapping("/delete/{permId}")
    public String delete(@PathVariable Long permId) {
        boolean exist = rolePrivilegeService.existRolePermission(permId);
        if (exist) {
            return "false";
        }
        return booleanToString(privilegeService.deleteById(permId));
    }

    @ResponseBody
    @Permission("2003")
    @RequestMapping("/flush")
    public String flush() {

//        System.err.println("token = " + getSSOToken().jsonToken());

        Cache cache = cacheManager.getCache("permissionCache");

        List<User> users = userService.selectList(null);

        if (users != null && users.size() > 0) {
            for (User user : users) {
                cache.remove(user.getId());
            }
        }
        return Boolean.TRUE.toString();
    }

}

