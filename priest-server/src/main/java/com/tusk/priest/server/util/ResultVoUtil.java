package com.tusk.priest.server.util;


import com.tusk.priest.server.enums.ResultEnum;
import com.tusk.priest.server.vo.Result4Page;
import com.tusk.priest.server.vo.ResultVo;
import com.tusk.priest.server.modules.system.bo.URL;

/**
 * 响应数据(结果)最外层对象工具
 *
 * @author alvin
 * @date 2018/10/15
 */
public class ResultVoUtil {

    public static ResultVo SUCCESS = success("success");

    /**
     * 操作成功
     *
     * @param msg    提示信息
     * @param object 对象
     */
    public static ResultVo success(String msg, Object object) {
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(ResultEnum.SUCCESS.getCode());
        if(null != object) {
            resultVo.setData(object);
        }
        return resultVo;
    }

    /**
     * 操作成功，返回url地址
     *
     * @param msg 提示信息
     * @param url URL包装对象
     */
    public static ResultVo success(String msg, URL url) {
        return success(msg, url.getUrl());
    }

    /**
     * 操作成功，使用默认的提示信息
     *
     * @param object 对象
     */
    public static ResultVo success(Object object) {
        String message = ResultEnum.SUCCESS.getMessage();
        return success(message, object);
    }


    /**
     * layui返回
     * 操作成功，使用默认的提示信息
     *
     * @param object 对象
     */
    public static Result4Page success(Object object, Long count) {
        String message = ResultEnum.LAYUISUCCESS.getMessage();
        return success(message, object, count);
    }

    /**
     * layui 返回
     * @param object 数据列表
     * @param total  数据总数
     * @return
     */
    private static Result4Page success(String msg, Object object, Long total) {
        Result4Page page = new Result4Page();
        page.setMsg(msg);
        page.setCode(ResultEnum.LAYUISUCCESS.getCode());
        page.setTotal(total);
        if(null != object) page.setData(object);

        return page;
    }

    /**
     * 操作成功，返回提示信息，不返回数据
     */
    public static ResultVo success(String msg) {
        Object object = null;
        return success(msg, object);
    }

    /**
     * 操作成功，不返回数据
     */
    public static ResultVo success() {
        return success(null);
    }

    /**
     * 操作有误
     *
     * @param code 错误码
     * @param msg  提示信息
     */
    public static ResultVo error(Integer code, String msg) {
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }

    /**
     * 操作有误，使用默认400错误码
     *
     * @param msg 提示信息
     */
    public static ResultVo error(String msg) {
        Integer code = ResultEnum.ERROR.getCode();
        return error(code, msg);
    }


}
