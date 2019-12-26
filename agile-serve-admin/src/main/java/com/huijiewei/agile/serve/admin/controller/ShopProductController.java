package com.huijiewei.agile.serve.admin.controller;

import com.huijiewei.agile.core.response.PageResponse;
import com.huijiewei.agile.core.response.SearchPageResponse;
import com.huijiewei.agile.core.shop.request.ShopProductSearchRequest;
import com.huijiewei.agile.core.shop.response.ShopProductBaseResponse;
import com.huijiewei.agile.core.shop.service.ShopProductService;
import com.huijiewei.agile.core.user.request.UserSearchRequest;
import com.huijiewei.agile.core.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "shop-product", description = "商品接口")
public class ShopProductController {
    private final ShopProductService shopProductService;

    @Autowired
    public ShopProductController(ShopProductService shopProductService) {
        this.shopProductService = shopProductService;
    }

    @GetMapping(
            value = "/shop-products",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @Operation(description = "用户列表", operationId = "shopProductIndex", parameters = {
            @Parameter(name = "name", description = "名称", in = ParameterIn.QUERY, schema = @Schema(type = "string")),
            @Parameter(name = "page", description = "分页页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
            @Parameter(name = "size", description = "分页大小", in = ParameterIn.QUERY, schema = @Schema(type = "integer"))
    })
    @ApiResponse(responseCode = "200", description = "用户列表")
    @PreAuthorize("hasPermission('ADMIN', 'shop-product/index')")
    public SearchPageResponse<ShopProductBaseResponse> actionIndex(
            @Parameter(description = "是否返回搜索字段信息") @RequestParam(required = false) Boolean withSearchFields,
            @Parameter(hidden = true) ShopProductSearchRequest shopProductSearchRequest,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return this.shopProductService.getAll(withSearchFields, shopProductSearchRequest, pageable);
    }
}
