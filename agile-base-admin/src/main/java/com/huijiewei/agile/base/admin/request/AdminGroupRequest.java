package com.huijiewei.agile.base.admin.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class AdminGroupRequest {
    @NotBlank
    @Schema(description = "管理组名称", required = true)
    private String name;

    @Schema(description = "管理组权限")
    private List<String> permissions;
}
