package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.admin.security.AdminGroupAcl;
import com.huijiewei.agile.core.admin.security.AdminGroupAclItem;
import com.huijiewei.agile.core.admin.service.AdminGroupService;
import com.huijiewei.agile.core.shop.response.ShopCategoryResponse;
import com.huijiewei.agile.core.shop.service.ShopCategoryService;
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
    @Operation(description = "管理组 ACL 列表", operationId = "miscAdminGroupAcl")
    @ApiResponse(responseCode = "200", description = "管理组 ACL 列表")
    public List<AdminGroupAclItem> actionAdminGroupAcl() {
        return AdminGroupAcl.getAll();
    }

    @GetMapping(
            value = "/misc/admin-group-map",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组 MAP", operationId = "miscAdminGroupMap")
    @ApiResponse(responseCode = "200", description = "管理组 MAP")
    public Map<Integer, String> actionAdminGroupOptions() {
        return this.adminGroupService.getMap();
    }

    @GetMapping(
            value = "/misc/image-upload-option",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "图片上传设置获取", operationId = "miscImageUploadOption")
    @ApiResponse(responseCode = "200", description = "图片上传设置")
    public UploadRequest actionImageUploadOption() {
        return this.uploadDriver.build(1024 * 1024, Arrays.asList("jpg", "jpeg", "gif", "png"));
    }

    @GetMapping(
            value = "/misc/file-upload-option",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "文件上传设置获取", operationId = "miscFileUploadOption")
    @ApiResponse(responseCode = "200", description = "文件上传设置")
    public UploadRequest actionFileUploadOption() {
        return this.uploadDriver.build(1024 * 1024 * 10, Arrays.asList("jpg", "jpeg", "gif", "png", "zip", "xlsx", "docx", "pptx"));
    }

    @GetMapping(
            value = "/misc/shop-category-tree",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品分类树", operationId = "miscShopCategoryTree")
    @ApiResponse(responseCode = "200", description = "商品分类树")
    public List<ShopCategoryResponse> actionShopCategoryTree() {
        return this.shopCategoryService.getTree();
    }

    @GetMapping(
            value = "/misc/shop-category-route",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品分类路径", operationId = "miscShopCategoryRoute")
    @ApiResponse(responseCode = "200", description = "商品分类路径")
    @ApiResponse(responseCode = "404", description = "分类不存在")
    public List<ShopCategoryResponse> actionShopCategoryRoute(Integer id) {
        return this.shopCategoryService.getRoute(id);
    }
}
