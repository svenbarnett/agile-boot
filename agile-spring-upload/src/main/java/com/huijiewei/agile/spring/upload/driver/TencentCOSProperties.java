package com.huijiewei.agile.spring.upload.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload.tencent-cos")
public class TencentCOSProperties {
    private String appId;
    private String secretId;
    private String secretKey;

    private String bucket;
    private String region;
    private String directory = "";
}
