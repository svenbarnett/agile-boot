package com.huijiewei.agile.base.admin.request;

import com.huijiewei.agile.base.constraint.FieldMatch;
import com.huijiewei.agile.base.constraint.Phone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@FieldMatch(fieldName = "password", fieldMatchName = "confirmPassword", message = "密码和确认密码不匹配")
public class AdminSignUpRequest {
    @ApiModelProperty(name = "邮件地址")
    @NotBlank(message = "邮件地址不能为空")
    @Email(message = "无效的邮件地址")
    private String email;

    @ApiModelProperty(name = "手机号码")
    @NotBlank(message = "手机号码不能为空")
    @Phone(message = "无效的手机号码")
    private String phone;

    @ApiModelProperty(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(name = "确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
