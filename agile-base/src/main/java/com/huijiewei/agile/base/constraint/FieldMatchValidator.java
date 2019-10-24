package com.huijiewei.agile.base.constraint;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String fieldName;
    private String fieldMatchName;
    private String message;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.fieldMatchName = constraintAnnotation.fieldMatchName();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(fieldName);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatchName);

        boolean valid;

        if (fieldValue != null) {
            valid = fieldValue.equals(fieldMatchValue);
        } else {
            valid = fieldMatchValue == null;
        }

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.fieldName)
                    .addConstraintViolation();
        }

        return valid;
    }
}
