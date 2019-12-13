package com.huijiewei.agile.core.admin.constraint;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.constraint.PhoneValidator;
import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.repository.CaptchaRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountValidator implements ConstraintValidator<Account, Object> {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String accountTypeMessage;
    private String accountNotExistMessage;
    private String passwordIncorrectMessage;
    private String captchaIncorrectMessage;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminLogRepository adminLogRepository;

    @Autowired
    private CaptchaRepository captchaRepository;

    @Autowired
    private ConcurrentMapCacheManager cacheManager;

    private Cache loginRetryCache;

    @Override
    public void initialize(final Account constraintAnnotation) {
        this.accountTypeMessage = constraintAnnotation.accountTypeMessage();
        this.accountNotExistMessage = constraintAnnotation.accountNotExistMessage();
        this.passwordIncorrectMessage = constraintAnnotation.passwordIncorrectMessage();
        this.captchaIncorrectMessage = constraintAnnotation.captchaIncorrectMessage();

        this.loginRetryCache = cacheManager.getCache("AGILE_ADMIN_LOGIN_RETRY");
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AdminLoginRequest request = (AdminLoginRequest) value;

        String account = request.getAccount();
        String password = request.getPassword();

        if (StringUtils.isEmpty(account)) {
            return true;
        }

        if (StringUtils.isEmpty(password)) {
            return true;
        }

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

        String loginRetryCacheKey = request.getClientId() + "_" + request.getRemoteAddr();

        Integer retryCount = loginRetryCache.get(loginRetryCacheKey, Integer.class);

        if (retryCount == null) {
            retryCount = 0;
        }

        boolean needCheckCaptcha = retryCount >= 2;
        boolean checkCaptcha = retryCount >= 3;

        if (checkCaptcha) {
            String captcha = request.getCaptcha();

            boolean captchaIncorrect = true;

            if (!StringUtils.isEmpty(captcha)) {
                String[] captchaSplit = captcha.split("_");

                if (captchaSplit.length == 2) {
                    if (this.captchaRepository.existsByCodeAndUuidAndUserAgentAndRemoteAddr(
                            captchaSplit[0],
                            captchaSplit[1],
                            request.getUserAgent(),
                            request.getRemoteAddr()
                    )) {
                        captchaIncorrect = false;
                    }
                }
            }

            if (captchaIncorrect) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(this.captchaIncorrectMessage)
                        .addPropertyNode("captcha")
                        .addConstraintViolation();

                return false;
            }
        }

        Admin admin = null;

        if (accountType == AccountTypeEnums.PHONE) {
            admin = this.adminRepository.findByPhone(account);
        }

        if (accountType == AccountTypeEnums.EMAIL) {
            admin = this.adminRepository.findByEmail(account);
        }

        if (admin == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.accountNotExistMessage)
                    .addPropertyNode("account")
                    .addConstraintViolation();

            if (needCheckCaptcha) {
                context.buildConstraintViolationWithTemplate("")
                        .addPropertyNode("captcha")
                        .addConstraintViolation();
            }

            retryCount++;

            loginRetryCache.put(loginRetryCacheKey, retryCount);

            return false;
        }

        AdminLog adminLog = new AdminLog();
        adminLog.setAdmin(admin);
        adminLog.setType(AdminLog.TYPE_LOGIN);
        adminLog.setMethod("POST");
        adminLog.setAction("Login");
        adminLog.setUserAgent(request.getUserAgent());
        adminLog.setRemoteAddr(request.getRemoteAddr());

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(this.passwordIncorrectMessage)
                    .addPropertyNode("password")
                    .addConstraintViolation();

            if (needCheckCaptcha) {
                context.buildConstraintViolationWithTemplate("")
                        .addPropertyNode("captcha")
                        .addConstraintViolation();
            }

            retryCount++;

            loginRetryCache.put(loginRetryCacheKey, retryCount);

            adminLog.setStatus(AdminLog.STATUS_FAIL);
            this.adminLogRepository.save(adminLog);

            return false;
        }

        loginRetryCache.evict(loginRetryCacheKey);

        adminLog.setStatus(AdminLog.STATUS_SUCCESS);
        this.adminLogRepository.save(adminLog);

        request.setAdmin(admin);

        return true;
    }
}
