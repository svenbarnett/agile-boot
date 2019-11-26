package com.huijiewei.agile.base.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminAccessToken;
import com.huijiewei.agile.base.admin.mapper.AdminMapper;
import com.huijiewei.agile.base.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.base.admin.repository.AdminRepository;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.request.AdminRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.security.AdminIdentity;
import com.huijiewei.agile.base.exception.BadRequestException;
import com.huijiewei.agile.base.exception.ConflictException;
import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.ListResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class AdminService {
    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.orElseGet(AdminAccessToken::new);

        if (!adminAccessToken.hasId()) {
            adminAccessToken.setAdminId(admin.getId());
            adminAccessToken.setClientId(clientId);
        }

        adminAccessToken.setUserAgent(userAgent);
        adminAccessToken.setAccessToken(accessToken);

        this.adminAccessTokenRepository.save(adminAccessToken);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminBaseResponse(admin));
        adminLoginResponse.setGroupPermissions(this.adminGroupService.getPermissionsById(admin.getAdminGroup().getId()));
        adminLoginResponse.setGroupMenus(this.adminGroupService.getMenusById(admin.getAdminGroup().getId()));
        adminLoginResponse.setAccessToken(accessToken);

        return adminLoginResponse;
    }

    public void logout(AdminIdentity identity) {
        this.adminAccessTokenRepository.deleteByAdminIdAndClientId(
                identity.getAdmin().getId(),
                identity.getClientId());

    }

    public AdminAccountResponse account(AdminIdentity identity) {
        Admin admin = identity.getAdmin();

        AdminAccountResponse adminAccountResponse = new AdminAccountResponse();
        adminAccountResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminBaseResponse(admin));
        adminAccountResponse.setGroupPermissions(this.adminGroupService.getPermissionsById(admin.getAdminGroup().getId()));
        adminAccountResponse.setGroupMenus(this.adminGroupService.getMenusById(admin.getAdminGroup().getId()));

        return adminAccountResponse;
    }

    public ListResponse<AdminResponse> getAll() {
        ListResponse<AdminResponse> response = new ListResponse<>();
        response.setItems(AdminMapper.INSTANCE.toAdminResponses(this.adminRepository.findAllWithAdminGroupByOrderByIdAsc()));

        return response;
    }

    public AdminResponse getById(Integer id) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        return AdminMapper.INSTANCE.toAdminResponse(adminOptional.get());
    }

    @Validated(AdminRequest.Create.class)
    public AdminResponse create(@Valid AdminRequest request) {
        if (this.adminRepository.existsByPhone(request.getPhone())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.adminRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        Admin admin = AdminMapper.INSTANCE.toAdmin(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        this.adminRepository.save(admin);

        return AdminMapper.INSTANCE.toAdminResponse(admin);
    }

    @Validated(AdminRequest.Edit.class)
    public void profile(@Valid AdminRequest request, AdminIdentity identity) {
        this.update(identity.getAdmin(), request, true);
    }

    public AdminResponse profile(AdminIdentity identity) {
        return AdminMapper.INSTANCE.toAdminResponse(identity.getAdmin());
    }

    private Admin update(Admin current, AdminRequest request, boolean isOwner) {
        if (this.adminRepository.existsByPhoneAndIdNot(request.getPhone(), current.getId())) {
            throw new BadRequestException("手机号码已被使用");
        }

        if (this.adminRepository.existsByEmailAndIdNot(request.getEmail(), current.getId())) {
            throw new BadRequestException("电子邮箱已被使用");
        }

        Admin admin = AdminMapper.INSTANCE.toAdmin(request);
        admin.setId(current.getId());
        admin.setPassword(current.getPassword());

        if (!StringUtils.isEmpty(request.getPassword())) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (isOwner) {
            admin.setAdminGroup(current.getAdminGroup());
        }

        this.adminRepository.save(admin);

        return admin;
    }

    @Validated(AdminRequest.Edit.class)
    public AdminResponse edit(Integer id, @Valid AdminRequest request, AdminIdentity identity) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        Admin current = adminOptional.get();

        Admin admin = this.update(current, request, identity.getAdmin().getId().equals(current.getId()));

        return AdminMapper.INSTANCE.toAdminResponse(admin);
    }

    public void delete(Integer id, AdminIdentity identity) {
        Optional<Admin> adminOptional = this.adminRepository.findById(id);

        if (adminOptional.isEmpty()) {
            throw new NotFoundException("管理员不存在");
        }

        Admin admin = adminOptional.get();

        if (admin.getId().equals(identity.getAdmin().getId())) {
            throw new ConflictException("管理员不能删除自己");
        }

        this.adminRepository.delete(admin);
    }
}
