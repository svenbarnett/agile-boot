package com.huijiewei.agile.core.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopProductBaseResponse {
    @Schema(description = "Id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "封面")
    private String cover;

    @Schema(description = "商品分类")
    private ShopCategoryBaseResponse shopCategory;

    @Schema(description = "商品品牌")
    private ShopBrandBaseResponse shopBrand;
}
