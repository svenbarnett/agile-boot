package com.huijiewei.agile.core.shop.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopBrandResponse extends ShopBrandBaseResponse {
    @Schema(description = "介绍")
    private String description;

    @Schema(description = "绑定分类")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShopCategoryBaseResponse> shopCategories;
}
