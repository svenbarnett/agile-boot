package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.security.AdminGroupAcl;
import com.huijiewei.agile.base.admin.security.AdminGroupAclItem;
import com.huijiewei.agile.base.admin.service.AdminGroupService;
import com.huijiewei.agile.base.shop.mapper.ShopCategoryMapper;
import com.huijiewei.agile.base.shop.response.ShopCategoryResponse;
import com.huijiewei.agile.base.shop.service.ShopCategoryService;
import com.huijiewei.agile.spring.upload.UploadRequest;
import com.huijiewei.agile.spring.upload.driver.LocalFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ShopCategoryService shopCategoryService;
    private final LocalFile uploadDriver;

    @Autowired
    public MiscController(AdminGroupService adminGroupService, ShopCategoryService shopCategoryService, LocalFile uploadDriver) {
        this.adminGroupService = adminGroupService;
        this.shopCategoryService = shopCategoryService;
        this.uploadDriver = uploadDriver;
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
        return this.uploadDriver.build(1024 * 1024, Arrays.asList("jpg", "jpeg", "gif", "png"));
    }

    @GetMapping(
            value = "/misc/shop-category-tree",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品分类树")
    @ApiResponse(responseCode = "200", description = "商品分类树")
    public List<ShopCategoryResponse> actionShopCategoryTree() {
        return this.shopCategoryService.getTree();
    }
}
