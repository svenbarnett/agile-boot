package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminGroupRequest;
import com.huijiewei.agile.base.admin.response.AdminGroupResponse;
import com.huijiewei.agile.base.admin.service.AdminGroupService;
import com.huijiewei.agile.base.response.ListResponse;
import com.huijiewei.agile.base.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "admin-group", description = "管理组")
public class AdminGroupController {
    private final AdminGroupService adminGroupService;

    @Autowired
    public AdminGroupController(AdminGroupService adminGroupService) {
        this.adminGroupService = adminGroupService;
    }

    @GetMapping(
            value = "/admin-groups",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组列表")
    @ApiResponse(responseCode = "200", description = "管理组列表")
    @PreAuthorize("hasPermission(#ADMIN, 'admin-group/index')")
    public ListResponse<AdminGroupResponse> actionList() {
        return this.adminGroupService.getAll();
    }

    @GetMapping(
            value = "/admin-groups/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组详情")
    @ApiResponse(responseCode = "200", description = "管理组")
    @ApiResponse(responseCode = "404", description = "管理组不存在", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, {'admin-group/view', 'admin-group/edit'})")
    public AdminGroupResponse actionDetail(@PathVariable("id") Integer id) {
        AdminGroupResponse adminGroupResponse = this.adminGroupService.getById(id);
        adminGroupResponse.setPermissions(this.adminGroupService.getPermissionsById(adminGroupResponse.getId()));

        return adminGroupResponse;
    }

    @PostMapping(
            value = "/admin-groups",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组新建")
    @ApiResponse(responseCode = "201", description = "管理组")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "ConstraintViolationProblem")
    @PreAuthorize("hasPermission(#ADMIN, 'admin-group/create')")
    public AdminGroupResponse actionCreate(@RequestBody AdminGroupRequest request) {
        return this.adminGroupService.create(request);
    }

    @DeleteMapping(
            value = "/admin-groups/{id}"
    )
    @Operation(description = "管理组删除")
    @ApiResponse(responseCode = "205", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "管理组不存在", ref = "Problem")
    @ApiResponse(responseCode = "409", description = "管理组不允许删除", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, 'admin-group/delete')")
    public ResponseEntity<MessageResponse> actionDelete(@PathVariable("id") Integer id) {
        this.adminGroupService.delete(id);

        return ResponseEntity.ok(MessageResponse.of("管理组删除成功"));
    }
}
