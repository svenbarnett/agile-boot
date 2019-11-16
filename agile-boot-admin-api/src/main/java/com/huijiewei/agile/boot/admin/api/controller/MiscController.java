package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.security.AdminGroupAcl;
import com.huijiewei.agile.base.admin.security.AdminGroupAclItem;
import com.huijiewei.agile.base.admin.service.AdminGroupService;
import com.huijiewei.agile.spring.upload.UploadRequest;
import com.huijiewei.agile.spring.upload.driver.AliyunOSS;
import com.huijiewei.agile.spring.upload.driver.LocalFile;
import com.huijiewei.agile.spring.upload.driver.TencentCOS;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "misc", description = "杂项接口")
public class MiscController {
    private final AdminGroupService adminGroupService;
    private final AliyunOSS aliyunOSS;
    private final TencentCOS tencentCOS;
    private final LocalFile localFile;

    public MiscController(AdminGroupService adminGroupService, AliyunOSS aliyunOSS, TencentCOS tencentCOS, LocalFile localFile) {
        this.adminGroupService = adminGroupService;
        this.aliyunOSS = aliyunOSS;
        this.tencentCOS = tencentCOS;
        this.localFile = localFile;
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

    @GetMapping(
            value = "/misc/avatar-upload-options",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "头像上传设置获取")
    @ApiResponse(responseCode = "200", description = "头像上传设置")
    public UploadRequest actionAvatarUploadOptions() {
        return this.localFile.build(1024 * 1024, Arrays.asList("jpg", "jpeg", "gif", "png"));
    }
}
