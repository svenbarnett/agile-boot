package com.huijiewei.agile.base.admin.service;

import com.huijiewei.agile.base.admin.request.AdminSignInRequest;
import com.huijiewei.agile.base.admin.request.AdminSignUpRequest;
import com.huijiewei.agile.base.admin.response.AdminResponse;

public interface AdminService {
    public AdminResponse signIn(AdminSignInRequest request);

    public AdminResponse signUp(AdminSignUpRequest request);
}
