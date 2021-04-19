package com.eio.licenseserver.module.controller;

import com.eio.licenseserver.common.context.EioConstant;
import com.eio.licenseserver.module.domain.User;
import com.eio.licenseserver.module.param.LicenseParam;
import com.eio.licenseserver.module.param.UserParam;
import com.eio.licenseserver.module.service.UserService;
import com.eio.licenseserver.module.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author alvin
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 主页
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "/user/index";
    }

    /**
     * 数据表格
     * @return
     */
    @PostMapping(value = "/data")
    @ResponseBody
    public ResultVo data(@RequestBody UserParam param) {
        return userService.getPageList(param);
    }

}
