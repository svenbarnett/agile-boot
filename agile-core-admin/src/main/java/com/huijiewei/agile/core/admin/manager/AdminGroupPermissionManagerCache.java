package com.huijiewei.agile.core.admin.manager;

import com.huijiewei.agile.core.admin.entity.AdminGroupPermission;
import com.huijiewei.agile.core.admin.repository.AdminGroupPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminGroupPermissionManagerCache {
    public static final String ADMIN_GROUP_PERMISSIONS_CACHE_KEY = "admin-group-permissions";

    private final AdminGroupPermissionRepository adminGroupPermissionRepository;

    @Autowired
    public AdminGroupPermissionManagerCache(AdminGroupPermissionRepository adminGroupPermissionRepository) {
        this.adminGroupPermissionRepository = adminGroupPermissionRepository;
    }

    @Cacheable(cacheNames = ADMIN_GROUP_PERMISSIONS_CACHE_KEY, key = "#adminGroupId")
    public List<String> getPermissionsByAdminGroupId(Integer adminGroupId) {
        return this.adminGroupPermissionRepository
                .findAllByAdminGroupId(adminGroupId)
                .stream()
                .map(AdminGroupPermission::getActionId)
                .collect(Collectors.toList());
    }
}
