package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.mapper.AdminMapper;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminSignInRequest;
import com.huijiewei.agile.base.admin.request.AdminSignUpRequest;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.consts.AccountTypeEnums;
import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminResponse signIn(AdminSignInRequest request) {
        Admin admin = null;

        if (request.getAccountType() == AccountTypeEnums.PHONE) {
            admin = this.adminRepository.findByPhone(request.getAccount());
        }

        if (request.getAccountType() == AccountTypeEnums.EMAIL) {
            admin = this.adminRepository.findByEmail(request.getAccount());
        }

        if (admin == null) {
            throw new NotFoundException("用户不存在");
        }

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("密码错误");
        }

        return AdminMapper.INSTANCE.toAdminResponse(admin);
    }

    @Override
    public AdminResponse signUp(AdminSignUpRequest request) {
        return null;
    }
}
