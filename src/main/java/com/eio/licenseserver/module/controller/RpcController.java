package com.eio.licenseserver.module.controller;

import com.eio.licenseserver.common.util.ResultVoUtil;
import com.eio.licenseserver.module.vo.ResultVo;
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
    @GetMapping("/getLicense")
    public ResultVo getLicense() {
        return ResultVoUtil.SUCCESS;
    }

}
