package com.huijiewei.agile.base.admin.constraint;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.constraint.PhoneValidator;
import com.huijiewei.agile.base.consts.AccountTypeEnums;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountValidator implements ConstraintValidator<Account, Object> {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String accountFieldName;
    private String passwordFieldName;
    private String accountEntityFieldName;
    private String accountTypeMessage;
    private String accountNotExistMessage;
    private String passwordIncorrectMessage;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void initialize(final Account constraintAnnotation) {
        this.accountFieldName = constraintAnnotation.accountFieldName();
        this.passwordFieldName = constraintAnnotation.passwordFieldName();
        this.accountEntityFieldName = constraintAnnotation.accountEntityFieldName();
        this.accountTypeMessage = constraintAnnotation.accountTypeMessage();
        this.accountNotExistMessage = constraintAnnotation.accountNotExistMessage();
        this.passwordIncorrectMessage = constraintAnnotation.passwordIncorrectMessage();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String account = String.valueOf(new BeanWrapperImpl(value).getPropertyValue(accountFieldName));
        String password = String.valueOf(new BeanWrapperImpl(value).getPropertyValue(passwordFieldName));

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
                    .addPropertyNode(this.accountFieldName)
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
                    .addPropertyNode(this.accountFieldName)
                    .addConstraintViolation();

            return false;
        }

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.passwordIncorrectMessage)
                    .addPropertyNode(this.passwordFieldName)
                    .addConstraintViolation();

            return false;
        }

        new BeanWrapperImpl(value).setPropertyValue(accountEntityFieldName, admin);

        return true;
    }
}
