package com.huijiewei.agile.core.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ShopBrandBaseResponse {
    @Schema(description = "Id")
    private Integer id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "LOGO")
    private String logo;
}
