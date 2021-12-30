package com.tusk.priest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.tusk.priest")
@EnableCaching
// @EntityListeners(AuditingEntityListener.class)、    @CreatedDate、 LastModifiedDate要使用@EnableJpaAuditing才能生效
//@EnableJpaAuditing
public class PriestApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(PriestApplication.class, args);
    }

}
