package com.huijiewei.agile.base.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonView;
import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.security.AdminUser;
import com.huijiewei.agile.base.admin.security.AdminUserDetails;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, AdminAccessTokenRepository adminAccessTokenRepository) {
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
    }

    public AdminLoginResponse login(String clientId, String userAgent, @Valid AdminLoginRequest request) {
        Admin admin = request.getAdmin();
        String accessToken = FriendlyId.createFriendlyId();

        AdminAccessToken adminAccessToken = this.adminAccessTokenRepository.findByClientId(clientId);

        if (adminAccessToken == null) {
            adminAccessToken = new AdminAccessToken();
            adminAccessToken.setAdminId(admin.getId());
            adminAccessToken.setClientId(clientId);
            adminAccessToken.setUserAgent(userAgent);
        }

        adminAccessToken.setAccessToken(accessToken);
        this.adminAccessTokenRepository.save(adminAccessToken);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(admin);
        adminLoginResponse.setAccessToken(accessToken);

        return adminLoginResponse;
    }

    private AdminUser getCurrentAdminUser() {
        AdminUserDetails adminUserDetails = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminUserDetails.getAdminUser();
    }

    public AdminAccountResponse account() {
        Admin admin = this.getCurrentAdminUser().getAdmin();

        AdminAccountResponse adminAccountResponse = new AdminAccountResponse();
        adminAccountResponse.setCurrentUser(admin);

        return adminAccountResponse;
    }

    public List<Admin> getAll() {
        return this.adminRepository.findAll();
    }

    public Admin getById(Integer id) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        return adminOptional.get();
    }
}
