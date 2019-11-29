package com.huijiewei.agile.core.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminGroupAclItem {
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String actionId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AdminGroupAclItem> children;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> combines;

    public AdminGroupAclItem name(String name) {
        this.setName(name);

        return this;
    }

    AdminGroupAclItem actionId(String actionId) {
        this.setActionId(actionId);

        return this;
    }

    AdminGroupAclItem addChild(AdminGroupAclItem adminGroupAclItem) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }

        this.children.add(adminGroupAclItem);

        return this;
    }

    AdminGroupAclItem addCombine(String combine) {
        if (this.combines == null) {
            this.combines = new ArrayList<>();
        }

        this.combines.add(combine);

        return this;
    }
}
