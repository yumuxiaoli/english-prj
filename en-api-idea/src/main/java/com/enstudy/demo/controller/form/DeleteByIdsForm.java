package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Schema(description = "删除表单")
public class DeleteByIdsForm {

    @NotEmpty(message = "ids不能为空")
    @Schema(description = "id不能为空")
    private Integer[] ids;
}

