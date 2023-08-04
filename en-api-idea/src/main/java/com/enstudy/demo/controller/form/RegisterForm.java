package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加学生表单")
public class RegisterForm {

    @NotBlank(message = "username不能为空")
    @Schema(description = "姓名")
    private String username;

    @NotBlank(message = "nickname不能为空")
    @Schema(description = "昵称")
    private String nickname;

    @NotBlank(message = "password不能为空")
    @Schema(description = "密码")
    private String password;

    @NotBlank(message = "image不能为空")
    @Schema(description = "头像")
    private String image;

    @NotNull(message = "imageSize不能为空")
    @Min(value = 1, message = "imageSize不能小于1")
    @Schema(description = "大小")
    private Integer imageSize;

    @NotBlank(message = "email不能为空")
    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "phone不能为空")
    @Schema(description = "电话")
    private String phone;



}
