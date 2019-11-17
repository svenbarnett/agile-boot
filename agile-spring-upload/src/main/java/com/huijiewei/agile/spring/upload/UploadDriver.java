package com.huijiewei.agile.spring.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class UploadDriver implements BaseDriver {
    private UploadDriverProperties properties;
    private ApplicationContext applicationContext;

    @Autowired
    public UploadDriver(UploadDriverProperties properties, ApplicationContext applicationContext) {
        this.properties = properties;
        this.applicationContext = applicationContext;
    }

    private BaseDriver getDriver() {
        return (BaseDriver) this.applicationContext.getBean(this.properties.getDriverClassName());
    }

    @Override
    public UploadRequest build(Integer fileSize, List<String> fileTypes) {
        return this.getDriver().build(fileSize, fileTypes);
    }

    @Override
    public String paramName() {
        return this.getDriver().paramName();
    }

    @Override
    public UploadResponse upload(String policy, MultipartFile file) {
        return this.getDriver().upload(policy, file);
    }

    @Override
    public UploadResponse crop(String policy, String file, Float x, Float y, Float w, Float h) {
        return this.getDriver().crop(policy, file, x, y, w, h);
    }
}
