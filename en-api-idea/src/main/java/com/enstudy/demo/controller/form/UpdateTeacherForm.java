package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Schema(description = "更新讲师表单")
public class UpdateTeacherForm {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    @Schema(description = "主键")
    private Integer id;
    
    @NotNull(message = "number不能为空")
    @Min(value = 1, message = "number不能小于1")
    @Schema(description = "编号")
    private Integer number;
    
    @NotBlank(message = "name不能为空")
    @Schema(description = "姓名")
    private String name;
    
    
    @Schema(description = "头像")
    private String image;
    
    @Min(value = 1, message = "imageSize不能小于1")
    @Schema(description = "大小")
    private Integer imageSize;
    
    @NotBlank(message = "introduce不能为空")
    @Schema(description = "简介")
    private String introduce;
    
    @NotNull(message = "sort不能为空")
    @Min(value = 1, message = "sort不能小于1")
    @Schema(description = "顺序")
    private Integer sort;
    
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Schema(description = "修改时间")
    private Date updatedAt;
    
    @NotNull(message = "oldSort不能为空")
    @Min(value = 1, message = "oldSort不能小于1")
    @Schema(description = "更新前的顺序")
    private Integer oldSort;
}
