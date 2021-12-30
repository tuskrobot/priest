package com.tusk.priest.common.constant;

import com.tusk.priest.common.enums.LicenseStatusEnum;

import java.util.HashMap;
import java.util.Map;

public class LicenseConstant {

    public final static Map<Integer, String> statusMap = new HashMap<>();

    static{
        statusMap.put(LicenseStatusEnum.ENABLED.getCode(), LicenseStatusEnum.ENABLED.getValue());
        statusMap.put(LicenseStatusEnum.REGISTERED.getCode(), LicenseStatusEnum.REGISTERED.getValue());
        statusMap.put(LicenseStatusEnum.EXPIRED.getCode(), LicenseStatusEnum.EXPIRED.getValue());
        statusMap.put(LicenseStatusEnum.DISABLED.getCode(), LicenseStatusEnum.DISABLED.getValue());
    }
}