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
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = AdminGroup.class))))
    })
    @GetMapping(
            value = "/admin-groups",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public List<AdminGroup> actionIndex() {
        return this.adminService.getAllAdminGroup();
    }
}
