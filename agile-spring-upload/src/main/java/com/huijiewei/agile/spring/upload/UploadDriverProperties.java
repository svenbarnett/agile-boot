package com.huijiewei.agile.spring.upload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload")
public class UploadDriverProperties {
    private String driverClassName;
}
