package com.huijiewei.agile.base.admin.security;

import java.util.ArrayList;
import java.util.List;

public class AdminGroupMenu {
    public static List<AdminGroupMenuItem> getAll() {
        List<AdminGroupMenuItem> all = new ArrayList<>();

        all.add(new AdminGroupMenuItem().label("管理首页").icon("home").open(true).url("site/index"));

        all.add(new AdminGroupMenuItem().label("系统管理").icon("setting")
                .addChild(new AdminGroupMenuItem().label("管理员").url("admin/index"))
                .addChild(new AdminGroupMenuItem().label("管理组").url("admin-group/index"))
        );

        return all;
    }
}
