package com.huijiewei.agile.core.admin.constraint;

import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.constraint.PhoneValidator;
import com.huijiewei.agile.core.consts.AccountTypeEnums;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountValidator implements ConstraintValidator<Account, Object> {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String accountTypeMessage;
    private String accountNotExistMessage;
    private String passwordIncorrectMessage;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminLogRepository adminLogRepository;

    @Override
    public void initialize(final Account constraintAnnotation) {
        this.accountTypeMessage = constraintAnnotation.accountTypeMessage();
        this.accountNotExistMessage = constraintAnnotation.accountNotExistMessage();
        this.passwordIncorrectMessage = constraintAnnotation.passwordIncorrectMessage();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        AdminLoginRequest request = (AdminLoginRequest) value;

        String account = request.getAccount();
        String password = request.getPassword();

        if (account == null || account.length() == 0) {
            return true;
        }

        if (password == null || password.length() == 0) {
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
            context.buildConstraintViolationWithTemplate(this.passwordIncorrectMessage)
                    .addPropertyNode("password")
                    .addConstraintViolation();

            adminLog.setStatus(AdminLog.STATUS_FAIL);
            this.adminLogRepository.save(adminLog);

            return false;
        }

        adminLog.setStatus(AdminLog.STATUS_SUCCESS);
        this.adminLogRepository.save(adminLog);

        request.setAdmin(admin);

        return true;
    }
}
