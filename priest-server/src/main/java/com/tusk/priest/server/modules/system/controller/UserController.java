package com.tusk.priest.server.modules.system.controller;

import com.tusk.priest.server.modules.system.param.UserParam;
import com.tusk.priest.server.modules.system.service.UserService;
import com.tusk.priest.server.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
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
