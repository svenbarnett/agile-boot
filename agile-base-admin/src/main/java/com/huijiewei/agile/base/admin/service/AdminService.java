package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;

public interface AdminService {
    public AdminLoginResponse login(String clientId, AdminLoginRequest request);
}
