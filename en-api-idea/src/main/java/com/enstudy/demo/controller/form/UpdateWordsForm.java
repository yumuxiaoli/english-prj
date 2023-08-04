package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "更新单词表单")
public class UpdateWordsForm {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "主键")
    private Integer id;
    
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
    
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "修改时间")
    private Date updatedAt;
    
}
