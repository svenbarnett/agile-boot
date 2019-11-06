package com.huijiewei.agile.base.admin.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLoginResponse extends AdminAccountResponse {
    private String accessToken;
}
