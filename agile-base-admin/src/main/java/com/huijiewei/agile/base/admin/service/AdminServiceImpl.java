package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.mapper.AdminMapper;
import com.huijiewei.agile.base.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminGroupRepository adminGroupRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminGroupRepository adminGroupRepository) {
        this.adminRepository = adminRepository;
        this.adminGroupRepository = adminGroupRepository;
    }

    @Override
    public AdminLoginResponse login(String clientId, AdminLoginRequest request) {
        Admin admin = request.getAdmin();

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminResponse(admin));

        return adminLoginResponse;
    }

    public List<Admin> getAllAdmin() {
        return this.adminRepository.findAll();
    }

    public List<AdminGroup> getAllAdminGroup() {
        return this.adminGroupRepository.findAll();
    }
}
