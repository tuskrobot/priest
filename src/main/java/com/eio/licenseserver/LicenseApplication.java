package com.eio.licenseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.eio")
@EnableCaching
// @EntityListeners(AuditingEntityListener.class)、    @CreatedDate、 LastModifiedDate要使用@EnableJpaAuditing才能生效
@EnableJpaAuditing
public class LicenseApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(LicenseApplication.class, args);
    }

}
