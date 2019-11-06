package com.huijiewei.agile.boot.admin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EntityScan(basePackages = "com.huijiewei.agile")
@ComponentScan(basePackages = "com.huijiewei.agile")
@EnableJpaRepositories(basePackages = "com.huijiewei.agile")
public class AdminApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }

}
