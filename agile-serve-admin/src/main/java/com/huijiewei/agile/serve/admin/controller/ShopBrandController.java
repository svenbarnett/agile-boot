package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.response.MessageResponse;
import com.huijiewei.agile.core.response.SearchListResponse;
import com.huijiewei.agile.core.shop.request.ShopBrandRequest;
import com.huijiewei.agile.core.shop.request.ShopBrandSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopBrandResponse;
import com.huijiewei.agile.core.shop.service.ShopBrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "shop-brand", description = "商品品牌接口")
public class ShopBrandController {
    private final ShopBrandService shopBrandService;

    public ShopBrandController(ShopBrandService shopBrandService) {
        this.shopBrandService = shopBrandService;
    }

    @GetMapping(
            value = "/shop-brands",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "品牌列表", operationId = "shopBrandIndex", parameters = {
            @Parameter(name = "name", description = "品牌名称", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
    })
    @ApiResponse(responseCode = "200", description = "商品品牌列表")
    @PreAuthorize("hasPermission('ADMIN', 'shop-brand/index')")
    public SearchListResponse<ShopBrandResponse> actionIndex(@Parameter(hidden = true) ShopBrandSearchRequest request) {
        return this.shopBrandService.getAll(request);
    }

    @GetMapping(
            value = "/shop-brands/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "品牌详情", operationId = "shopBrandView")
    @ApiResponse(responseCode = "200", description = "商品品牌")
    @ApiResponse(responseCode = "404", description = "商品品牌不存在", ref = "NotFoundProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-brand/view/:id, shop-brand/edit/:id')")
    public ShopBrandResponse actionView(@PathVariable("id") Integer id) {
        return this.shopBrandService.getById(id);
    }

    @PostMapping(
            value = "/shop-brands",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "品牌新建", operationId = "shopBrandCreate")
    @ApiResponse(responseCode = "201", description = "商品品牌")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-brand/create')")
    public ShopBrandResponse actionCreate(@RequestBody ShopBrandRequest request) {
        return this.shopBrandService.create(request);
    }

    @PutMapping(
            value = "/shop-brands/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "品牌编辑", operationId = "shopBrandEdit")
    @ApiResponse(responseCode = "200", description = "品牌")
    @ApiResponse(responseCode = "422", description = "输入验证错误", ref = "UnprocessableEntityProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-brand/edit/:id')")
    public ShopBrandResponse actionEdit(@PathVariable("id") Integer id, @RequestBody ShopBrandRequest request) {
        return this.shopBrandService.edit(id, request);
    }

    @DeleteMapping(
            value = "/shop-brands/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "分类删除", operationId = "shopBrandDelete")
    @ApiResponse(responseCode = "200", description = "删除成功")
    @ApiResponse(responseCode = "404", description = "品牌不存在", ref = "NotFoundProblem")
    @ApiResponse(responseCode = "409", description = "品牌不允许删除", ref = "ConflictProblem")
    @PreAuthorize("hasPermission('ADMIN', 'shop-brand/delete')")
    public MessageResponse actionDelete(@PathVariable("id") Integer id) {
        this.shopBrandService.delete(id);

        return MessageResponse.of("品牌删除成功");
    }
}
