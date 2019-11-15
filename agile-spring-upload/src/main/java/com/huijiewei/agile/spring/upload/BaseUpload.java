package com.huijiewei.agile.spring.upload;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public abstract class BaseUpload {
    abstract public UploadRequest build(Integer fileSize, List<String> fileTypes);

    abstract public String paramName();

    protected String base64Encode(String input) {
        try {
            return this.base64Encode(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }

    protected String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    protected String UrlDecode(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }

    protected String sha1Encode(String input) {
        return DigestUtils.sha1Hex(input);
    }
}
