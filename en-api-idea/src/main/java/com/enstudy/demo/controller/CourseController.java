package com.enstudy.demo.controller;

;
import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Course;
import com.enstudy.demo.service.Impl.CourseServiceImpl;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/course")
@Tag(name = "CourseController", description = "课程Web接口")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @PostMapping("/listCourseByPage")
    @Operation(summary = "查询课程分页数据")
    public R listCourseByPage(@Valid @RequestBody SearchCourseByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = courseService.listCourseByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加课程")
    public R insert(@Valid @RequestBody InsertCourseForm form) {
        Course course = JSONUtil.parse(form).toBean(Course.class);
        if(!courseService.ifNameExists(form.getName(), null)) {
            int rows = courseService.insert(course);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:课程名已存在");
        }
    }

    @PostMapping("/deleteCourseByIds")
    @Operation(summary = "删除课程记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = courseService.deleteCourseByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询课程")
    public R searchById(@Valid SearchByIdForm form) {
        Course course = courseService.selectById(form.getId());
        return R.ok().put("data", course);
    }

    @PostMapping("/update")
    @Operation(summary = "更新课程")
    public R update(@Valid @RequestBody UpdateCourseForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!courseService.ifNameExists(form.getName(), form.getId())) {
            int rows = courseService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:课程名已存在");
        }
    }
    
    @GetMapping("/listValidCourse")
    @Operation(summary = "查询有效课程")
    public R searchValidCourse() {
        List<Course> dataList =  courseService.listValidCourse();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidCourse")
    @Operation(summary = "查询当前页有效课程")
    public R searchPageValidCourse(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = courseService.listPageValidCourse(param);
        return R.ok().put("page", pageUtils);
    }
}