package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加讲师表单")
public class InsertTeacherForm {
    
    @NotBlank(message = "number不能为空")
    @Schema(description = "编号")
    private String number;
    
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
    
    
    
}
