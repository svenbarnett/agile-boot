package com.huijiewei.agile.base.admin.request;

import com.huijiewei.agile.base.constraint.AccountType;
import com.huijiewei.agile.base.consts.AccountTypeEnums;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AccountType(accountFieldName = "account", accountTypeFieldName = "accountType", message = "帐号必须是邮箱或者手机号")
public class AdminSignInRequest {
    @NotBlank(message = "帐号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Hidden
    private Enum<AccountTypeEnums> accountType;
}

