package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminSignInRequest;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.service.AdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AdminService adminService;

    @Autowired
    public AuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(value = "/sign-in")
    @ApiOperation(value = "用户登录", notes = "使用帐号和密码登录")
    public ResponseEntity<?> actionSignIn(@Valid @RequestBody AdminSignInRequest request) {
        AdminResponse response = this.adminService.signIn(request);

        return ResponseEntity.ok(response);
    }

}
