package com.huijiewei.agile.core.shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopProductResponse {
    @Schema(description = "Id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "详情")
    private String detail;

    @Schema(description = "商品分类 Id")
    private Integer shopCategoryId;

    @Schema(description = "商品品牌 Id")
    private Integer shopBrandId;

    @Schema(description = "商品分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ShopCategoryResponse shopCategory;

    @Schema(description = "商品品牌")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ShopBrandResponse shopBrand;
}
