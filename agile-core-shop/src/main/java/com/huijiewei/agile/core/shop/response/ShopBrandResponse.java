package com.huijiewei.agile.core.shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class ShopBrandResponse {
    @Schema(description = "Id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "别名")
    private String alias;

    @Schema(description = "LOGO")
    private String logo;

    @Schema(description = "网站")
    private String website;

    @Schema(description = "介绍")
    private String description;

    @Schema(description = "绑定分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShopCategoryResponse> shopCategories;
}
