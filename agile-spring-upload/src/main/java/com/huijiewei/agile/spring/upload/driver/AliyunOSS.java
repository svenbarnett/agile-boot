package com.huijiewei.agile.spring.upload.driver;

import com.huijiewei.agile.spring.upload.BaseUpload;
import com.huijiewei.agile.spring.upload.UploadRequest;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "agile.spring.upload.aliyun-oss")
public class AliyunOSS extends BaseUpload {
    private String accessKeyId;
    private String accessKeySecret;

    private String endpoint;
    private String bucket;
    private String directory = "";

    @Override
    public UploadRequest build(Integer fileSize, List<String> fileTypes) {
        String url = "https://" + this.bucket + "." + this.endpoint;
        String directory = StringUtils.stripEnd(this.directory, "/") +
                "/" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM")).toString() +
                "/";

        String jsonExpiration = String.format(
                "\"expiration\":\"%s\"",
                LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()
        );

        String jsonContentLengthRange = String.format("[\"content-length-range\",%d,%d]", 0, fileSize);
        String jsonStartWith = String.format("[\"starts-with\",\"$key\",\"%s\"]", directory);

        String jsonConditions = String.format(
                "\"conditions\":[%s,%s]",
                jsonContentLengthRange,
                jsonStartWith
        );

        String policyJson = String.format("{%s,%s}", jsonExpiration, jsonConditions);

        String policyString = this.base64Encode(policyJson);

        String signature = this.base64Encode(this.hmacSHA1(this.accessKeySecret, policyString));

        Map<String, String> params = new HashMap<>();
        params.put("OSSAccessKeyId", this.accessKeyId);
        params.put("key", directory + "${filename}");
        params.put("policy", policyString);
        params.put("signature", signature);
        params.put("success_action_status", "201");

        UploadRequest request = new UploadRequest();
        request.setUrl(url);
        request.setParams(params);
        request.setHeaders(null);
        request.setDataType("xml");
        request.setParamName(this.paramName());
        request.setImageProcess("?x-oss-process=style/");
        request.setResponseParse("return result.querySelector('PostResponse > Location').textContent;");

        return request;
    }

    @Override
    public String paramName() {
        return "file";
    }

    private byte[] hmacSHA1(String key, String input) {
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
