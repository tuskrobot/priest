package com.tusk.priest.vo;

import lombok.Data;

/**
 * 响应数据(结果)最外层对象
 * @author alvin
 * @date 2018/10/15
 */
@Data
public class Result4Page<T> {

    // 状态码
    private Integer code = 0;
    // 提示信息
    private String msg = "success";
    // 响应数据
    private T data;
    // layui需要返回数据count
    private Long total;
}
