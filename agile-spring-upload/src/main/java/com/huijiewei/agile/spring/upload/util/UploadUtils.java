package com.huijiewei.agile.spring.upload.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UploadUtils {
    public static String base64Encode(String input) {
        try {
            return UploadUtils.base64Encode(input.getBytes("utf-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    public static String urlEncode(String url) {
        try {
            return URLEncoder.encode(url, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }

    public static String urlDecode(String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }

    public static String sha1Encode(String input) {
        return DigestUtils.sha1Hex(input);
    }
}
