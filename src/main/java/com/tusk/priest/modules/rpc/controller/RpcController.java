package com.tusk.priest.modules.rpc.controller;

import com.tusk.priest.util.ResultVoUtil;
import com.tusk.priest.vo.ResultVo;
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
     * hello
     */
    @ResponseBody
    @GetMapping("/hello")
    public ResultVo hello() {
        return ResultVoUtil.SUCCESS;
    }



}
