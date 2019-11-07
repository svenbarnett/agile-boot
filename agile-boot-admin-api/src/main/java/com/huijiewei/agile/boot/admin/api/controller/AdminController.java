package com.huijiewei.agile.boot.admin.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.huijiewei.agile.base.admin.entity.Admin;
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
    @ApiResponse(responseCode = "200", description = "管理员列表", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Admin.class))))
    @JsonView({Admin.Views.Detail.class})
    public List<Admin> actionList() {
        return this.adminService.getAll();
    }

    @GetMapping(
            value = "/admins/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理员详情")
    @ApiResponse(responseCode = "200", description = "管理员", content = @Content(schema = @Schema(implementation = Admin.class)))
    @ApiResponse(responseCode = "404", description = "管理员不存在", ref = "Problem")
    @JsonView({Admin.Views.Detail.class})
    public Admin actionDetail(@Schema(description = "管理员 Id") @PathVariable("id") Integer id) {
        return this.adminService.getById(id);
    }
}
