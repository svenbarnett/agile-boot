package com.huijiewei.agile.core.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShopBrandResponse extends ShopBrandBaseResponse {
    @Schema(description = "介绍")
    private String description;
}
