package com.tusk.priest.common.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 项目-shiro会话配置项
 * @author alvin
 * @date 2018/11/6
 */
@Data
@Component
//@ConfigurationProperties(prefix = "project.swagger")
public class ShiroProjectProperties {
    // cookie记住登录信息时间，默认7天
    private Integer rememberMeTimeout = 7;
    // Session会话超时时间，默认30分钟
    private Integer globalSessionTimeout = 3600;
    // Session会话检测间隔时间，默认15分钟
    private Integer sessionValidationInterval = 900;
}
