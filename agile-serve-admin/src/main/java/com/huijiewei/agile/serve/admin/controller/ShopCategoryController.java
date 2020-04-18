package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.response.MessageResponse;
import com.huijiewei.agile.core.shop.request.ShopCategoryRequest;
import com.huijiewei.agile.core.shop.response.ShopCategoryBaseResponse;
import com.huijiewei.agile.core.shop.service.ShopCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "shop-category", description = "商品分类接口")
public class ShopCategoryController {
    private final ShopCategoryService shopCategoryService;

    public ShopCategoryController(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }

    @GetMapping(
            value = "/shop-categories/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "分类详情", operationId = "shopCategoryView")
    @ApiResponse(responseCode = "200", description = "商品分类")
    @ApiResponse(responseCode = "404", description = "商品分类不存在", ref = "NotFoundProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-category/view, shop-category/edit, shop-category/delete')")
    public ShopCategoryBaseResponse actionView(@PathVariable("id") Integer id, @RequestParam(required = false) Boolean withParents) {
        return this.shopCategoryService.getById(id, withParents);
    }

    @PostMapping(
            value = "/shop-categories",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "分类新建", operationId = "shopCategoryCreate")
    @ApiResponse(responseCode = "201", description = "商品分类")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-category/create/:id')")
    public ShopCategoryBaseResponse actionCreate(@RequestBody ShopCategoryRequest request) {
        return this.shopCategoryService.create(request);
    }

    @PutMapping(
            value = "/shop-categories/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "分类编辑", operationId = "shopCategoryEdit")
    @ApiResponse(responseCode = "200", description = "分类")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-category/edit/:id')")
    public ShopCategoryBaseResponse actionEdit(@PathVariable("id") Integer id, @RequestBody ShopCategoryRequest request) {
        return this.shopCategoryService.edit(id, request);
    }

    @DeleteMapping(
            value = "/shop-categories/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "分类删除", operationId = "shopCategoryDelete")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "分类不存在", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "409", description = "分类不允许删除", ref = "ConflictProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-category/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.shopCategoryService.delete(id);

        return MessageResponse.of("分类删除成功");
    }
}
