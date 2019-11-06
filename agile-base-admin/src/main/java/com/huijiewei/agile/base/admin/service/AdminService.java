package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.entity.Admin;
import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;

import java.util.List;

public interface AdminService {
    public AdminLoginResponse login(String clientId, String userAgent, AdminLoginRequest request);

    public List<Admin> getAdminsAll();

    public List<AdminGroup> getAdminGroupsAll();

    public AdminGroup getAdminGroupById(Integer id);
}
