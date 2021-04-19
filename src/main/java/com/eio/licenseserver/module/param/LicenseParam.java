package com.eio.licenseserver.module.param;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseParam extends PageParam{

    private BaseParam param;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BaseParam {
        private Long id;
        private String projectName;
        private Date createDate;
        // 解决String->date报错
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date registerDate;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private Date expireDate;
        private Integer expiryTime;
        private Date updateDate;
        private String remark;
        private Integer status;
        private String mac;
        private String token;

        BaseParam(String queryJson) {
            BaseParam licenseParam = JSONObject.parseObject(queryJson, BaseParam.class);
            this.projectName = licenseParam.projectName;
            this.registerDate = licenseParam.registerDate;
            this.expireDate = licenseParam.expireDate;
        }

    }


}
