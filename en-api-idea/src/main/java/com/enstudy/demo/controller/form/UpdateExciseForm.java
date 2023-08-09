package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "更新练习表单")
public class UpdateExciseForm {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "主键")
    private Integer id;
    
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
    
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "修改时间")
    private Date updatedAt;
    
}
