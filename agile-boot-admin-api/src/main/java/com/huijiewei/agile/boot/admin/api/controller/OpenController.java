package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.security.AdminGroupAcl;
import com.huijiewei.agile.base.admin.security.AdminGroupAclItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "open", description = "开放接口")
public class OpenController {
    @GetMapping(
            value = "/open/admin-group-acl",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组 ACL 列表")
    @ApiResponse(responseCode = "200", description = "管理组 ACL 列表")
    public List<AdminGroupAclItem> actionAdminGroupAcl() {
        return AdminGroupAcl.getAll();
    }
}
