package com.huijiewei.agile.core.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminGroupPermissionItem {
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String actionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AdminGroupPermissionItem> children;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> combines;

    public AdminGroupPermissionItem name(String name) {
        this.setName(name);

        return this;
    }

    AdminGroupPermissionItem actionId(String actionId) {
        this.setActionId(actionId);

        return this;
    }

    AdminGroupPermissionItem addChild(AdminGroupPermissionItem adminGroupAclItem) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }

        this.children.add(adminGroupAclItem);

        return this;
    }

    AdminGroupPermissionItem addCombine(String combine) {
        if (this.combines == null) {
            this.combines = new ArrayList<>();
        }

        this.combines.add(combine);

        return this;
    }
}
