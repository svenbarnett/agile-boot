package com.huijiewei.agile.base.admin.security;

import java.util.ArrayList;
import java.util.List;

public class AdminGroupAcl {
    public static List<AdminGroupAclItem> getAll() {
        List<AdminGroupAclItem> all = new ArrayList<>();

        all.add(new AdminGroupAclItem().name("管理首页")
                .addChild(new AdminGroupAclItem().name("测试短信发送").actionId("site/sms-send"))
                .addChild(new AdminGroupAclItem().name("更新系统缓存").actionId("site/clean-cache"))
        );

        all.add(new AdminGroupAclItem().name("系统管理")
                .addChild(new AdminGroupAclItem().name("管理员管理")
                        .addChild(new AdminGroupAclItem().name("管理员列表").actionId("admin/index"))
                        .addChild(new AdminGroupAclItem().name("管理员新建").actionId("admin/create"))
                        .addChild(new AdminGroupAclItem().name("管理员编辑").actionId("admin/edit"))
                        .addChild(new AdminGroupAclItem().name("管理员删除").actionId("admin/delete"))
                )
                .addChild(new AdminGroupAclItem().name("管理组管理")
                        .addChild(new AdminGroupAclItem().name("管理组列表").actionId("admin-group/index"))
                        .addChild(new AdminGroupAclItem().name("管理组新建").actionId("admin-group/create"))
                        .addChild(new AdminGroupAclItem().name("管理组编辑").actionId("admin-group/edit"))
                        .addChild(new AdminGroupAclItem().name("管理组删除").actionId("admin-group/delete"))
                )
        );

        all.add(new AdminGroupAclItem().name("用户管理")
                .addChild(new AdminGroupAclItem().name("用户列表").actionId("user/index"))
                .addChild(new AdminGroupAclItem().name("用户新建").actionId("user/create"))
                .addChild(new AdminGroupAclItem().name("用户导入").actionId("user/import"))
                .addChild(new AdminGroupAclItem().name("用户编辑").actionId("user/edit"))
                .addChild(new AdminGroupAclItem().name("用户删除").actionId("user/delete"))
        );

        return all;
    }
}
