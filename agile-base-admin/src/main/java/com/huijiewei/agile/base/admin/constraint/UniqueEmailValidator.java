package com.huijiewei.agile.base.admin.constraint;

import com.huijiewei.agile.base.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private AdminRepository adminRepository;

    @Autowired
    public UniqueEmailValidator(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && adminRepository.findByEmail(value) == null;
    }
}
