package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "根据ID查询表单")
public class SearchByNameForm {

    @NotNull(message = "name不能为空")
    @Schema(description = "课程名")
    private String name;
}