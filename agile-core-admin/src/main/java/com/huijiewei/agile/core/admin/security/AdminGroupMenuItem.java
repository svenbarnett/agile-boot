package com.huijiewei.agile.core.admin.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdminGroupMenuItem {
    private String label;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String icon;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String url;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Boolean open = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AdminGroupMenuItem> children;

    AdminGroupMenuItem label(String label) {
        this.setLabel(label);

        return this;
    }

    AdminGroupMenuItem icon(String icon) {
        this.setIcon(icon);

        return this;
    }

    AdminGroupMenuItem url(String url) {
        this.setUrl(url);

        return this;
    }

    AdminGroupMenuItem open() {
        this.setOpen(true);

        return this;
    }

    AdminGroupMenuItem addChild(AdminGroupMenuItem adminGroupMenuItem) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }

        this.children.add(adminGroupMenuItem);

        return this;
    }
}
