package com.huijiewei.agile.core.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLogResponse {
    @Schema(description = "日志 Id")
    private Integer id;

    @Schema(description = "日志类型")
    private Type type;

    @Schema(description = "操作状态")
    private Status status;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "操作动作")
    private String action;

    @Schema(description = "访问参数")
    private String params;

    @Schema(description = "浏览器")
    private String userAgent;

    @Schema(description = "IP 地址")
    private String remoteAddr;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "所属管理员")
    private AdminBaseResponse admin;

    @Data
    public static class Type {
        private String type;
        private String description;

        public Type(String value, String label) {
            this.type = value;
            this.description = label;
        }
    }

    @Data
    public static class Status {
        private Integer status;
        private String description;

        public Status(Integer status, String label) {
            this.status = status;
            this.description = label;
        }
    }
}
