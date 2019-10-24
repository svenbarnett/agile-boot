package com.huijiewei.agile.boot.admin.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.huijiewei.agile.base.admin"})
@ComponentScan(basePackages = {"com.huijiewei.agile.boot.admin.api", "com.huijiewei.agile.base.admin"})
@EnableJpaRepositories(basePackages = {"com.huijiewei.agile.base.admin"})
public class AdminApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApiApplication.class, args);
    }

}
