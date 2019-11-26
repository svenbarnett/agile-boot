package com.huijiewei.agile.boot.admin.api.security;

import com.huijiewei.agile.base.admin.service.AdminGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AdminPermissionEvaluator implements PermissionEvaluator {
    private AdminGroupService adminGroupService;

    @Autowired
    public AdminPermissionEvaluator(AdminGroupService adminGroupService) {
        this.adminGroupService = adminGroupService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null) {
            return false;
        }

        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();

        if (adminUserDetails == null) {
            return false;
        }

        Integer adminGroupId = adminUserDetails.getAdminIdentity().getAdmin().getAdminGroup().getId();

        return this.adminGroupService.checkPermission(adminGroupId, permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException("Id and Class permissions are not supported by this application");
    }
}
