package com.tusk.priest.modules.rpc.controller;

import com.tusk.priest.modules.rpc.dto.RegisterDto;
import com.tusk.priest.modules.system.domain.Register;
import com.tusk.priest.modules.system.service.RegisterService;
import com.tusk.priest.pojo.PriestDiscoveryProperties;
import com.tusk.priest.pojo.PriestRegistration;
import com.tusk.priest.util.ResultVoUtil;
import com.tusk.priest.util.ToolUtil;
import com.tusk.priest.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@ResponseBody
@RequestMapping("/priest")
public class PriestController {

    @Autowired
    private RegisterService registerService;

    /**
     * hello
     */
    @ResponseBody
    @GetMapping("/hello")
    public ResultVo hello() {
        return ResultVoUtil.SUCCESS;
    }

    @ResponseBody
    @PostMapping({"/register"})
    public ResponseEntity<Object> register(@RequestBody PriestDiscoveryProperties discoveryProperties) {
        try {
            registerService.register(discoveryProperties);
            return new ResponseEntity(ResultVoUtil.SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(ResultVoUtil.error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @PostMapping({"/heartbeat"})
    public ResponseEntity<Object> heartbeat(@RequestBody PriestDiscoveryProperties discoveryProperties) {
        try {
            /*Register newInfo = new Register(registration);
            List<Register> registerList = registerService.findAll();
            boolean isRegisterInfoExist = false;
            for (Register info : registerList) {
                if(info.equals(newInfo)) {
                    log.debug("registerInfo:" + newInfo + " is exist, skip....");
                    isRegisterInfoExist = true;
                    break;
                }
            }
            if(!isRegisterInfoExist) {
                newInfo.setId(ToolUtil.getUpperRandomString(12));
                newInfo.setCreateDate(new Date());
                registerService.save(newInfo);
            }*/
            return new ResponseEntity(ResultVoUtil.SUCCESS, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(ResultVoUtil.error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }




}
