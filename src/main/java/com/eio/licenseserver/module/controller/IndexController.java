package com.eio.licenseserver.module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/index")
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
