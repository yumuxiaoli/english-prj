package com.enstudy.demo.controller;

import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Span;
import com.enstudy.demo.service.SpanService;
import com.enstudy.demo.util.PageUtil;;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/span")
@Tag(name = "SpanController", description = "文章Web接口")
public class SpanController {

    @Autowired
    private SpanService spanService;

    @PostMapping("/listSpanByPage")
    @Operation(summary = "查询文章分页数据")
    public R listSpanByPage(@Valid @RequestBody SearchSpanByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = spanService.listSpanByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加文章")
    public R insert(@Valid @RequestBody InsertSpanForm form) {
        Span span = JSONUtil.parse(form).toBean(Span.class);
        if(!spanService.ifContentExists(form.getContent(), null)) {
            int rows = spanService.insert(span);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:内容已存在");
        }
    }

    @PostMapping("/deleteSpanByIds")
    @Operation(summary = "删除文章记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = spanService.deleteSpanByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询文章")
    public R searchById(@Valid SearchByIdForm form) {
        Span span = spanService.selectById(form.getId());
        return R.ok().put("data", span);
    }

    @PostMapping("/update")
    @Operation(summary = "更新文章")
    public R update(@Valid @RequestBody UpdateSpanForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!spanService.ifContentExists(form.getContent(), form.getId())) {
            int rows = spanService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:内容已存在");
        }
    }
    
    @GetMapping("/listValidSpan")
    @Operation(summary = "查询有效文章")
    public R searchValidSpan() {
        List<Span> dataList =  spanService.listValidSpan();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidSpan")
    @Operation(summary = "查询当前页有效文章")
    public R searchPageValidSpan(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = spanService.listPageValidSpan(param);
        return R.ok().put("page", pageUtils);
    }
}