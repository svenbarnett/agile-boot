package com.huijiewei.agile.spring.upload.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UploadUtils {
    public static String base64Encode(String input) {
        return UploadUtils.base64Encode(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    public static String urlEncode(String url) {
        return URLEncoder.encode(url, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String url) {
        return URLDecoder.decode(url, StandardCharsets.UTF_8);
    }

    public static String sha1Encode(String input) {
        return DigestUtils.sha1Hex(input);
    }

    public static String buildMonthName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    public static String buildAbsoluteAccessPathUrl(String accessPath) {
        String replaceUrl = StringUtils
                .stripEnd(StringUtils
                        .stripEnd(accessPath, "*"), "/");

        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath(replaceUrl)
                .replaceQuery("")
                .toUriString();
    }

    public static String buildAbsoluteUploadPath(String uploadPath) {
        String absolutePath;

        if (StringUtils.startsWith(uploadPath, "file:")) {
            absolutePath = StringUtils.stripStart(uploadPath, "file:");
        } else {
            absolutePath = Paths.get(
                    new ApplicationHome(UploadUtils.class)
                            .getSource()
                            .getParentFile()
                            .toString(),
                    uploadPath)
                    .toAbsolutePath()
                    .normalize()
                    .toString();
        }

        return StringUtils.stripEnd(absolutePath, File.separator) + File.separator;
    }
}
