package com.tusk.priest.common.enums;

import lombok.Getter;

/**
 * 后台返回结果集枚举
 * @author alvin
 * @date 2018/8/14
 */
@Getter
public enum UserStatusEnum{

    /**
     * 通用状态
     */
    NORMAL(1, "正常"),
    FREEZE(2, "冻结"),
    DELETE(3, "删除"),

    ;

    private Integer code;

    private String value;

    UserStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
