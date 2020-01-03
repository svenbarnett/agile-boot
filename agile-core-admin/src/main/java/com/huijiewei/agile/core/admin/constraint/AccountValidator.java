package com.huijiewei.agile.core.admin.constraint;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.constraint.PhoneValidator;
import com.huijiewei.agile.core.consts.AccountTypeEnums;
import com.huijiewei.agile.core.entity.Captcha;
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
import java.util.Optional;

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
    private Admin admin;

    @Override
    public void initialize(final Account constraintAnnotation) {
        this.accountTypeMessage = constraintAnnotation.accountTypeMessage();
        this.accountNotExistMessage = constraintAnnotation.accountNotExistMessage();
        this.passwordIncorrectMessage = constraintAnnotation.passwordIncorrectMessage();
        this.captchaIncorrectMessage = constraintAnnotation.captchaIncorrectMessage();

        this.loginRetryCache = cacheManager.getCache("AGILE_ADMIN_LOGIN_RETRY");
    }

    private boolean invalidCaptcha(AdminLoginRequest request, ConstraintValidatorContext context) {
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

    private boolean validPassword(AdminLoginRequest request, ConstraintValidatorContext context) {
        String password = request.getPassword();

        String loginRetryCacheKey = "ADMIN_" + admin.getId();

        Integer retryCount = loginRetryCache.get(loginRetryCacheKey, Integer.class);

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= 3 && this.invalidCaptcha(request, context)) {
            return false;
        }

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(this.passwordIncorrectMessage)
                    .addPropertyNode("password")
                    .addConstraintViolation();

            if (retryCount >= 2) {
                context.buildConstraintViolationWithTemplate("")
                        .addPropertyNode("captcha")
                        .addConstraintViolation();
            }

            loginRetryCache.put(loginRetryCacheKey, retryCount + 1);

            return false;
        }

        loginRetryCache.evict(loginRetryCacheKey);

        return true;
    }

    private boolean validAccount(AdminLoginRequest request, ConstraintValidatorContext context) {
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

        String loginRetryCacheKey = request.getClientId() + "_" + request.getRemoteAddr();

        Integer retryCount = this.loginRetryCache.get(loginRetryCacheKey, Integer.class);

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= 3 && this.invalidCaptcha(request, context)) {
            return false;
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

            if (retryCount >= 2) {
                context.buildConstraintViolationWithTemplate("")
                        .addPropertyNode("captcha")
                        .addConstraintViolation();
            }

            loginRetryCache.put(loginRetryCacheKey, retryCount + 1);

            return false;
        }

        this.admin = admin;

        loginRetryCache.evict(loginRetryCacheKey);

        return true;
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

        if (!this.validAccount(request, context)) {
            return false;
        }

        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(this.admin.getId());
        adminLog.setType(AdminLog.TYPE_LOGIN);
        adminLog.setMethod("POST");
        adminLog.setAction("Login");
        adminLog.setUserAgent(request.getUserAgent());
        adminLog.setRemoteAddr(request.getRemoteAddr());

        if (!this.validPassword(request, context)) {
            adminLog.setStatus(AdminLog.STATUS_FAIL);
            adminLog.setException("密码错误");
            this.adminLogRepository.save(adminLog);

            return false;
        }

        adminLog.setStatus(AdminLog.STATUS_SUCCESS);
        this.adminLogRepository.save(adminLog);

        request.setAdmin(this.admin);

        return true;
    }
}
