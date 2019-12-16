package com.huijiewei.agile.core.admin.response;

import com.huijiewei.agile.core.admin.entity.AdminLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminLogResponse {
    @Schema(description = "日志 Id")
    private Integer id;

    @Schema(description = "日志类型")
    private AdminLog.Type type;

    @Schema(description = "操作状态")
    private AdminLog.Status status;

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
}
