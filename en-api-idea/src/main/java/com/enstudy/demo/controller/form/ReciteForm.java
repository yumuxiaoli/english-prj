package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(defaultValue = "背诵表单类")
public class ReciteForm {
    @NotBlank(message = "words不能为空")
    @Schema(description = "单词")
    private String words;
}
