package com.huijiewei.agile.core.until;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String passwordEncode(String password) {
        return SecurityUtils.passwordEncoder.encode(password);
    }

    public static boolean passwordMatches(String password, String encodePassword) {
        return SecurityUtils.passwordEncoder.matches(password, encodePassword);
    }

    public static String md5(String value) {
        return DigestUtils.md5Hex(value);
    }
}
