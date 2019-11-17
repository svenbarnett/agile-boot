package com.huijiewei.agile.spring.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BaseDriver {
    public UploadRequest build(Integer fileSize, List<String> fileTypes);

    public String paramName();

    default public UploadResponse upload(String policy, MultipartFile file) {
        throw new RuntimeException("方法未实现");
    }

    default public UploadResponse crop(String policy, String file, Float x, Float y, Float w, Float h) {
        throw new RuntimeException("方法未实现");
    }
}
