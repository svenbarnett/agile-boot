package com.huijiewei.agile.spring.upload.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = LocalFileProperties.PREFIX)
public class LocalFileProperties {
    public static final String PREFIX = "agile.spring.upload.local-file";

    private String uploadPath;
    private String accessPath;
    private String corpAction;
    private String uploadAction;
    private String policyKey;
}
