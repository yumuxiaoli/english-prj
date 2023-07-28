package com.enstudy.demo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Teacher;
import com.enstudy.demo.service.Impl.TeacherServiceImpl;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/teacher")
@Tag(name = "TeacherController", description = "讲师Web接口")
public class TeacherController {

    @Autowired
    private TeacherServiceImpl teacherService;

    @PostMapping("/listTeacherByPage")
    @Operation(summary = "查询讲师分页数据")
    public R listTeacherByPage(@Valid @RequestBody SearchTeacherByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = teacherService.listTeacherByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加讲师")
    public R insert(@Valid @RequestBody InsertTeacherForm form) {
        Teacher teacher = JSONUtil.parse(form).toBean(Teacher.class);
        if(!teacherService.ifNumberExists(form.getNumber(), null)) {
            int rows = teacherService.insert(teacher);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:编号已存在");
        }
    }

    @PostMapping("/deleteTeacherByIds")
    @Operation(summary = "删除讲师记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = teacherService.deleteTeacherByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询讲师")
    public R searchById(@Valid SearchByIdForm form) {
        Teacher teacher = teacherService.selectById(form.getId());
        return R.ok().put("data", teacher);
    }

    @PostMapping("/update")
    @Operation(summary = "更新讲师")
    public R update(@Valid @RequestBody UpdateTeacherForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!teacherService.ifNumberExists(form.getNumber(), form.getId())) {
            int rows = teacherService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:编号已存在");
        }
    }
    
    @GetMapping("/listValidTeacher")
    @Operation(summary = "查询有效讲师")
    //@SaCheckPermission(value = {"ROOT"}, mode = SaMode.OR)
    public R searchValidTeacher() {
        List<Teacher> dataList =  teacherService.listValidTeacher();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidTeacher")
    @Operation(summary = "查询当前页有效讲师")
    //@SaCheckPermission(value = {"ROOT"}, mode = SaMode.OR)
    public R searchPageValidTeacher(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = teacherService.listPageValidTeacher(param);
        return R.ok().put("page", pageUtils);
    }
}