package com.huijiewei.agile.spring.upload.driver;

import com.devskiller.friendly_id.FriendlyId;
import com.huijiewei.agile.spring.upload.BaseDriver;
import com.huijiewei.agile.spring.upload.UploadRequest;
import com.huijiewei.agile.spring.upload.UploadResponse;
import com.huijiewei.agile.spring.upload.util.UploadUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class LocalFile implements BaseDriver {
    private LocalFileProperties properties;

    @Autowired
    public LocalFile(LocalFileProperties properties) {
        this.properties = properties;
    }

    public UploadResponse upload(String policy, MultipartFile file) {
        String policyDecrypt = UploadUtils.urlDecode(policy);
        String policyValue = this.decrypt(policyDecrypt, this.properties.getPolicyKey());

        String[] policies = policyValue.split(";");

        if (policies.length != 4) {
            throw new RuntimeException("上传参数错误");
        }

        long currentTimestamp = System.currentTimeMillis() / 1000L;

        long timestamp = Long.parseLong(policies[0]);

        if (timestamp < currentTimestamp) {
            throw new RuntimeException("参数已过期");
        }

        if (file.isEmpty()) {
            throw new RuntimeException("没有文件被上传");
        }

        int fileSize = Integer.parseInt(policies[1]);

        if (file.getSize() > fileSize) {
            throw new RuntimeException("文件大小超出：" + FileUtils.byteCountToDisplaySize(fileSize));
        }

        List<String> fileTypes = Arrays.asList(policies[2].split(","));

        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (!fileTypes.contains(fileExtension)) {
            throw new RuntimeException("文件类型限制：" + String.join(",", fileTypes));
        }

        String path = this.properties.getUploadPath();

        if (StringUtils.startsWith(path, "file:")) {
            path = StringUtils.stripStart(path, "file:");
        } else {
            path = new File(new File(new ApplicationHome(getClass()).getSource().toString()), path).getAbsolutePath();
        }

        String fileHashName;

        switch (this.properties.getFilenameHash()) {
            case "md5_file":
                try {
                    fileHashName = this.md5(file.getInputStream());
                } catch (Exception ex) {
                    throw new RuntimeException("Error while getInputStream: " + ex.getMessage(), ex);
                }
                break;
            case "original":
                fileHashName = FilenameUtils.getBaseName(file.getOriginalFilename());
                break;
            case "random":
            default:
                fileHashName = FriendlyId.createFriendlyId();

        }

        String fileName = fileHashName + "." + fileExtension;
        String monthName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM")).toString();

        path = StringUtils.stripEnd(path, File.separator) + File.separator + monthName;

        File pathFile = new File(path);

        if (!pathFile.exists()) {
            if (!pathFile.mkdirs()) {
                throw new RuntimeException("服务器创建目录错误:" + path);
            }
        }

        path = path + File.separator + fileName;

        String url = StringUtils.stripEnd(StringUtils.stripEnd(this.properties.getAccessPath(), "*"), "/")
                + "/" + monthName + "/" + fileName;

        try {
            file.transferTo(new File(path));
        } catch (Exception ex) {
            throw new RuntimeException("服务器保存文件错误: " + ex.getMessage(), ex);
        }

        UploadResponse response = new UploadResponse();
        response.setUrl(ServletUriComponentsBuilder.fromCurrentRequest().replacePath(url).replaceQuery("").toUriString());

        return response;
    }

    @Override
    public UploadRequest build(Integer fileSize, List<String> fileTypes) {
        long currentTimestamp = System.currentTimeMillis() / 1000L;

        String policy = String.format("%d;%d;%s;%s", currentTimestamp + 10 * 60, fileSize, String.join(",", fileTypes), true);
        String policyEncrypt = this.encrypt(policy, this.properties.getPolicyKey());
        String policyValue = UploadUtils.urlEncode(policyEncrypt);

        String url = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(this.properties.getUploadAction())
                .replaceQueryParam("policy", policyValue)
                .toUriString();

        String cropUrl = StringUtils.isNotEmpty(this.properties.getCorpAction())
                ? ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(StringUtils.stripStart(this.properties.getUploadAction(), "/"))
                .replaceQueryParam("policy", policyValue)
                .toUriString()
                : null;

        UploadRequest request = new UploadRequest();
        request.setUrl(url);
        request.setTimeout(9 * 60);
        request.setCropUrl(cropUrl);
        request.setParams(null);
        request.setHeaders(null);
        request.setDataType("json");
        request.setParamName(this.paramName());
        request.setImageProcess("");
        request.setResponseParse("return result.url;");

        return request;
    }

    @Override
    public String paramName() {
        return "file";
    }

    private String md5(InputStream data) {
        try {
            return new String(DigestUtils.md5(data));
        } catch (Exception ex) {
            throw new RuntimeException("Error while md5: " + ex.getMessage(), ex);
        }
    }

    private String encrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));

            byte[] encrypt = cipher.doFinal(data.getBytes("utf-8"));

            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception ex) {
            throw new RuntimeException("Error while encrypting: " + ex.getMessage(), ex);
        }
    }

    private String decrypt(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));

            byte[] decrypt = cipher.doFinal(Base64.getDecoder().decode(data));

            return new String(decrypt);
        } catch (Exception ex) {
            throw new RuntimeException("Error while decrypting: " + ex.getMessage(), ex);
        }
    }
}
