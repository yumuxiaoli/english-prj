package com.enstudy.demo.controller;

import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Words;
import com.enstudy.demo.service.Impl.WordsServiceImpl;
import com.enstudy.demo.service.WordsService;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/words")
@Tag(name = "WordsController", description = "单词Web接口")
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @PostMapping("/listWordsByPage")
    @Operation(summary = "查询单词分页数据")
    public R listWordsByPage(@Valid @RequestBody SearchWordsByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = wordsService.listWordsByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加单词")
    public R insert(@Valid @RequestBody InsertWordsForm form) {
        Words words = JSONUtil.parse(form).toBean(Words.class);
        if(!wordsService.ifWordsExists(form.getWords(), null)) {
            int rows = wordsService.insert(words);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:单词已存在");
        }
    }

    @PostMapping("/deleteWordsByIds")
    @Operation(summary = "删除单词记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = wordsService.deleteWordsByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询单词")
    public R searchById(@Valid SearchByIdForm form) {
        Words words = wordsService.selectById(form.getId());
        return R.ok().put("data", words);
    }

    @PostMapping("/update")
    @Operation(summary = "更新单词")
    public R update(@Valid @RequestBody UpdateWordsForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!wordsService.ifWordsExists(form.getWords(), form.getId())) {
            int rows = wordsService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:单词已存在");
        }
    }
    
    @GetMapping("/listValidWords")
    @Operation(summary = "查询有效单词")
    public R searchValidWords() {
        List<Words> dataList =  wordsService.listValidWords();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidWords")
    @Operation(summary = "查询当前页有效单词")
    public R searchPageValidWords(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = wordsService.listPageValidWords(param);
        return R.ok().put("page", pageUtils);
    }
}