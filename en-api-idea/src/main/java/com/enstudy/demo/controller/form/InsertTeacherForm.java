package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
@Data
@Schema(description = "添加讲师表单")
public class InsertTeacherForm {
    
    @NotNull(message = "number不能为空")
    @Min(value = 1, message = "number不能小于1")
    @Schema(description = "编号")
    private Integer number;
    
    @NotBlank(message = "name不能为空")
    @Schema(description = "姓名")
    private String name;
    
    @NotBlank(message = "image不能为空")
    @Schema(description = "头像")
    private String image;
    
    @NotNull(message = "imageSize不能为空")
    @Min(value = 1, message = "imageSize不能小于1")
    @Schema(description = "大小")
    private Integer imageSize;
    
    @NotBlank(message = "introduce不能为空")
    @Schema(description = "简介")
    private String introduce;
    
    @NotNull(message = "sort不能为空")
    @Min(value = 1, message = "sort不能小于1")
    @Schema(description = "顺序")
    private Integer sort;
    
    
    
}
