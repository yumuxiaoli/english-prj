package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加练习表单")
public class InsertExciseForm {
    
    @NotBlank(message = "title不能为空")
    @Schema(description = "题目")
    private String title;
    
    @NotBlank(message = "questiontype不能为空")
    @Schema(description = "题型")
    private String questiontype;
    
    @NotBlank(message = "rightanswer不能为空")
    @Schema(description = "正确答案")
    private String rightanswer;
    
    @NotBlank(message = "selecta不能为空")
    @Schema(description = "选项A")
    private String selecta;
    
    @NotBlank(message = "selectb不能为空")
    @Schema(description = "选项B")
    private String selectb;
    
    @NotBlank(message = "selectc不能为空")
    @Schema(description = "选项C")
    private String selectc;
    
    @NotBlank(message = "selectd不能为空")
    @Schema(description = "选项D")
    private String selectd;
    
    @NotBlank(message = "exlog不能为空")
    @Schema(description = "日志")
    private String exlog;
    
    
    
}
