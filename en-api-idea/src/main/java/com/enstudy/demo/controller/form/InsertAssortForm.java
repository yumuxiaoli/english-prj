package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Schema(description = "添加分类表单")
public class InsertAssortForm {
    
    @NotBlank(message = "name不能为空")
    @Schema(description = "职位名称")
    private String name;
    
    @NotBlank(message = "status不能为空")
    @Schema(description = "状态")
    private String status;
    
    
    
}
