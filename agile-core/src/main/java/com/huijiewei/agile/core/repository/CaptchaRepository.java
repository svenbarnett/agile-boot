package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.Captcha;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CaptchaRepository extends CrudRepository<Captcha, Integer> {
    Optional<Captcha> findByCodeAndUuidAndUserAgentAndRemoteAddr(String code, String uuid, String userAgent, String remoteAddr);
}
