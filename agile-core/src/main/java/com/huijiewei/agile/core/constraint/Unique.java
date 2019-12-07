package com.huijiewei.agile.core.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UniqueValidator.class})
public @interface Unique {
    String primaryKey() default "id";

    String[] fields() default {};

    String message() default "Such %s already exists!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Unique[] value();
    }
}
