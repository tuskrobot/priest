package com.tusk.priest.enums;

import lombok.Getter;

@Getter
public enum LicenseStatusEnum {
    //1-启用 2-激活 3-过期 4-禁用

    ENABLED(1, "启用"),
    REGISTERED(2, "激活"),
    EXPIRED(3, "过期"),
    DISABLED(4, "禁用");

    private Integer code;
    private String value;

    LicenseStatusEnum(Integer code, String value){
        this.code = code;
        this.value = value;
    };


}
