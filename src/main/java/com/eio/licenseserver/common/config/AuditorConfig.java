package com.eio.licenseserver.common.config;

import com.eio.licenseserver.module.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * 审核员自动赋值配置
 * @author alvin
 * @date 2018/8/14
 */
@Configuration
@Profile("!test")
public class AuditorConfig implements AuditorAware<User> {

    @Override
    @NonNull
    public Optional<User> getCurrentAuditor() {
        try {
            Subject subject = SecurityUtils.getSubject();
            User user = (User) subject.getPrincipal();
            return Optional.ofNullable(user);
        } catch (UnavailableSecurityManagerException e) {
            return Optional.empty();
        }
    }
}
