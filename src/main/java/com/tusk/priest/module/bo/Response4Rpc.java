package com.tusk.priest.module.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response4Rpc {
    private Integer code = 200;
    private String message = "SUCCESS";
}
