package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminAccountResponse;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth", description = "管理员登录注册")
public class AuthController {
    private final AdminService adminService;

    @Autowired
    public AuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping(
            value = "/auth/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员登录")
    @ApiResponse(responseCode = "200", description = "登录成功")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "ConstraintViolationProblem")
    public AdminLoginResponse actionLogin(
            @Parameter(hidden = true) @RequestHeader(name = "X-Client-Id", defaultValue = "") String clientId,
            @Parameter(hidden = true) @RequestHeader(name = "User-Agent", defaultValue = "", required = false) String userAgent,
            @RequestBody AdminLoginRequest request) {
        return this.adminService.login(clientId, userAgent, request);
    }

    @Operation(description = "当前登录帐号")
    @ApiResponse(responseCode = "200", description = "获取成功")
    @GetMapping(
            value = "/auth/account",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AdminAccountResponse actionAccount() {
        return this.adminService.account();
    }
}
