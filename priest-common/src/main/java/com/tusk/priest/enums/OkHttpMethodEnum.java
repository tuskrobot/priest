package com.tusk.priest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OkHttpMethodEnum {

    PUT("put", 0),
    POST("post",1),
    GET("get", 2);


    private String name;
    private Integer value;
}
