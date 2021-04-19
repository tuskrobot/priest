package com.eio.licenseserver.module.controller;

import com.eio.licenseserver.module.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author alvin
 * @date 2018/8/14
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;


    /**
     * 主页
     */
    @GetMapping("/")
    @RequiresPermissions("index")
    public String index(Model model) {
        return "/index";
    }


}
