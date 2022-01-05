package com.tusk.priest.server.context;

import com.tusk.priest.server.enums.UserStatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 超级管理员常量
 * @author alvin
 * @date 2018/8/14
 */
public class TuskConstant {


    /**
     * 扩展地图属性
     * */
    public static Map<Integer, String> getUserStatusMap() {
        Map<Integer, String> map = new HashMap<>();
        for(UserStatusEnum statusEnum: UserStatusEnum.values()) {
            map.put(statusEnum.getCode(), statusEnum.getValue());
        }

        return map;
    }



}
