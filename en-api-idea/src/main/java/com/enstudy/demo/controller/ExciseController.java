package com.enstudy.demo.controller;


import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Excise;
import com.enstudy.demo.service.ExciseService;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/excise")
@Tag(name = "ExciseController", description = "练习Web接口")
public class ExciseController {

    @Autowired
    private ExciseService exciseService;

    @PostMapping("/listExciseByPage")
    @Operation(summary = "查询练习分页数据")
    public R listExciseByPage(@Valid @RequestBody SearchExciseByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = exciseService.listExciseByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加练习")
    public R insert(@Valid @RequestBody InsertExciseForm form) {
        Excise excise = JSONUtil.parse(form).toBean(Excise.class);
        if(!exciseService.ifTitleExists(form.getTitle(), null)) {
            int rows = exciseService.insert(excise);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:题目已存在");
        }
    }

    @PostMapping("/deleteExciseByIds")
    @Operation(summary = "删除练习记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = exciseService.deleteExciseByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询练习")
    public R searchById(@Valid SearchByIdForm form) {
        Excise excise = exciseService.selectById(form.getId());
        return R.ok().put("data", excise);
    }

    @PostMapping("/update")
    @Operation(summary = "更新练习")
    public R update(@Valid @RequestBody UpdateExciseForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!exciseService.ifTitleExists(form.getTitle(), form.getId())) {
            int rows = exciseService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:题目已存在");
        }
    }

    @GetMapping("/listValidExcise")
    @Operation(summary = "查询有效练习")
    public R searchValidExcise() {
        List<Excise> dataList =  exciseService.listValidExcise();
        return R.ok().put("dataList", dataList);
    }

    @PostMapping("/listPageValidExcise")
    @Operation(summary = "查询当前页有效练习")
    public R searchPageValidExcise(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = exciseService.listPageValidExcise(param);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/questionlist")
    @Operation(summary = "问题列表")
    public R questionList(){
        ArrayList<HashMap> page = exciseService.questionlist();
        return R.ok().put("page", page);
    }
}