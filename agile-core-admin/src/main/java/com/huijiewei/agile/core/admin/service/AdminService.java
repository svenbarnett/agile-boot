package com.huijiewei.agile.core.admin.service;

import com.devskiller.friendly_id.FriendlyId;
import com.github.wenhao.jpa.Sorts;
import com.huijiewei.agile.core.admin.entity.Admin;
import com.huijiewei.agile.core.admin.entity.AdminAccessToken;
import com.huijiewei.agile.core.admin.entity.AdminLog;
import com.huijiewei.agile.core.admin.manager.AdminGroupPermissionManager;
import com.huijiewei.agile.core.admin.mapper.AdminLogMapper;
import com.huijiewei.agile.core.admin.mapper.AdminMapper;
import com.huijiewei.agile.core.admin.repository.AdminAccessTokenRepository;
import com.huijiewei.agile.core.admin.repository.AdminLogRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminLogSearchRequest;
import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.admin.request.AdminRequest;
import com.huijiewei.agile.core.admin.response.AdminAccountResponse;
import com.huijiewei.agile.core.admin.response.AdminLogResponse;
import com.huijiewei.agile.core.admin.response.AdminLoginResponse;
import com.huijiewei.agile.core.admin.response.AdminResponse;
import com.huijiewei.agile.core.admin.security.AdminIdentity;
import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.ListResponse;
import com.huijiewei.agile.core.response.PageResponse;
import com.huijiewei.agile.core.response.SearchPageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    private final AdminLogRepository adminLogRepository;
    private final AdminAccessTokenRepository adminAccessTokenRepository;
    private final AdminGroupPermissionManager adminGroupPermissionManager;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        AdminLogRepository adminLogRepository,
                        AdminAccessTokenRepository adminAccessTokenRepository,
                        AdminGroupPermissionManager adminGroupPermissionManager) {
        this.adminRepository = adminRepository;
        this.adminLogRepository = adminLogRepository;
        this.adminAccessTokenRepository = adminAccessTokenRepository;
        this.adminGroupPermissionManager = adminGroupPermissionManager;
    }

    public AdminLoginResponse login(@Valid AdminLoginRequest request) {
        Admin admin = request.getAdmin();
        String accessToken = FriendlyId.createFriendlyId();

        Optional<AdminAccessToken> adminAccessTokenOptional = this.adminAccessTokenRepository
                .findByClientIdAndAdminId(request.getClientId(), admin.getId());

        AdminAccessToken adminAccessToken = adminAccessTokenOptional.orElseGet(AdminAccessToken::new);

        if (!adminAccessToken.hasId()) {
            adminAccessToken.setAdminId(admin.getId());
            adminAccessToken.setClientId(request.getClientId());
        }

        adminAccessToken.setUserAgent(request.getUserAgent());
        adminAccessToken.setRemoteAddr(request.getRemoteAddr());
        adminAccessToken.setAccessToken(accessToken);

        this.adminAccessTokenRepository.save(adminAccessToken);

        AdminLoginResponse adminLoginResponse = new AdminLoginResponse();
        adminLoginResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminMiniResponse(admin));
        adminLoginResponse.setGroupPermissions(this.adminGroupPermissionManager.getPermissionsByAdminGroupId(admin.getAdminGroup().getId()));
        adminLoginResponse.setGroupMenus(this.adminGroupPermissionManager.getMenusByAdminGroupId(admin.getAdminGroup().getId()));
        adminLoginResponse.setAccessToken(accessToken);

        return adminLoginResponse;
    }

    public void logout(AdminIdentity identity, String userAgent, String remoteAddr) {
        Admin admin = identity.getAdmin();

        this.adminAccessTokenRepository.deleteByAdminIdAndClientId(
                admin.getId(),
                identity.getClientId());

        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(admin.getId());
        adminLog.setType(AdminLog.TYPE_LOGIN);
        adminLog.setStatus(AdminLog.STATUS_SUCCESS);
        adminLog.setMethod("POST");
        adminLog.setAction("Logout");
        adminLog.setUserAgent(userAgent);
        adminLog.setRemoteAddr(remoteAddr);

        this.adminLogRepository.save(adminLog);
    }

    public AdminAccountResponse account(AdminIdentity identity) {
        Admin admin = identity.getAdmin();

        AdminAccountResponse adminAccountResponse = new AdminAccountResponse();
        adminAccountResponse.setCurrentUser(AdminMapper.INSTANCE.toAdminMiniResponse(admin));
        adminAccountResponse.setGroupPermissions(this.adminGroupPermissionManager.getPermissionsByAdminGroupId(admin.getAdminGroup().getId()));
        adminAccountResponse.setGroupMenus(this.adminGroupPermissionManager.getMenusByAdminGroupId(admin.getAdminGroup().getId()));

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
        Admin admin = AdminMapper.INSTANCE.toAdmin(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));

        this.adminRepository.saveWithValid(admin);

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
        Admin admin = AdminMapper.INSTANCE.toAdmin(request, current);

        if (StringUtils.isNotEmpty(request.getPassword())) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (isOwner) {
            admin.setAdminGroupId(current.getAdminGroupId());
        }

        this.adminRepository.saveWithValid(admin);

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

    public PageResponse<AdminLogResponse> getLog(Boolean withSearchFields, AdminLogSearchRequest searchRequest, Pageable pageable) {
        Specification<AdminLog> adminLogSpecification = searchRequest.getSpecification();

        Page<AdminLogResponse> adminLogs = AdminLogMapper.INSTANCE.toPageResponse(
                this.adminLogRepository.findAll(
                        adminLogSpecification,
                        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sorts.builder().desc("id").build())
                )
        );

        SearchPageResponse<AdminLogResponse> response = new SearchPageResponse<>();
        response.setPage(adminLogs);

        if (withSearchFields != null && withSearchFields) {
            response.setSearchFields(searchRequest.getSearchFields());
        }

        return response;
    }
}
