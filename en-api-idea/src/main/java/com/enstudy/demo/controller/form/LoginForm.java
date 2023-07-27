package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(defaultValue = "登陆表单类")
public class LoginForm {
    @NotBlank(message = "username不能为空")
    @Pattern(regexp = "^[a-zA-z0-9]{2,20}$",message = "username内容不正确")
    @Schema(description = "用户名")
    private String username;

    @NotBlank(message = "password不能为空")
    @Pattern(regexp = "^[a-zA-z0-9]{6,20}$",message = "password内容不正确")
    @Schema(description = "密码")
    private String password;
}
