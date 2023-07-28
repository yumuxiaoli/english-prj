package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "更新文章表单")
public class UpdateSpanForm {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "主键")
    private Integer id;
    
    @NotBlank(message = "title不能为空")
    @Schema(description = "题目")
    private String title;
    
    @NotBlank(message = "content不能为空")
    @Schema(description = "内容")
    private String content;
    
    @NotNull(message = "courseId不能为空")
    @Min(value = 1, message = "courseId不能小于1")
    @Schema(description = "分类")
    private Integer courseId;
    
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "修改时间")
    private Date updatedAt;
    
}
