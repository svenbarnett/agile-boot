package com.huijiewei.agile.base.admin.security;

import com.huijiewei.agile.base.admin.entity.Admin;
import lombok.Data;

@Data
public
class AdminIdentity {
    private Admin admin;

    private String clientId;
}
