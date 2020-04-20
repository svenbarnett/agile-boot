package com.huijiewei.agile.core.admin.response;

import com.huijiewei.agile.core.admin.security.AdminGroupMenuItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminAccountResponse {
    @Schema(description = "当前用户")
    private AdminResponse currentUser;

    @Schema(description = "菜单列表")
    private List<AdminGroupMenuItem> groupMenus = new ArrayList<>();

    @Schema(description = "权限列表")
    private List<String> groupPermissions = new ArrayList<>();
}
