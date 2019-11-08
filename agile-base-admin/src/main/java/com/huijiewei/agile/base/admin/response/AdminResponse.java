package com.huijiewei.agile.base.admin.response;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminResponse {
    @Schema(description = "管理员 Id")
    private Integer id;

    @Schema(description = "管理员电话")
    private String phone;

    @Schema(description = "管理员邮箱")
    private String email;

    @Schema(description = "管理员名称")
    private String name;

    @Schema(description = "管理员头像")
    private String avatar;

    @Schema(description = "所属管理组")
    private AdminGroup adminGroup;
}
