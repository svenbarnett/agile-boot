package com.huijiewei.agile.base.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.mapper.AdminMapper;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.security.AdminUser;
import com.huijiewei.agile.base.admin.security.AdminUserDetails;
import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;
    private final AdminGroupService adminGroupService;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        AdminAccessTokenRepository adminAccessTokenRepository,
                        AdminGroupService adminGroupService) {
        this.adminRepository = adminRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
        this.adminGroupService = adminGroupService;
    }

    public AdminLoginResponse login(String clientId, String userAgent, @Valid AdminLoginRequest request) {
        Admin admin = request.getAdmin();
        String accessToken = FriendlyId.createFriendlyId();

        Optional<AdminAccessToken> adminAccessTokenOptional = this.adminAccessTokenRepository.findByClientId(clientId);

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.isEmpty()
                ? new AdminAccessToken()
                : adminAccessTokenOptional.get();

        if (!adminAccessToken.isAdult()) {
            adminAccessToken.setAdminId(admin.getId());
            adminAccessToken.setClientId(clientId);
        }

        adminAccessToken.setUserAgent(userAgent);
        adminAccessToken.setAccessToken(accessToken);

        this.adminAccessTokenRepository.save(adminAccessToken);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminResponse(admin));
        adminLoginResponse.setGroupPermissions(this.adminGroupService.getPermissionsById(admin.getAdminGroup().getId()));
        adminLoginResponse.setGroupMenus(this.adminGroupService.getMenusById(admin.getAdminGroup().getId()));
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
        adminAccountResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminResponse(admin));
        adminAccountResponse.setGroupPermissions(this.adminGroupService.getPermissionsById(admin.getAdminGroup().getId()));
        adminAccountResponse.setGroupMenus(this.adminGroupService.getMenusById(admin.getAdminGroup().getId()));

        return adminAccountResponse;
    }

    public ListResponse<AdminResponse> getAll() {
        return new ListResponse<AdminResponse>()
                .data(AdminMapper.INSTANCE.toAdminResponses(this.adminRepository.findAll()));
    }

    public AdminResponse getById(Integer id) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        return AdminMapper.INSTANCE.toAdminResponse(adminOptional.get());
    }
}
