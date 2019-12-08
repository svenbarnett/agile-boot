package com.huijiewei.agile.spring.upload.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = TencentCOSProperties.PREFIX)
public class TencentCOSProperties {
    public static final String PREFIX = "agile.spring.upload.tencent-cos";

    private String appId;
    private String secretId;
    private String secretKey;

    private String bucket;
    private String region;
    private String directory = "";
    private String ciHost = "";
    private String ciDelimiter = "/";
}
