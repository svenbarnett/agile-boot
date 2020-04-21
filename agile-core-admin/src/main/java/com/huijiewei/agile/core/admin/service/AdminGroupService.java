package com.huijiewei.agile.core.admin.service;

import com.huijiewei.agile.core.admin.entity.AdminGroup;
import com.huijiewei.agile.core.admin.manager.AdminGroupPermissionManager;
import com.huijiewei.agile.core.admin.mapper.AdminGroupMapper;
import com.huijiewei.agile.core.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.core.admin.repository.AdminRepository;
import com.huijiewei.agile.core.admin.request.AdminGroupRequest;
import com.huijiewei.agile.core.admin.response.AdminGroupResponse;
import com.huijiewei.agile.core.exception.ConflictException;
import com.huijiewei.agile.core.exception.NotFoundException;
import com.huijiewei.agile.core.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminGroupService {
    private final AdminRepository adminRepository;
    private final AdminGroupRepository adminGroupRepository;
    private final AdminGroupPermissionManager adminGroupPermissionManager;

    @Autowired
    public AdminGroupService(AdminRepository adminRepository,
                             AdminGroupRepository adminGroupRepository,
                             AdminGroupPermissionManager adminGroupPermissionManager) {
        this.adminRepository = adminRepository;
        this.adminGroupRepository = adminGroupRepository;
        this.adminGroupPermissionManager = adminGroupPermissionManager;
    }

    public ListResponse<AdminGroupResponse> all() {
        ListResponse<AdminGroupResponse> response = new ListResponse<>();
        response.setItems(AdminGroupMapper.INSTANCE.toAdminGroupResponses(this.adminGroupRepository.findAll()));

        return response;
    }

    public List<AdminGroupResponse> list() {
        return AdminGroupMapper.INSTANCE.toAdminGroupResponses(this.adminGroupRepository.findAll());
    }

    public List<String> getPermissionsById(Integer id) {
        return this.adminGroupPermissionManager.getPermissionsByAdminGroupId(id);
    }

    public AdminGroupResponse view(Integer id) {
        AdminGroup adminGroup = this.getById(id);

        AdminGroupResponse adminGroupResponse = AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroup);
        adminGroupResponse.setPermissions(this.getPermissionsById(id));

        return adminGroupResponse;
    }

    private AdminGroup getById(Integer id) {
        Optional<AdminGroup> adminGroupOptional = this.adminGroupRepository.findById(id);

        if (adminGroupOptional.isEmpty()) {
            throw new NotFoundException("管理组不存在");
        }

        return adminGroupOptional.get();
    }

    public AdminGroupResponse create(@Valid AdminGroupRequest request) {
        AdminGroup adminGroup = AdminGroupMapper.INSTANCE.toAdminGroup(request);

        this.adminGroupRepository.saveWithValid(adminGroup);
        this.adminGroupPermissionManager.updateAdminGroupPermissions(adminGroup.getId(), request.getPermissions(), false);

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroup);
    }

    public AdminGroupResponse edit(Integer id, @Valid AdminGroupRequest request) {
        AdminGroup current = this.getById(id);

        AdminGroup adminGroup = AdminGroupMapper.INSTANCE.toAdminGroup(request, current);

        this.adminGroupRepository.saveWithValid(adminGroup);

        this.adminGroupPermissionManager.updateAdminGroupPermissions(current.getId(), request.getPermissions(), true);

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroup);
    }

    public void delete(Integer id) {
        AdminGroup current = this.getById(id);

        if (this.adminRepository.existsByAdminGroupId(id)) {
            throw new ConflictException("管理组内拥有管理员，无法删除");
        }

        this.adminGroupRepository.delete(current);
        this.adminGroupPermissionManager.updateAdminGroupPermissions(id, null, true);
    }
}
