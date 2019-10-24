package com.huijiewei.agile.base.admin.request;

import com.huijiewei.agile.base.constraint.AccountType;
import com.huijiewei.agile.base.consts.AccountTypeEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AccountType(accountFieldName = "account", accountTypeFieldName = "accountType", message = "帐号必须是邮箱或者手机号")
public class AdminSignInRequest {
    @ApiModelProperty(name = "帐号，可以是邮箱或者手机号")
    @NotBlank(message = "帐号不能为空")
    private String account;

    @ApiModelProperty(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(hidden = true)
    private Enum<AccountTypeEnums> accountType;
}

