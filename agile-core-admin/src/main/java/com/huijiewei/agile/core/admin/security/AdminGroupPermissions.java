package com.huijiewei.agile.core.admin.security;

import java.util.ArrayList;
import java.util.List;

public class AdminGroupPermissions {
    public static List<AdminGroupPermissionItem> getAll() {
        List<AdminGroupPermissionItem> all = new ArrayList<>();

        all.add(new AdminGroupPermissionItem().name("管理首页")
                .addChild(new AdminGroupPermissionItem().name("测试短信发送").actionId("site/sms-send"))
                .addChild(new AdminGroupPermissionItem().name("更新系统缓存").actionId("site/clean-cache"))
        );

        all.add(new AdminGroupPermissionItem().name("用户管理")
                .addChild(new AdminGroupPermissionItem().name("用户列表").actionId("user/index"))
                .addChild(new AdminGroupPermissionItem().name("用户导出").actionId("user/export"))
                .addChild(new AdminGroupPermissionItem().name("用户新建").actionId("user/create"))
                .addChild(new AdminGroupPermissionItem().name("用户查看").actionId("user/view/:id"))
                .addChild(new AdminGroupPermissionItem().name("用户编辑").actionId("user/edit/:id").addCombine("user/view/:id"))
                .addChild(new AdminGroupPermissionItem().name("用户删除").actionId("user/delete"))
                .addChild(new AdminGroupPermissionItem().name("用户导入").actionId("user/import"))
        );

        all.add(new AdminGroupPermissionItem().name("商品管理")
                .addChild(new AdminGroupPermissionItem().name("商品管理")
                        .addChild(new AdminGroupPermissionItem().name("商品列表").actionId("shop-product/index"))
                        .addChild(new AdminGroupPermissionItem().name("商品新建").actionId("shop-product/create"))
                        .addChild(new AdminGroupPermissionItem().name("商品查看").actionId("shop-product/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("商品编辑").actionId("shop-product/edit/:id").addCombine("shop-product/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("商品删除").actionId("shop-product/delete").addCombine("shop-product/view/:id"))
                )
                .addChild(new AdminGroupPermissionItem().name("商品分类管理")
                        .addChild(new AdminGroupPermissionItem().name("商品分类").actionId("shop-category/index"))
                        .addChild(new AdminGroupPermissionItem().name("分类新建").actionId("shop-category/create/:id"))
                        .addChild(new AdminGroupPermissionItem().name("分类查看").actionId("shop-category/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("分类编辑").actionId("shop-category/edit/:id").addCombine("shop-category/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("分类删除").actionId("shop-category/delete").addCombine("shop-category/view/:id"))
                )
                .addChild(new AdminGroupPermissionItem().name("商品品牌管理")
                        .addChild(new AdminGroupPermissionItem().name("商品品牌").actionId("shop-brand/index"))
                        .addChild(new AdminGroupPermissionItem().name("品牌新建").actionId("shop-brand/create"))
                        .addChild(new AdminGroupPermissionItem().name("品牌查看").actionId("shop-brand/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("品牌编辑").actionId("shop-brand/edit/:id").addCombine("shop-brand/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("品牌删除").actionId("shop-brand/delete").addCombine("shop-brand/view/:id"))
                )
        );

        all.add(new AdminGroupPermissionItem().name("系统管理")
                .addChild(new AdminGroupPermissionItem().name("管理员管理")
                        .addChild(new AdminGroupPermissionItem().name("管理员列表").actionId("admin/index"))
                        .addChild(new AdminGroupPermissionItem().name("管理员新建").actionId("admin/create"))
                        .addChild(new AdminGroupPermissionItem().name("管理员查看").actionId("admin/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("管理员编辑").actionId("admin/edit/:id").addCombine("admin/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("管理员删除").actionId("admin/delete"))
                )
                .addChild(new AdminGroupPermissionItem().name("管理组管理")
                        .addChild(new AdminGroupPermissionItem().name("管理组列表").actionId("admin-group/index"))
                        .addChild(new AdminGroupPermissionItem().name("管理组新建").actionId("admin-group/create"))
                        .addChild(new AdminGroupPermissionItem().name("管理组查看").actionId("admin-group/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("管理组编辑").actionId("admin-group/edit/:id").addCombine("admin-group/view/:id"))
                        .addChild(new AdminGroupPermissionItem().name("管理组删除").actionId("admin-group/delete"))
                )
                .addChild(new AdminGroupPermissionItem().name("操作日志")
                        .addChild(new AdminGroupPermissionItem().name("操作日志查看").actionId("admin-log/index"))
                )
        );

        return all;
    }
}
