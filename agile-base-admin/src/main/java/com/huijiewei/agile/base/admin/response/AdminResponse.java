package com.huijiewei.agile.base.admin.response;

import com.huijiewei.agile.base.admin.entity.AdminGroup;
import lombok.Data;

@Data
public class AdminResponse {
    private String phone;
    private String email;
    private String name;
    private String avatar;

    private AdminGroup adminGroup;
}
