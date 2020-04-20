package com.huijiewei.agile.serve.admin.security;

import com.huijiewei.agile.core.admin.service.AdminGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class AdminPermissionEvaluator implements PermissionEvaluator {
    private final AdminGroupService adminGroupService;

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

        List<String> adminGroupPermissions = this.adminGroupService.getPermissionsById(adminGroupId);

        String[] permissions = permission.toString().split(",");

        for (String everyPermission : permissions) {
            if (adminGroupPermissions.contains(StringUtils.trim(everyPermission))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        throw new RuntimeException("Id and Class permissions are not supported by this application");
    }
}
