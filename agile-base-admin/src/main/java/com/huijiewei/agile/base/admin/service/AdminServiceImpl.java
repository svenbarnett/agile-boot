package com.huijiewei.agile.base.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.mapper.AdminMapper;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminGroupRepository adminGroupRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminGroupRepository adminGroupRepository, AdminAccessTokenRepository adminAccessTokenRepository) {
        this.adminRepository = adminRepository;
        this.adminGroupRepository = adminGroupRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
    }

    @Override
    public AdminLoginResponse login(String clientId, String userAgent, AdminLoginRequest request) {
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
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminResponse(admin));
        adminLoginResponse.setAccessToken(accessToken);

        return adminLoginResponse;
    }

    @Override
    public AdminAccountResponse account(Admin admin) {
        AdminAccountResponse adminAccountResponse = new AdminAccountResponse();
        adminAccountResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminResponse(admin));

        return adminAccountResponse;
    }

    public List<AdminResponse> getAdminsAll() {
        return AdminMapper.INSTANCE.toAdminResponses(this.adminRepository.findAll());
    }

    public List<AdminGroup> getAdminGroupsAll() {
        return this.adminGroupRepository.findAll();
    }

    @Override
    public AdminGroup getAdminGroupById(Integer id) {
        Optional<AdminGroup> adminGroup = this.adminGroupRepository.findById(id);

        if (adminGroup.isEmpty()) {
            throw new NotFoundException("管理组不存在");
        }

        return adminGroup.get();
    }
}
