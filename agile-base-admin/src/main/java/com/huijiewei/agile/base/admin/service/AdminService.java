package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.security.AdminUser;

import java.util.List;

public interface AdminService {
    AdminLoginResponse login(String clientId, String userAgent, AdminLoginRequest request);

    AdminAccountResponse account();

    AdminUser getCurrentAdminUser();

    List<AdminResponse> getAdminsAll();

    List<AdminGroup> getAdminGroupsAll();

    AdminGroup getAdminGroupById(Integer id);
}
