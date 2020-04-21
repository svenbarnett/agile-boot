package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.admin.request.AdminGroupRequest;
import com.huijiewei.agile.core.admin.response.AdminGroupResponse;
import com.huijiewei.agile.core.admin.service.AdminGroupService;
import com.huijiewei.agile.core.response.ListResponse;
import com.huijiewei.agile.core.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @Operation(description = "管理组列表", operationId = "adminGroupIndex")
    @ApiResponse(responseCode = "200", description = "管理组列表")
    @PreAuthorize("hasPermission('ADMIN', 'admin-group/index')")
    public ListResponse<AdminGroupResponse> actionIndex() {
        return this.adminGroupService.all();
    }

    @GetMapping(
            value = "/admin-groups/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组详情", operationId = "adminGroupView")
    @ApiResponse(responseCode = "200", description = "管理组")
    @ApiResponse(responseCode = "404", description = "管理组不存在", ref = "Problem")
    @PreAuthorize("hasPermission('ADMIN', 'admin-group/view/:id, admin-group/edit/:id')")
    public AdminGroupResponse actionView(@PathVariable("id") Integer id) {
        return this.adminGroupService.view(id);
    }

    @PostMapping(
            value = "/admin-groups",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组新建", operationId = "adminGroupCreate")
    @ApiResponse(responseCode = "201", description = "管理组")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin-group/create')")
    public AdminGroupResponse actionCreate(@RequestBody AdminGroupRequest request) {
        return this.adminGroupService.create(request);
    }

    @PutMapping(
            value = "/admin-groups/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组编辑", operationId = "adminGroupEdit")
    @ApiResponse(responseCode = "200", description = "管理组")
    @ApiResponse(responseCode = "422", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin-group/edit/:id')")
    public AdminGroupResponse actionEdit(@PathVariable("id") Integer id, @RequestBody AdminGroupRequest request) {
        return this.adminGroupService.edit(id, request);
    }

    @DeleteMapping(
            value = "/admin-groups/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组删除", operationId = "adminGroupDelete")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "409", ref = "ConflictProblem")
    @PreAuthorize("hasPermission('ADMIN', 'admin-group/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.adminGroupService.delete(id);

        return MessageResponse.of("管理组删除成功");
    }
}
