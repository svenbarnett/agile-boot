package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.entity.AdminGroupPermission;
import com.huijiewei.agile.base.admin.mapper.AdminGroupMapper;
import com.huijiewei.agile.base.admin.repository.AdminGroupPermissionRepository;
import com.huijiewei.agile.base.admin.repository.AdminGroupRepository;
import com.huijiewei.agile.base.admin.request.AdminGroupRequest;
import com.huijiewei.agile.base.admin.response.AdminGroupResponse;
import com.huijiewei.agile.base.admin.security.AdminGroupMenu;
import com.huijiewei.agile.base.admin.security.AdminGroupMenuItem;
import com.huijiewei.agile.base.exception.NotFoundException;
import com.huijiewei.agile.base.response.ListResponse;
import com.huijiewei.agile.base.until.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AdminGroupService {
    private final AdminGroupRepository adminGroupRepository;
    private final AdminGroupPermissionRepository adminGroupPermissionRepository;

    @Autowired
    public AdminGroupService(AdminGroupRepository adminGroupRepository, AdminGroupPermissionRepository adminGroupPermissionRepository) {
        this.adminGroupRepository = adminGroupRepository;
        this.adminGroupPermissionRepository = adminGroupPermissionRepository;
    }

    public ListResponse<AdminGroupResponse> getAll() {
        ListResponse<AdminGroupResponse> response = new ListResponse<>();
        response.setItems(AdminGroupMapper.INSTANCE.toAdminGroupResponses(this.adminGroupRepository.findAll()));

        return response;
    }

    public AdminGroupResponse getById(Integer id) {
        Optional<AdminGroup> adminGroupOptional = this.adminGroupRepository.findById(id);

        if (adminGroupOptional.isEmpty()) {
            throw new NotFoundException("管理组不存在");
        }

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroupOptional.get());
    }

    @Cacheable(value = "admin-group-permissions")
    public List<String> getPermissionsById(Integer id) {
        List<String> permissions = new ArrayList<>();

        List<AdminGroupPermission> adminGroupPermissions = this.adminGroupPermissionRepository.findAllByAdminGroupId(id);

        for (AdminGroupPermission adminGroupPermission : adminGroupPermissions) {
            permissions.add(adminGroupPermission.getActionId());
        }

        return permissions;
    }

    public boolean checkPermission(Integer adminGroupId, Object actionId) {
        if (actionId instanceof String) {
            return this.getPermissionsById(adminGroupId).contains(actionId);
        }

        if (actionId instanceof Collection) {
            List<String> actionIds = CollectionUtils.castList(actionId, String.class);
            List<String> groupPermissions = this.getPermissionsById(adminGroupId);

            for (String everyActionId : actionIds) {
                if (groupPermissions.contains(everyActionId)) {
                    return true;
                }
            }
        }

        return false;
    }

    private AdminGroupMenuItem getAdminGroupMenuItemInPermissions(AdminGroupMenuItem adminGroupMenuItem, List<String> permissions) {
        if (adminGroupMenuItem.getUrl() != null
                && !adminGroupMenuItem.getOpen()
                && !permissions.contains(adminGroupMenuItem.getUrl())
        ) {
            return null;
        }

        List<AdminGroupMenuItem> children = null;

        if (adminGroupMenuItem.getChildren() != null) {
            children = new ArrayList<>();

            for (AdminGroupMenuItem child : adminGroupMenuItem.getChildren()) {
                AdminGroupMenuItem item = this.getAdminGroupMenuItemInPermissions(child, permissions);

                if (item != null) {
                    children.add(item);
                }
            }

            if (children.isEmpty()) {
                return null;
            }
        }

        AdminGroupMenuItem result = new AdminGroupMenuItem();
        result.setLabel(adminGroupMenuItem.getLabel());
        result.setIcon(adminGroupMenuItem.getIcon());
        result.setOpen(adminGroupMenuItem.getOpen());
        result.setUrl(adminGroupMenuItem.getUrl());

        if (children != null) {
            result.setChildren(children);
        }

        return result;
    }

    @Cacheable(value = "admin-group-menus")
    public List<AdminGroupMenuItem> getMenusById(Integer id) {
        List<AdminGroupMenuItem> all = AdminGroupMenu.getAll();
        List<String> adminGroupPermissions = this.getPermissionsById(id);

        List<AdminGroupMenuItem> adminGroupMenuItems = new ArrayList<>();

        for (AdminGroupMenuItem adminGroupMenuItem : all) {
            AdminGroupMenuItem item = this.getAdminGroupMenuItemInPermissions(adminGroupMenuItem, adminGroupPermissions);

            if (item != null) {
                adminGroupMenuItems.add(item);
            }
        }

        return adminGroupMenuItems;
    }

    public AdminGroupResponse create(@Valid AdminGroupRequest request) {
        AdminGroup adminGroup = AdminGroupMapper.INSTANCE.toAdminGroup(request);

        this.adminGroupRepository.save(adminGroup);

        return AdminGroupMapper.INSTANCE.toAdminGroupResponse(adminGroup);
    }
}
