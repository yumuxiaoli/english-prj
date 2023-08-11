package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "根据老师id查询课程")
public class SearchCourseByTeacherIdForm {
    @NotNull(message = "teacherId不能为空")
    @Schema(description = "教师Id")
    private String teacherId;
}