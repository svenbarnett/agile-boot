package com.huijiewei.agile.core.admin.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
public class AdminResponse {
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

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "所属管理组 Id")
    private Integer adminGroupId;

    @Schema(description = "所属管理组")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private AdminGroupResponse adminGroup;
}
