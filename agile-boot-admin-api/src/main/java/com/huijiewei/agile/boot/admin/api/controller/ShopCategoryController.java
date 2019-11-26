package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.shop.response.ShopCategoryResponse;
import com.huijiewei.agile.base.shop.service.ShopCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(description = "分类详情")
    @ApiResponse(responseCode = "200", description = "商品分类")
    @ApiResponse(responseCode = "404", description = "商品分类不存在", ref = "Problem")
    @PreAuthorize("hasPermission(#ADMIN, {'shop-category/view', 'shop-category/edit'})")
    public ShopCategoryResponse actionView(@PathVariable("id") Integer id, @RequestParam(required = false) Boolean withParents) {
        return this.shopCategoryService.getById(id, withParents);
    }
}
