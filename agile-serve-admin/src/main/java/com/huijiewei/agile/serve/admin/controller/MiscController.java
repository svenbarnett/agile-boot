package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.admin.response.AdminGroupResponse;
import com.huijiewei.agile.core.admin.security.AdminGroupAcl;
import com.huijiewei.agile.core.admin.security.AdminGroupAclItem;
import com.huijiewei.agile.core.admin.service.AdminGroupService;
import com.huijiewei.agile.core.shop.response.ShopBrandBaseResponse;
import com.huijiewei.agile.core.shop.response.ShopCategoryBaseResponse;
import com.huijiewei.agile.core.shop.service.ShopBrandService;
import com.huijiewei.agile.core.shop.service.ShopCategoryService;
import com.huijiewei.agile.serve.admin.security.AdminUserDetails;
import com.huijiewei.agile.spring.upload.UploadDriver;
import com.huijiewei.agile.spring.upload.UploadRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@Tag(name = "misc", description = "杂项接口")
public class MiscController {
    private final AdminGroupService adminGroupService;
    private final ShopCategoryService shopCategoryService;
    private final ShopBrandService shopBrandService;
    private final UploadDriver uploadDriver;

    @Autowired
    public MiscController(
            AdminGroupService adminGroupService,
            ShopCategoryService shopCategoryService,
            ShopBrandService shopBrandService,
            UploadDriver uploadDriver) {
        this.adminGroupService = adminGroupService;
        this.shopCategoryService = shopCategoryService;
        this.shopBrandService = shopBrandService;
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
            value = "/misc/admin-group-list",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "管理组列表", operationId = "miscAdminGroupMap")
    @ApiResponse(responseCode = "200", description = "管理组列表")
    public List<AdminGroupResponse> actionAdminGroupList() {
        return this.adminGroupService.getList();
    }

    @GetMapping(
            value = "/misc/image-upload-option",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "图片上传设置获取", operationId = "miscImageUploadOption")
    @ApiResponse(responseCode = "200", description = "图片上传设置")
    public UploadRequest actionImageUploadOption(@RequestParam(required = false) List<String> thumbs,
                                                 @RequestParam(required = false) Boolean cropper) {
        return this.uploadDriver.build(
                "a" + AdminUserDetails.getCurrentAdminIdentity().getAdmin().getId().toString(),
                2048 * 1024,
                Arrays.asList("jpg", "jpeg", "gif", "png"),
                thumbs,
                cropper
        );
    }

    @GetMapping(
            value = "/misc/file-upload-option",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "文件上传设置获取", operationId = "miscFileUploadOption")
    @ApiResponse(responseCode = "200", description = "文件上传设置")
    public UploadRequest actionFileUploadOption() {
        return this.uploadDriver.build(
                "a" + AdminUserDetails.getCurrentAdminIdentity().getAdmin().getId().toString(),
                1024 * 1024 * 10,
                Arrays.asList("jpg", "jpeg", "gif", "png", "zip", "xlsx", "docx", "pptx")
        );
    }

    @GetMapping(
            value = "/misc/shop-category-tree",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品分类树", operationId = "miscShopCategoryTree")
    @ApiResponse(responseCode = "200", description = "商品分类树")
    public List<ShopCategoryBaseResponse> actionShopCategoryTree() {
        return this.shopCategoryService.getTree();
    }

    @GetMapping(
            value = "/misc/shop-category-path",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品分类路径", operationId = "miscShopCategoryPath")
    @ApiResponse(responseCode = "200", description = "商品分类路径")
    @ApiResponse(responseCode = "404", description = "分类不存在", ref = "NotFoundProblem")
    public List<ShopCategoryBaseResponse> actionShopCategoryPath(Integer id) {
        return this.shopCategoryService.getPath(id);
    }

    @GetMapping(
            value = "/misc/shop-brand-list",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "商品品牌列表", operationId = "miscShopBrandList")
    @ApiResponse(responseCode = "200", description = "商品品牌列表")
    public List<ShopBrandBaseResponse> actionShopBrandList() {
        return this.shopBrandService.getList();
    }
}
