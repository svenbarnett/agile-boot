package com.huijiewei.agile.base.admin.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminLoginResponse extends AdminAuthenticationResponse {
    private String accessToken;
}
