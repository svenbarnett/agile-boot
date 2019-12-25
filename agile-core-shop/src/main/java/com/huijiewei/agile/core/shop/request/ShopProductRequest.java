package com.huijiewei.agile.core.shop.request;

import com.huijiewei.agile.core.constraint.Exist;
import com.huijiewei.agile.core.shop.entity.ShopBrand;
import com.huijiewei.agile.core.shop.entity.ShopCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShopProductRequest {
    @NotBlank
    @Schema(description = "商品名称", required = true)
    private String name;

    @NotBlank
    @Schema(description = "商品封面", required = true)
    private String cover;

    @NotBlank
    @Schema(description = "商品详情", required = true)
    private String detail;

    @NotNull
    @Schema(description = "商品分类", required = true)
    @Exist(targetEntity = ShopCategory.class, targetProperty = "id", sourceProperty = "id", message = "你选择的商品分类不存在")
    private ShopCategory shopCategory;

    @NotNull
    @Schema(description = "商品品牌", required = true)
    @Exist(targetEntity = ShopBrand.class, targetProperty = "id", sourceProperty = "id", allowValues = "0", message = "你选择的商品品牌不存在")
    private ShopBrand shopBrand;
}
