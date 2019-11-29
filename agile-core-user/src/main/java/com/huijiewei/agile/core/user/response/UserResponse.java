package com.huijiewei.agile.core.user.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    @Schema(description = "用户 Id")
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

    @Schema(description = "创建 IP")
    private String createdIp;

    @Schema(description = "创建来源")
    private CreatedFrom createdFrom;


    @Data
    public static class CreatedFrom {
        private String name;
        private String description;

        public CreatedFrom(String value, String label) {
            this.name = value;
            this.description = label;
        }
    }
}
