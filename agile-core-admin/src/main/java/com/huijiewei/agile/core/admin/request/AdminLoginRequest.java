package com.huijiewei.agile.core.admin.request;

import com.huijiewei.agile.core.admin.service.AdminIdentityService;
import com.huijiewei.agile.core.constraint.Account;
import com.huijiewei.agile.core.request.IdentityRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Account(
        service = AdminIdentityService.class,
        accountTypeMessage = "无效的帐号类型, 帐号应该是手机号码或者电子邮箱",
        accountNotExistMessage = "帐号不存在",
        passwordIncorrectMessage = "密码错误",
        captchaIncorrectMessage = "验证码错误")

public class AdminLoginRequest extends IdentityRequest {
}

