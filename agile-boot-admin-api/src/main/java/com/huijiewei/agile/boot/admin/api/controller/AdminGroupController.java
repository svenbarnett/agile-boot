package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminGroupRequest;
import com.huijiewei.agile.base.admin.response.AdminGroupResponse;
import com.huijiewei.agile.base.admin.service.AdminGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "admin-group", description = "管理组")
public class AdminGroupController {
    private final AdminGroupService adminGroupService;

    @Autowired
    public AdminGroupController(AdminGroupService adminGroupService) {
        this.adminGroupService = adminGroupService;
    }

    @Operation(description = "管理组列表", responses = {
            @ApiResponse(responseCode = "200", description = "管理组列表")
    })
    @GetMapping(
            value = "/admin-groups",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<AdminGroupResponse> actionList() {
        return this.adminGroupService.getAll();
    }

    @GetMapping(
            value = "/admin-groups/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组详情", responses = {
            @ApiResponse(responseCode = "200", description = "管理组"),
            @ApiResponse(responseCode = "404", description = "管理组不存在", ref = "Problem")
    })
    public AdminGroupResponse actionDetail(@PathVariable("id") Integer id) {
        return this.adminGroupService.getById(id);
    }

    @PostMapping(
            value = "/admin-groups",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组新建",
            responses = {
                    @ApiResponse(responseCode = "201", description = "管理组"),
                    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "ConstraintViolationProblem")
            })
    public AdminGroupResponse actionCreate(@RequestBody AdminGroupRequest request) {
        return this.adminGroupService.create(request);
    }
}
