package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminLoginRequest;
import com.huijiewei.agile.base.admin.response.AdminLoginResponse;
import com.huijiewei.agile.base.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "auth", description = "管理员登录注册")
public class AuthController {
    private final AdminService adminService;

    @Autowired
    public AuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(description = "管理员登录", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = AdminLoginResponse.class))),
            @ApiResponse(responseCode = "422", content = @Content(schema = @Schema(ref = "ConstraintViolationProblem")), description = "输入验证错误")
    })
    @PostMapping(
            value = "/auth/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AdminLoginResponse actionLogin(@Valid @RequestBody AdminLoginRequest request) {
        return this.adminService.login("", request);
    }

}
