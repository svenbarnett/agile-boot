package com.huijiewei.agile.base.admin.security;

import com.huijiewei.agile.base.admin.entity.Admin;
import lombok.Data;

@Data
public
class AdminUser {
    private Admin admin;

    private String clientId;
}
