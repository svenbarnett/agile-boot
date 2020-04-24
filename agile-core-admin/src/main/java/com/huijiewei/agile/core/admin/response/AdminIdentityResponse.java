package com.huijiewei.agile.core.admin.response;

import com.huijiewei.agile.core.admin.security.AdminGroupMenuItem;
import com.huijiewei.agile.core.response.IdentityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminIdentityResponse extends IdentityResponse<AdminResponse> {
    @Schema(description = "菜单列表")
    private List<AdminGroupMenuItem> groupMenus = new ArrayList<>();

    @Schema(description = "权限列表")
    private List<String> groupPermissions = new ArrayList<>();
}
