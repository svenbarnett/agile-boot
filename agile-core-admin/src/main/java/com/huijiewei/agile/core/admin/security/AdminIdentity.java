package com.huijiewei.agile.core.admin.security;

import com.huijiewei.agile.core.admin.entity.Admin;
import lombok.Data;

@Data
public
class AdminIdentity {
    private Admin admin;

    private String clientId;
}
