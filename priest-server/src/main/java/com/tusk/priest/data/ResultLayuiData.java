package com.tusk.priest.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultLayuiData<T>{
    private int code=0;
    private String msg="S";
    private Long count; //总条数
    private List<T> data = new ArrayList();
}
