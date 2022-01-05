package com.tusk.priest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.tusk.priest")
@EnableCaching
// @EntityListeners(AuditingEntityListener.class)、    @CreatedDate、 LastModifiedDate要使用@EnableJpaAuditing才能生效
//@EnableJpaAuditing
public class PriestApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(PriestApplication.class, args);
    }

}
