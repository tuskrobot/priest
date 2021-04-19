package com.eio.licenseserver.module.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap<T> {
    private String msg = "SUCCESS";
    private T data;
    private int code = 0;
    private int count;
}
