package com.huijiewei.agile.base.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AccountTypeValidator.class)
@Documented
public @interface AccountType {
    String message() default "{agile.base.constraints.AccountType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String accountFieldName();

    String accountTypeFieldName();
}
