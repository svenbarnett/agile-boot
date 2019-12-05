package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.admin.request.AdminLoginRequest;
import com.huijiewei.agile.core.admin.request.AdminRequest;
import com.huijiewei.agile.core.admin.response.AdminAccountResponse;
import com.huijiewei.agile.core.admin.response.AdminLoginResponse;
import com.huijiewei.agile.core.admin.response.AdminResponse;
import com.huijiewei.agile.core.admin.service.AdminService;
import com.huijiewei.agile.core.response.MessageResponse;
import com.huijiewei.agile.serve.admin.security.AdminUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    @Operation(description = "管理员登录", operationId = "authLogin")
    @ApiResponse(responseCode = "200", description = "登录成功")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    public AdminLoginResponse actionLogin(
            @Parameter(hidden = true) @RequestHeader(name = "X-Client-Id", defaultValue = "") String clientId,
            @Parameter(hidden = true) @RequestHeader(name = "User-Agent", defaultValue = "", required = false) String userAgent,
            @RequestBody AdminLoginRequest request,
            HttpServletRequest servletRequest) {
        return this.adminService.login(clientId, userAgent, servletRequest.getRemoteAddr(), request);
    }

    @Operation(description = "当前登录帐号", operationId = "authAccount")
    @ApiResponse(responseCode = "200", description = "获取成功")
    @GetMapping(
            value = "/auth/account",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AdminAccountResponse actionAccount() {
        return this.adminService.account(AdminUserDetails.getCurrentAdminIdentity());
    }

    @GetMapping(
            value = "/auth/profile",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员个人资料", operationId = "authProfileView")
    @ApiResponse(responseCode = "200", description = "管理员个人资料")
    public AdminResponse actionProfileView() {
        return this.adminService.profile(AdminUserDetails.getCurrentAdminIdentity());
    }

    @PutMapping(
            value = "/auth/profile",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员个人资料更新", operationId = "authProfileEdit")
    @ApiResponse(responseCode = "200", description = "管理员个人资料")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    public MessageResponse actionProfileEdit(@RequestBody AdminRequest request) {
        this.adminService.profile(request, AdminUserDetails.getCurrentAdminIdentity());

        return MessageResponse.of("个人资料更新成功");
    }

    @Operation(description = "管理员退出登录", operationId = "authLogout")
    @ApiResponse(responseCode = "200", description = "退出登录成功")
    @PostMapping(
            value = "/auth/logout",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public MessageResponse actionLogout() {
        this.adminService.logout(AdminUserDetails.getCurrentAdminIdentity());

        return MessageResponse.of("退出登录成功");
    }
}
