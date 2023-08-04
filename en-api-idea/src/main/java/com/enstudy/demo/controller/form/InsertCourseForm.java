package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加课程表单")
public class InsertCourseForm {
    
    @NotBlank(message = "name不能为空")
    @Schema(description = "课程名")
    private String name;
    
    @NotBlank(message = "introduce不能为空")
    @Schema(description = "简介")
    private String introduce;
    
    @NotBlank(message = "details不能为空")
    @Schema(description = "详情")
    private String details;
    
    @NotBlank(message = "image不能为空")
    @Schema(description = "图片")
    private String image;
    
    @NotNull(message = "imageSize不能为空")
    @Min(value = 1, message = "imageSize不能小于1")
    @Schema(description = "大小")
    private Integer imageSize;
    
    @NotNull(message = "assortId不能为空")
    @Min(value = 1, message = "assortId不能小于1")
    @Schema(description = "分类")
    private Integer assortId;
    
    @NotNull(message = "teacherId不能为空")
    @Min(value = 1, message = "teacherId不能小于1")
    @Schema(description = "老师")
    private Integer teacherId;
    
    
    
}
