package com.huijiewei.agile.spring.upload.driver;

import com.huijiewei.agile.spring.upload.UploadDriver;
import com.huijiewei.agile.spring.upload.UploadProperties;
import com.huijiewei.agile.spring.upload.UploadRequest;
import com.huijiewei.agile.spring.upload.util.UploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ConditionalOnProperty(prefix = UploadProperties.PREFIX, name = "driver-name", havingValue = AliyunOSS.DRIVER_NAME)
public class AliyunOSS implements UploadDriver {
    public static final String DRIVER_NAME = "aliyun-oss";

    private final AliyunOSSProperties properties;

    @Autowired
    public AliyunOSS(AliyunOSSProperties properties) {
        this.properties = properties;
    }

    @Override
    public UploadRequest option(String identity, Integer size, List<String> types) {
        String url = "https://" + this.properties.getBucket() + "." + this.properties.getEndpoint();
        String directory = StringUtils.stripEnd(this.properties.getDirectory(), "/") +
                "/" +
                UploadUtils.buildMonthName() +
                "/";

        String jsonExpiration = String.format(
                "\"expiration\":\"%s\"",
                LocalDateTime.now().plusMinutes(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")).toString()
        );

        String jsonContentLengthRange = String.format("[\"content-length-range\",%d,%d]", 0, size);
        String jsonStartWith = String.format("[\"starts-with\",\"$key\",\"%s\"]", directory);

        String jsonConditions = String.format(
                "\"conditions\":[%s,%s]",
                jsonContentLengthRange,
                jsonStartWith
        );

        String policyJson = String.format("{%s,%s}", jsonExpiration, jsonConditions);

        String policyString = UploadUtils.base64Encode(policyJson);

        String signature = UploadUtils.base64Encode(this.hmacSHA1(this.properties.getAccessKeySecret(), policyString));

        Map<String, String> params = new HashMap<>();
        params.put("OSSAccessKeyId", this.properties.getAccessKeyId());
        params.put("key", directory + identity + "_${filename}");
        params.put("policy", policyString);
        params.put("signature", signature);
        params.put("success_action_status", "201");

        UploadRequest request = new UploadRequest();
        request.setUrl(url);
        request.setTimeout(9 * 60);
        request.setParams(params);
        request.setHeaders(null);
        request.setDataType("xml");
        request.setParamName(this.paramName());
        request.setImageProcess("return url + '?x-oss-process=style/' + imageStyle");
        request.setResponseParse("return result.querySelector('PostResponse > Location').textContent;");
        request.setSizeLimit(size);
        request.setTypesLimit(types);

        return request;
    }

    @Override
    public String paramName() {
        return "file";
    }

    private byte[] hmacSHA1(String key, String input) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");

            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));

            return mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Unsupported algorithm: HmacSHA1");
        } catch (InvalidKeyException ex) {
            throw new RuntimeException("Invalid key: " + key);
        }
    }
}
