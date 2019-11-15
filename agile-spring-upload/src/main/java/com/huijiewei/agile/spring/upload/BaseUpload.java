package com.huijiewei.agile.spring.upload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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

    protected String jsonEncode(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return "";
        }
    }

    protected byte[] hmacSHA1(String key, String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");

            mac.init(new SecretKeySpec(key.getBytes("utf-8"), "HmacSHA1"));

            return mac.doFinal(input.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Unsupported algorithm: HmacSHA1");
        } catch (InvalidKeyException ex) {
            throw new RuntimeException("Invalid key: " + key);
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unsupported charset: " + ex.getMessage());
        }
    }
}
