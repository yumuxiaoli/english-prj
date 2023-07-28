package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "更新分类表单")
public class UpdateAssortForm {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "主键")
    private Integer id;
    
    @NotBlank(message = "name不能为空")
    @Schema(description = "职位名称")
    private String name;
    
    @NotBlank(message = "status不能为空")
    @Schema(description = "状态")
    private String status;
    
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "修改时间")
    private Date updatedAt;
    
}
