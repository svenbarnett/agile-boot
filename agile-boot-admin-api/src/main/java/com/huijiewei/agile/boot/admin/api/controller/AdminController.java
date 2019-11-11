package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.service.AdminService;
import com.huijiewei.agile.base.response.ListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "admin", description = "管理员")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(
            value = "/admins",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员列表")
    @ApiResponse(responseCode = "200", description = "管理员列表")
    @PreAuthorize("hasPermission(#ADMIN, 'admin/index')")
    public ListResponse<AdminResponse> actionList() {
        return this.adminService.getAll();
    }

    @GetMapping(
            value = "/admins/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员详情")
    @ApiResponse(responseCode = "200", description = "管理员")
    @ApiResponse(responseCode = "404", description = "管理员不存在", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, {'admin/view', 'admin/edit'})")
    public AdminResponse actionDetail(@PathVariable("id") Integer id) {
        return this.adminService.getById(id);
    }
}
