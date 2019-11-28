package com.huijiewei.agile.spring.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public abstract class BaseDriver {
    public UploadRequest build(Integer size, List<String> types) {
        UploadRequest request = this.option(size, types);

        request.setSizeLimit(size);
        request.setTypesLimit(types);

        return request;
    }

    abstract protected UploadRequest option(Integer size, List<String> types);

    abstract public String paramName();

    public UploadResponse upload(String policy, MultipartFile file) {
        throw new RuntimeException("方法未实现");
    }

    public UploadResponse crop(String policy, ImageCropRequest request) {
        throw new RuntimeException("方法未实现");
    }
}
