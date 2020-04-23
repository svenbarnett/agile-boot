package com.huijiewei.agile.core.constraint;

import com.huijiewei.agile.core.entity.Identity;
import com.huijiewei.agile.core.entity.IdentityLog;
import com.huijiewei.agile.core.service.IdentityService;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountValidator.class)
public @interface Account {
    public String message() default "";

    Class<? extends IdentityService> service();

    public String accountTypeMessage() default "Invalid account type, account should be the phone or email";

    public String accountNotExistMessage() default "Account is not exists";

    public String passwordIncorrectMessage() default "Password is incorrect";

    public String captchaIncorrectMessage() default "Captcha is incorrect";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
