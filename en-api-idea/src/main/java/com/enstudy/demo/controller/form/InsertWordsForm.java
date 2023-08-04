package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加单词表单")
public class InsertWordsForm {
    
    @NotBlank(message = "words不能为空")
    @Schema(description = "单词")
    private String words;
    
    @NotBlank(message = "pron不能为空")
    @Schema(description = "读音")
    private String pron;
    
    @NotBlank(message = "explains不能为空")
    @Schema(description = "解释")
    private String explains;
    
    @NotBlank(message = "grades不能为空")
    @Schema(description = "等级")
    private String grades;
    
    
    
}
