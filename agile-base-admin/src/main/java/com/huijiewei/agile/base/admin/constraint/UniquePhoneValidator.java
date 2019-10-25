package com.huijiewei.agile.base.admin.constraint;

import com.huijiewei.agile.base.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {
    private AdminRepository adminRepository;

    @Autowired
    public UniquePhoneValidator(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && adminRepository.findByPhone(value) == null;
    }
}
