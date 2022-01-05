package com.tusk.priest.server.modules.system.controller;

import com.tusk.priest.server.modules.system.domain.Register;
import com.tusk.priest.server.modules.system.param.RegisterParam;
import com.tusk.priest.server.modules.system.service.RegisterService;
import com.tusk.priest.server.util.ResultVoUtil;
import com.tusk.priest.server.vo.Result4Page;
import com.tusk.priest.server.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author alvin
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 主页
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "/register/index";
    }

    /**
     * 数据表格
     * @return
     */
    @PostMapping(value = "/data")
    @ResponseBody
    public Result4Page data(@RequestBody RegisterParam param) {
        List<Register> registerList = registerService.getListGroupByType(param.getType());
        return ResultVoUtil.success(registerList, (long) registerList.size());
    }

}
