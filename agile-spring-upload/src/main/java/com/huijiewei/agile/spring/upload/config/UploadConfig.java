package com.huijiewei.agile.spring.upload.config;

import com.huijiewei.agile.spring.upload.driver.LocalFileProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Paths;

@Configuration
public class UploadConfig implements WebMvcConfigurer {
    private LocalFileProperties properties;

    @Autowired
    public UploadConfig(LocalFileProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = this.properties.getUploadPath();

        if (StringUtils.startsWith(path, "file:")) {
            path = StringUtils.stripEnd(this.properties.getUploadPath(), File.separator) + File.separator;
        } else {
            path = Paths.get(new ApplicationHome(getClass()).getSource().getParentFile().toString(), path).toAbsolutePath().normalize().toString();
            path = "file:" + StringUtils.stripEnd(path, File.separator) + File.separator;
        }

        registry.addResourceHandler(this.properties.getAccessPath())
                .addResourceLocations(path)
                .resourceChain(false);
    }
}
