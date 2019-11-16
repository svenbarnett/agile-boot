package com.huijiewei.agile.spring.upload.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload.aliyun-oss")
public class AliyunOSSProperties {
    private String accessKeyId;
    private String accessKeySecret;

    private String endpoint;
    private String bucket;
    private String directory = "";
}
