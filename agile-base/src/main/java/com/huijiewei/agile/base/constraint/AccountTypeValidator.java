package com.huijiewei.agile.base.constraint;

import com.huijiewei.agile.base.consts.AccountTypeEnums;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountTypeValidator implements ConstraintValidator<AccountType, Object> {
    private String accountFieldName;
    private String accountTypeFieldName;
    private String message;

    @Override
    public void initialize(final AccountType constraintAnnotation) {
        this.accountFieldName = constraintAnnotation.accountFieldName();
        this.accountTypeFieldName = constraintAnnotation.accountTypeFieldName();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        String account = String.valueOf(new BeanWrapperImpl(value).getPropertyValue(accountFieldName));

        EmailValidator emailValidator = new EmailValidator();

        if (emailValidator.isValid(account, context)) {
            new BeanWrapperImpl(value).setPropertyValue(accountTypeFieldName, AccountTypeEnums.EMAIL);

            return true;
        }

        PhoneValidator phoneValidator = new PhoneValidator();

        if (phoneValidator.isValid(account, context)) {
            new BeanWrapperImpl(value).setPropertyValue(accountTypeFieldName, AccountTypeEnums.PHONE);

            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(this.message)
                .addPropertyNode(this.accountFieldName)
                .addConstraintViolation();

        return false;
    }
}
