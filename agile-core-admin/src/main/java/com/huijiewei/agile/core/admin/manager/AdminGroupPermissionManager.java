package com.huijiewei.agile.core.admin.manager;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;
import com.huijiewei.agile.core.admin.repository.AdminGroupPermissionRepository;
import com.huijiewei.agile.core.admin.security.AdminGroupMenu;
import com.huijiewei.agile.core.admin.security.AdminGroupMenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminGroupPermissionManager {
    private static final String ADMIN_GROUP_MENUS_CACHE_KEY = "admin-group-menus";
    private static final String ADMIN_GROUP_PERMISSIONS_CACHE_KEY = "admin-group-permissions";

    private final AdminGroupPermissionRepository adminGroupPermissionRepository;

    @Autowired
    public AdminGroupPermissionManager(AdminGroupPermissionRepository adminGroupPermissionRepository) {
        this.adminGroupPermissionRepository = adminGroupPermissionRepository;
    }

    @Cacheable(value = ADMIN_GROUP_MENUS_CACHE_KEY, key = "#adminGroupId")
    public List<AdminGroupMenuItem> getMenusByAdminGroupId(Integer adminGroupId) {
        List<AdminGroupMenuItem> all = AdminGroupMenu.getAll();
        List<String> adminGroupPermissions = this.getPermissionsByAdminGroupId(adminGroupId);

        List<AdminGroupMenuItem> adminGroupMenuItems = new ArrayList<>();

        for (AdminGroupMenuItem adminGroupMenuItem : all) {
            AdminGroupMenuItem item = this.getAdminGroupMenuItemInPermissions(adminGroupMenuItem, adminGroupPermissions);

            if (item != null) {
                adminGroupMenuItems.add(item);
            }
        }

        return adminGroupMenuItems;
    }

    @Cacheable(value = ADMIN_GROUP_PERMISSIONS_CACHE_KEY, key = "#adminGroupId")
    public List<String> getPermissionsByAdminGroupId(Integer adminGroupId) {
        return this.adminGroupPermissionRepository
                .findAllByAdminGroupId(adminGroupId)
                .stream()
                .map(AdminGroupPermission::getActionId)
                .collect(Collectors.toList());
    }

    @CacheEvict(value = {ADMIN_GROUP_MENUS_CACHE_KEY, ADMIN_GROUP_PERMISSIONS_CACHE_KEY}, key = "#adminGroupId")
    public void updateAdminGroupPermissions(Integer adminGroupId, List<String> permissions, Boolean deletedExistPermissions) {
        if (deletedExistPermissions) {
            this.adminGroupPermissionRepository.deleteAllByAdminGroupId(adminGroupId);
        }

        if (permissions == null) {
            return;
        }

        if (permissions.isEmpty()) {
            return;
        }

        List<AdminGroupPermission> adminGroupPermissions = new ArrayList<>();

        for (String actionId : permissions) {
            AdminGroupPermission permission = new AdminGroupPermission();
            permission.setActionId(actionId);
            permission.setAdminGroupId(adminGroupId);

            adminGroupPermissions.add(permission);
        }

        this.adminGroupPermissionRepository.batchInsert(adminGroupPermissions);
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
}
