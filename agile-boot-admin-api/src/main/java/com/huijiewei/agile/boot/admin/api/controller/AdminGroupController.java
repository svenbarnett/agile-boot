package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import com.huijiewei.agile.base.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "admin-group", description = "管理组")
public class AdminGroupController {
    private final AdminService adminService;

    @Autowired
    public AdminGroupController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(description = "管理组列表", responses = {
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AdminGroup.class))))
    })
    @GetMapping(
            value = "/admin-groups",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<AdminGroup> actionList() {
        return this.adminService.getAdminGroupsAll();
    }

    @Operation(description = "管理组详情", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AdminGroup.class))),
            @ApiResponse(responseCode = "404", description = "管理组不存在", ref = "Problem")
    })
    @GetMapping(
            value = "/admin-groups/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AdminGroup actionDetail(@PathVariable("id") Integer id) {
        return this.adminService.getAdminGroupById(id);
    }
}
