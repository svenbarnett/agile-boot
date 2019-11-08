package com.huijiewei.agile.base.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminGroupResponse {
    @Schema(description = "管理组 Id")
    private Integer id;

    @Schema(description = "管理组名称")
    private String name;
}
