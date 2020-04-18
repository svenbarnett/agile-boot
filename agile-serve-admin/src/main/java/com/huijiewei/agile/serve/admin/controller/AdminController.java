package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.admin.request.AdminRequest;
import com.huijiewei.agile.core.admin.response.AdminResponse;
import com.huijiewei.agile.core.admin.service.AdminService;
import com.huijiewei.agile.core.response.ListResponse;
import com.huijiewei.agile.core.response.MessageResponse;
import com.huijiewei.agile.serve.admin.security.AdminUserDetails;
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
    @PreAuthorize("hasPermission('ADMIN', 'admin/index')")
    public ListResponse<AdminResponse> actionIndex() {
        return this.adminService.getAll();
    }

    @GetMapping(
            value = "/admins/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员详情")
    @ApiResponse(responseCode = "200", description = "管理员")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin/view/:id, admin/edit/:id')")
    public AdminResponse actionView(@PathVariable("id") Integer id) {
        return this.adminService.getById(id);
    }

    @PostMapping(
            value = "/admins",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员新建")
    @ApiResponse(responseCode = "201", description = "管理员")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin/create')")
    public AdminResponse actionCreate(@RequestBody AdminRequest request) {
        return this.adminService.create(request);
    }

    @PutMapping(
            value = "/admins/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员编辑")
    @ApiResponse(responseCode = "200", description = "管理员")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin/edit/:id')")
    public AdminResponse actionEdit(@PathVariable("id") Integer id, @RequestBody AdminRequest request) {
        return this.adminService.edit(id, request, AdminUserDetails.getCurrentAdminIdentity());
    }

    @DeleteMapping(
            value = "/admins/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员删除")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "409", ref = "ConflictProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.adminService.delete(id, AdminUserDetails.getCurrentAdminIdentity());

        return MessageResponse.of("管理员删除成功");
    }
}
