package com.huijiewei.agile.base.admin.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.huijiewei.agile.base.admin.entity.Admin;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdminAccountResponse {
    @JsonView(Admin.Views.Detail.class)
    @Schema(description = "当前用户")
    private Admin currentUser;
}
