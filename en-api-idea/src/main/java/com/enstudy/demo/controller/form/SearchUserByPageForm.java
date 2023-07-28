package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "查询学生分页表单")
public class SearchUserByPageForm {
	
    @Schema(description = "姓名")
    private String username;
    
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    @Schema(description = "页数")
    private Integer page;

    @NotNull(message = "length不能为空")
    @Range(min = 5, max = 20, message = "length必须为5~20之间")
    @Schema(description = "每页记录数")
    private Integer length;

    @Schema(description = "排序方式")
    private String orderType;

    @Schema(description = "排序字段")
    private String orderColumn;

}
