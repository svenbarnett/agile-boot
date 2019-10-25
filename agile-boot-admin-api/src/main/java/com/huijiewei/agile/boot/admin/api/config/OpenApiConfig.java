package com.huijiewei.agile.boot.admin.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(defineInfo())
                .components(new Components()
                        .addSchemas("NotFoundProblem", defineNotFoundProblemSchema())
                        .addSchemas("BadRequestProblem", defineBadRequestProblemSchema())
                        .addSchemas("ConstraintViolationProblem", defineConstraintViolationProblemSchema())
                );
    }

    private Schema defineNotFoundProblemSchema() {
        return new Schema()
                .type("object")
                .addProperties("status", new Schema().type("integer"))
                .addProperties("title", new Schema().type("string"))
                .addProperties("detail", new Schema().type("string"));
    }

    private Schema defineBadRequestProblemSchema() {
        return new Schema()
                .type("object")
                .addProperties("status", new Schema().type("integer"))
                .addProperties("title", new Schema().type("string"))
                .addProperties("detail", new Schema().type("string"));
    }

    private Schema defineConstraintViolationProblemSchema() {
        return new Schema()
                .type("object")
                .addProperties("type", new Schema().type("string"))
                .addProperties("status", new Schema().type("integer"))
                .addProperties("title", new Schema().type("string"))
                .addProperties(
                        "violations",
                        new ArraySchema()
                                .items(new Schema<>()
                                        .addProperties("field", new Schema().type("string"))
                                        .addProperties("message", new Schema().type("string"))
                                )
                );
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
