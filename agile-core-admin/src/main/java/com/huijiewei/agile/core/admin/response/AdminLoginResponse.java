package com.huijiewei.agile.core.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLoginResponse extends AdminAccountResponse {
    @Schema(description = "管理员访问令牌")
    private String accessToken;
}
