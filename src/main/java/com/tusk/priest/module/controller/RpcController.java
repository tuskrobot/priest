package com.tusk.priest.module.controller;

import com.tusk.priest.common.util.ResultVoUtil;
import com.tusk.priest.module.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/rpc")
public class RpcController {

    /**
     *
     */
    @ResponseBody
    @GetMapping("/hello")
    public ResultVo hello() {
        return ResultVoUtil.SUCCESS;
    }

}
