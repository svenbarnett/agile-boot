package com.huijiewei.agile.core.constraint;

import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.entity.Captcha;
import com.huijiewei.agile.core.entity.Identity;
import com.huijiewei.agile.core.entity.IdentityLog;
import com.huijiewei.agile.core.repository.CaptchaRepository;
import com.huijiewei.agile.core.request.IdentityRequest;
import com.huijiewei.agile.core.service.IdentityService;
import com.huijiewei.agile.core.until.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class AccountValidator implements ConstraintValidator<Account, IdentityRequest> {
    private String accountTypeMessage;
    private String accountNotExistMessage;
    private String passwordIncorrectMessage;
    private String captchaIncorrectMessage;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CaptchaRepository captchaRepository;

    @Autowired
    private ConcurrentMapCacheManager cacheManager;

    private Cache loginRetryCache;

    private IdentityService service;

    private Identity identity;

    @Override
    public void initialize(final Account constraintAnnotation) {
        this.accountTypeMessage = constraintAnnotation.accountTypeMessage();
        this.accountNotExistMessage = constraintAnnotation.accountNotExistMessage();
        this.passwordIncorrectMessage = constraintAnnotation.passwordIncorrectMessage();
        this.captchaIncorrectMessage = constraintAnnotation.captchaIncorrectMessage();
        this.service = this.applicationContext.getBean(constraintAnnotation.service());

        this.loginRetryCache = cacheManager.getCache(this.service.getRetryCacheName());
    }

    private Integer getRetryTimes(String retryKey) {
        Integer retryTimes = this.loginRetryCache.get(retryKey, Integer.class);

        if (retryTimes == null) {
            retryTimes = 0;
        }

        return retryTimes;
    }

    private void setRetryTimes(String retryKey, Integer retryTimes) {
        this.loginRetryCache.put(retryKey, retryTimes);
    }

    private void deleteRetryTimes(String retryKey) {
        this.loginRetryCache.evict(retryKey);
    }

    private boolean invalidCaptcha(IdentityRequest request, ConstraintValidatorContext context) {
        String captcha = request.getCaptcha();

        boolean invalidCaptcha = true;

        if (StringUtils.isNotEmpty(captcha)) {
            String[] captchaSplit = captcha.split("_");

            if (captchaSplit.length == 2) {
                Optional<Captcha> captchaOptional = this.captchaRepository.findByCodeAndUuidAndUserAgentAndRemoteAddr(
                        captchaSplit[0],
                        captchaSplit[1],
                        request.getUserAgent(),
                        request.getRemoteAddr()
                );

                if (captchaOptional.isPresent()) {
                    invalidCaptcha = false;
                    this.captchaRepository.delete(captchaOptional.get());
                }
            }
        }

        if (invalidCaptcha) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.captchaIncorrectMessage)
                    .addPropertyNode("captcha")
                    .addConstraintViolation();

            return true;
        }

        return false;
    }

    private boolean validPassword(IdentityRequest request, ConstraintValidatorContext context) {
        String password = request.getPassword();

        String retryKey = "";
        int retryTimes = 0;

        if (this.service.getIsEnableCaptcha()) {
            retryKey = "IDENTITY" + this.identity.getId();

            retryTimes = this.getRetryTimes(retryKey);

            if (retryTimes >= 3 && this.invalidCaptcha(request, context)) {
                return false;
            }
        }


        if (!SecurityUtils.passwordMatches(password, this.identity.getPassword())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(this.passwordIncorrectMessage)
                    .addPropertyNode("password")
                    .addConstraintViolation();

            if (this.service.getIsEnableCaptcha()) {
                if (retryTimes >= 2) {
                    context.buildConstraintViolationWithTemplate("")
                            .addPropertyNode("captcha")
                            .addConstraintViolation();
                }

                this.setRetryTimes(retryKey, retryTimes + 1);
            }

            return false;
        }
        if (this.service.getIsEnableCaptcha()) {
            this.deleteRetryTimes(retryKey);
        }

        return true;
    }

    private boolean validAccount(IdentityRequest request, ConstraintValidatorContext context) {
        String account = request.getAccount();

        AccountTypeEnums accountType = null;

        EmailValidator emailValidator = new EmailValidator();

        if (emailValidator.isValid(account, context)) {
            accountType = AccountTypeEnums.EMAIL;
        }

        PhoneValidator phoneValidator = new PhoneValidator();

        if (phoneValidator.isValid(account, context)) {
            accountType = AccountTypeEnums.PHONE;
        }

        if (accountType == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.accountTypeMessage)
                    .addPropertyNode("account")
                    .addConstraintViolation();

            return false;
        }

        String retryKey = "";
        int retryTimes = 0;

        if (this.service.getIsEnableCaptcha()) {
            retryKey = SecurityUtils.md5(request.getClientId() + "_" + request.getRemoteAddr()).substring(0, 8);

            retryTimes = this.getRetryTimes(retryKey);

            if (retryTimes >= 3 && this.invalidCaptcha(request, context)) {
                return false;
            }
        }

        this.identity = this.service.getIdentityByAccount(account, accountType);

        if (this.identity == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.accountNotExistMessage)
                    .addPropertyNode("account")
                    .addConstraintViolation();

            if (this.service.getIsEnableCaptcha()) {
                if (retryTimes >= 2) {
                    context.buildConstraintViolationWithTemplate("")
                            .addPropertyNode("captcha")
                            .addConstraintViolation();
                }

                this.setRetryTimes(retryKey, retryTimes + 1);
            }

            return false;
        }

        if (this.service.getIsEnableCaptcha()) {
            this.deleteRetryTimes(retryKey);
        }

        return true;
    }

    @Override
    public boolean isValid(IdentityRequest request, ConstraintValidatorContext context) {
        String account = request.getAccount();
        String password = request.getPassword();

        if (StringUtils.isEmpty(account)) {
            return true;
        }

        if (StringUtils.isEmpty(password)) {
            return true;
        }

        if (!this.validAccount(request, context)) {
            return false;
        }

        IdentityLog log = this.identity.createLog();

        if (log != null) {
            log.setType(IdentityLog.TYPE_LOGIN);
            log.setMethod("POST");
            log.setAction("Login");
            log.setUserAgent(request.getUserAgent());
            log.setRemoteAddr(request.getRemoteAddr());
        }

        boolean valid = this.validPassword(request, context);

        if (!valid) {
            if (log != null) {
                log.setStatus(IdentityLog.STATUS_FAIL);
                log.setException("密码错误");
            }
        } else {
            if (log != null) {
                log.setStatus(IdentityLog.STATUS_SUCCESS);
            }
        }

        if (log != null) {
            this.service.saveIdentityLog(log);
        }

        request.setIdentity(this.identity);

        return valid;
    }
}

