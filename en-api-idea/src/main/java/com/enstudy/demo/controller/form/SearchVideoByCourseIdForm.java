package com.enstudy.demo.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "根据课程id查询视频")
public class SearchVideoByCourseIdForm {
    @NotNull(message = "courseId不能为空")
    @Schema(description = "课程Id")
    private String courseId;
}
