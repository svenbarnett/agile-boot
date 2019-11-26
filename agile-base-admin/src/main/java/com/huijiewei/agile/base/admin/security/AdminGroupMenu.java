package com.huijiewei.agile.base.admin.security;

import java.util.ArrayList;
import java.util.List;

public class AdminGroupMenu {
    public static List<AdminGroupMenuItem> getAll() {
        List<AdminGroupMenuItem> all = new ArrayList<>();

        all.add(new AdminGroupMenuItem().label("管理首页").icon("home").open(true).url("site/index"));

        all.add(new AdminGroupMenuItem().label("用户管理").icon("user")
                .addChild(new AdminGroupMenuItem().label("用户列表").url("user/index"))
                .addChild(new AdminGroupMenuItem().label("用户新建")
                        .addChild(new AdminGroupMenuItem().label("手动创建").url("user/create"))
                        .addChild(new AdminGroupMenuItem().label("批量导入").url("user/import"))
                )
        );

        all.add(new AdminGroupMenuItem().label("商品管理").icon("setting")
                .addChild(new AdminGroupMenuItem().label("商品管理").url("shop-good/index"))
                .addChild(new AdminGroupMenuItem().label("商品分类").url("shop-category/index"))
        );

        all.add(new AdminGroupMenuItem().label("系统管理").icon("setting")
                .addChild(new AdminGroupMenuItem().label("管理员").url("admin/index"))
                .addChild(new AdminGroupMenuItem().label("管理组").url("admin-group/index"))
        );

        return all;
    }
}
