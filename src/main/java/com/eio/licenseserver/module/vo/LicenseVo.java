package com.eio.licenseserver.module.vo;

import com.eio.licenseserver.common.constant.LicenseConstant;
import com.eio.licenseserver.common.util.DateUtil;
import com.eio.licenseserver.module.domain.License;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseVo implements Serializable {

    private Long id;
    private String projectName;
    private Date createDate;
    private Date registerDate;
    private Date expireDate;
    // 编辑页面使用
    private String expireDateStr;
    private Date expiryTime;
    private String expiryTimeName;
    private Date updateDate;
    private String remark;
    private Integer status;
    private String statusName;
    private String token;
    private String register;

    public LicenseVo(License license) {
        this.id = license.getId();
        this.projectName = license.getProjectName();
        this.createDate = license.getCreateDate();
        this.registerDate = license.getRegisterDate();
        this.expireDate = license.getExpireDate();
        this.expireDateStr = DateUtil.formatTime(license.getExpireDate());
        this.expiryTimeName = getExpiryTimeName(license.getExpireDate());
        this.updateDate = license.getUpdateDate();
        this.remark = license.getRemark();
        this.status = license.getStatus();
        this.statusName = LicenseConstant.statusMap.get(license.getStatus());
        this.register = license.getRegister();
        this.token = license.getToken();
    }

    private String getExpiryTimeName(Date expireDate) {
        String expiryTimeName;
        String tag;
        int time;
        if (0 != DateUtil.days(expireDate, new Date())) {
            time = DateUtil.days(expireDate, new Date());
            tag = "天";
        } else if (0 != DateUtil.hours(expireDate, new Date())) {
            time = DateUtil.hours(expireDate, new Date());
            tag = "小时";
        } else if (0 != DateUtil.minutes(expireDate, new Date())) {
            time = DateUtil.minutes(expireDate, new Date());
            tag = "分钟";
        } else {
            time = DateUtil.seconds(expireDate, new Date());
            tag = "秒";
        }
        expiryTimeName = time + tag;
        return expiryTimeName;
    }
}
