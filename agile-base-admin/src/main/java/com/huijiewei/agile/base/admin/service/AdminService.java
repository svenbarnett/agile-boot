package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.response.AdminResponse;

import java.util.List;

public interface AdminService {
    AdminLoginResponse login(String clientId, String userAgent, AdminLoginRequest request);

    AdminAccountResponse account(Admin admin);

    List<AdminResponse> getAdminsAll();

    List<AdminGroup> getAdminGroupsAll();

    AdminGroup getAdminGroupById(Integer id);
}
