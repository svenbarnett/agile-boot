package com.huijiewei.agile.spring.upload;

import lombok.Data;

import java.util.Map;

@Data
public class UploadRequest {
    private String url;

    private String paramName;

    private String dataType;

    private Map<String, String> params;

    private Map<String, String> headers;

    private String imageProcess;

    private String responseParse;
}
