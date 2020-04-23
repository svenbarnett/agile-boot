package com.huijiewei.agile.core.request;

import com.huijiewei.agile.core.entity.Identity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class IdentityRequest {
    @NotBlank(message = "帐号不能为空")
    @Schema(description = "帐号，手机号码或者电子邮箱", required = true)
    private String account;

    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", required = true)
    private String password;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(hidden = true)
    private String clientId;

    @Schema(hidden = true)
    private String userAgent;

    @Schema(hidden = true)
    private String remoteAddr;

    @Schema(hidden = true)
    private Identity identity = null;
}
