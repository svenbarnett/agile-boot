package com.huijiewei.agile.base.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.huijiewei.agile.base.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    @Schema(description = "用户 Id")
    private Integer id;

    @Schema(description = "用户电话")
    private String phone;

    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "创建 IP")
    private String createdIp;

    @Schema(description = "创建来源")
    private CreatedFrom createdFrom;

    @Data
    public static class CreatedFrom {
        private String name;
        private String description;

        public CreatedFrom(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
