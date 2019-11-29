package com.huijiewei.agile.core.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class AdminBaseResponse {
    @Schema(description = "管理员 Id")
    private Integer id;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "所属管理组")
    private AdminGroupResponse adminGroup;
}
