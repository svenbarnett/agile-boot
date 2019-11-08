package com.huijiewei.agile.base.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminAccountResponse {
    @Schema(description = "当前用户")
    private AdminResponse currentUser;
}
