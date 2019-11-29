package com.huijiewei.agile.core.admin.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class AdminGroupResponse {
    @Schema(description = "管理组 Id")
    private Integer id;

    @Schema(description = "管理组名称")
    private String name;

    @Schema(description = "管理组权限")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> permissions;
}
