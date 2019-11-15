package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.security.AdminGroupAcl;
import com.huijiewei.agile.base.admin.security.AdminGroupAclItem;
import com.huijiewei.agile.base.admin.service.AdminGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "misc", description = "杂项接口")
public class MiscController {
    private AdminGroupService adminGroupService;

    public MiscController(AdminGroupService adminGroupService) {
        this.adminGroupService = adminGroupService;
    }

    @GetMapping(
            value = "/misc/admin-group-acl",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组 ACL 列表")
    @ApiResponse(responseCode = "200", description = "管理组 ACL 列表")
    public List<AdminGroupAclItem> actionAdminGroupAcl() {
        return AdminGroupAcl.getAll();
    }

    @GetMapping(
            value = "/misc/admin-group-map",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组 MAP")
    @ApiResponse(responseCode = "200", description = "管理组 MAP")
    public Map<Integer, String> actionAdminGroupOptions() {
        return this.adminGroupService.getMap();
    }

}
