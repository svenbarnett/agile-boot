package com.huijiewei.agile.base.shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ShopCategoryResponse {
    @Schema(description = "Id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "介绍")
    private String description;

    @Schema(description = "子分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShopCategoryResponse> children;

    @Schema(description = "上级分类列表")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShopCategoryResponse> parents;
}
