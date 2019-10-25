package com.huijiewei.agile.base.admin.request;

import com.huijiewei.agile.base.constraint.FieldMatch;
import com.huijiewei.agile.base.constraint.Phone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@FieldMatch(fieldName = "password", fieldMatchName = "confirmPassword", message = "密码和确认密码不匹配")
public class AdminSignUpRequest {
    @NotBlank(message = "邮件地址不能为空")
    @Email(message = "无效的邮件地址")
    private String email;

    @NotBlank(message = "手机号码不能为空")
    @Phone(message = "无效的手机号码")
    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
