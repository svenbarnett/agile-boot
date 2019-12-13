package com.huijiewei.agile.core.repository;

import com.huijiewei.agile.core.entity.Captcha;
import org.springframework.data.repository.CrudRepository;

public interface CaptchaRepository extends CrudRepository<Captcha, Integer> {
    public boolean existsByCodeAndUuidAndUserAgentAndRemoteAddr(String code, String uuid, String userAgent, String remoteAddr);
}
