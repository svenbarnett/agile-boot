package com.huijiewei.agile.core.shop.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShopBrandRequest {
    @NotBlank
    @Schema(description = "品牌名称", required = true)
    private String name;

    @NotBlank
    @Schema(description = "品牌别名", required = true)
    private String alias;

    @NotNull
    @Schema(description = "品牌 LOGO")
    private String logo;

    @Schema(description = "品牌介绍")
    private String description;
}
