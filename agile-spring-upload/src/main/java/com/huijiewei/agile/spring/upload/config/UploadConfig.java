package com.huijiewei.agile.spring.upload.config;

import com.huijiewei.agile.spring.upload.driver.LocalFileProperties;
import com.huijiewei.agile.spring.upload.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UploadConfig implements WebMvcConfigurer {
    private LocalFileProperties properties;

    @Autowired
    public UploadConfig(LocalFileProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absoluteUploadPath = UploadUtils.buildAbsoluteUploadPath(this.properties.getUploadPath());

        registry.addResourceHandler(this.properties.getAccessPath())
                .addResourceLocations("file:" + absoluteUploadPath)
                .resourceChain(false);
    }
}
