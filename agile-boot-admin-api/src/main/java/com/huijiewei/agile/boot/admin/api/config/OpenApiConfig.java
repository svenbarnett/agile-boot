package com.huijiewei.agile.boot.admin.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(defineInfo());
    }

    private Info defineInfo() {
        return new Info()
                .title("Agile Admin API")
                .description("Agile 后台管理 API")
                .contact(new Contact().name("Huijie Wei").email("huijiewei@outlook.com"))
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                .version("v1");
    }
}
