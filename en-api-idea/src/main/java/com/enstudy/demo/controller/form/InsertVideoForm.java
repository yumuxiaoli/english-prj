package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加视频表单")
public class InsertVideoForm {
    
    @NotBlank(message = "title不能为空")
    @Schema(description = "题目")
    private String title;
    
    @NotBlank(message = "video不能为空")
    @Schema(description = "视频")
    private String video;
    
    @NotBlank(message = "videoSize不能为空")
    @Schema(description = "大小")
    private String videoSize;
    
    @NotNull(message = "courseId不能为空")
    @Min(value = 1, message = "courseId不能小于1")
    @Schema(description = "课程")
    private Integer courseId;
    
    
    
}
