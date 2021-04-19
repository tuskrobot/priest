package com.eio.licenseserver.module.domain;


import com.eio.licenseserver.common.util.DateUtil;
import com.eio.licenseserver.module.param.LicenseParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="sys_license")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class License implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(30) comment '项目名称'")
    private String projectName;
    @CreatedDate
    private Date createDate;
    // 解决RequestBody解析时间String类型报错
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireDate;
    private Integer expiryTime;
    @LastModifiedDate
    private Date updateDate;
    @Column(columnDefinition = "text comment '备注'")
    private String remark;
    @Column(columnDefinition = "integer(3) comment '1-启用 2-激活 3-过期 4-禁用'")
    private Integer status;
    @Column(columnDefinition = "text comment 'EIO生成的令牌'")
    private String token;
    @Column(columnDefinition = "text comment 'LICENSE根据令牌生成注册码'")
    private String register;


    public License(LicenseParam.BaseParam param) {
        this.projectName = param.getProjectName();
        this.registerDate = param.getRegisterDate();
        this.expireDate = param.getExpireDate();
        this.token = param.getToken();
        this.status = 1;
        this.expiryTime =Integer.valueOf(DateUtil.seconds(param.getExpireDate(), param.getRegisterDate()));
    }
}
