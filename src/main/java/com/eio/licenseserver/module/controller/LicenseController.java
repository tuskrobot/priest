package com.eio.licenseserver.module.controller;

import com.alibaba.fastjson.JSON;
import com.eio.licenseserver.common.context.EioConstant;
import com.eio.licenseserver.common.exception.ResultException;
import com.eio.licenseserver.common.util.ResultVoUtil;
import com.eio.licenseserver.module.param.LicenseParam;
import com.eio.licenseserver.module.service.LicenseService;
import com.eio.licenseserver.module.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author alvin
 * @date 2018/8/14
 */
@Controller
@RequestMapping("/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;


    /**
     * 主页
     */
    @GetMapping("/index")
    public String index(Model model) {
        return "/license/index";
    }


    /**
     * 数据表格
     * @return
     */
    @PostMapping(value = "/data")
    @ResponseBody
    public ResultVo data(@RequestBody LicenseParam param) {
        return licenseService.getPageList(param);
    }

    /**
     * 授权
     */
    @GetMapping("/genRegister")
    @ResponseBody
    public ResultVo genRegister(@RequestParam(value = "id") Long id) {
        try {
            return licenseService.genRegister(id);
        } catch (Exception e) {
            return ResultVoUtil.error("授权失败");
        }
    }

    /**
     * 添加页面
     */
    @GetMapping("/add")
    public String add() {
        return "/license/add";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
    public ResultVo save(@RequestBody LicenseParam.BaseParam param) {
        return licenseService.save(param);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultVo delete(@RequestParam(value = "id") String id) {
        try {
            licenseService.delete(Long.parseLong(id));
            return ResultVoUtil.success("删除成功");
        } catch (ResultException e) {
            e.printStackTrace();
            return ResultVoUtil.error("删除失败");
        }
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @ResponseBody
    public ResultVo update(@RequestBody LicenseParam.BaseParam param) {
        return licenseService.update(param);
    }


    /**
     * 编辑
     */
    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id") Long id) {
        model.addAttribute("licenseVo", licenseService.edit(id));
        return "/license/eidt";
    }

    /**
     * 禁用
     */
    @GetMapping("/disable")
    @ResponseBody
    public ResultVo disable(@RequestParam(value = "id") Long id) {
        return licenseService.disable(id);
    }

    /**
     * 获取所有项目的名称
     */
    @GetMapping("/getProjectNames")
    @ResponseBody
    public ResultVo<String> getProjectNames() {
        return ResultVoUtil.success("查询成功", JSON.toJSONString(licenseService.getProjectNames()));
    }

}
