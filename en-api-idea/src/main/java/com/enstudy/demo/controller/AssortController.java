package com.enstudy.demo.controller;

import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Assort;
import com.enstudy.demo.service.AssortService;

import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/assort")
@Tag(name = "AssortController", description = "分类Web接口")
public class AssortController {

    @Autowired
    private AssortService assortService;

    @PostMapping("/listAssortByPage")
    @Operation(summary = "查询分类分页数据")
    public R listAssortByPage(@Valid @RequestBody SearchAssortByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = assortService.listAssortByPage(param);
        System.out.println(pageUtils);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加分类")
    public R insert(@Valid @RequestBody InsertAssortForm form) {
        Assort assort = JSONUtil.parse(form).toBean(Assort.class);
        if(!assortService.ifNameExists(form.getName(), null)) {
            int rows = assortService.insert(assort);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:职位名称已存在");
        }
    }

    @PostMapping("/deleteAssortByIds")
    @Operation(summary = "删除分类记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = assortService.deleteAssortByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询分类")
    public R searchById(@Valid SearchByIdForm form) {
        Assort assort = assortService.selectById(form.getId());
        return R.ok().put("data", assort);
    }

    @PostMapping("/update")
    @Operation(summary = "更新分类")
    public R update(@Valid @RequestBody UpdateAssortForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!assortService.ifNameExists(form.getName(), form.getId())) {
            int rows = assortService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:职位名称已存在");
        }
    }
    
    @GetMapping("/listValidAssort")
    @Operation(summary = "查询有效分类")
    public R searchValidAssort() {
        List<Assort> dataList =  assortService.listValidAssort();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidAssort")
    @Operation(summary = "查询当前页有效分类")
    public R searchPageValidAssort(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = assortService.listPageValidAssort(param);
        return R.ok().put("page", pageUtils);
    }
}