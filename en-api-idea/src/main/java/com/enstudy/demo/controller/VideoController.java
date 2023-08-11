package com.enstudy.demo.controller;

import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.Video;
import com.enstudy.demo.service.Impl.VideoServiceImpl;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/video")
@Tag(name = "VideoController", description = "视频Web接口")
public class VideoController {

    @Autowired
    private VideoServiceImpl videoService;

    @PostMapping("/listVideoByPage")
    @Operation(summary = "查询视频分页数据")
    public R listVideoByPage(@Valid @RequestBody SearchVideoByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = videoService.listVideoByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加视频")
    public R insert(@Valid @RequestBody InsertVideoForm form) {
        Video video = JSONUtil.parse(form).toBean(Video.class);
        if(!videoService.ifTitleExists(form.getTitle(), null)) {
            int rows = videoService.insert(video);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:题目已存在");
        }
    }

    @PostMapping("/deleteVideoByIds")
    @Operation(summary = "删除视频记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = videoService.deleteVideoByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @GetMapping("/searchById")
    @Operation(summary = "根据ID查询视频")
    public R searchById(@Valid SearchByIdForm form) {
        Video video = videoService.selectById(form.getId());
        return R.ok().put("data", video);
    }

    @PostMapping("/update")
    @Operation(summary = "更新视频")
    public R update(@Valid @RequestBody UpdateVideoForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if(!videoService.ifTitleExists(form.getTitle(), form.getId())) {
            int rows = videoService.update(param);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:题目已存在");
        }
    }
    
    @GetMapping("/listValidVideo")
    @Operation(summary = "查询有效视频")
    public R searchValidVideo() {
        List<Video> dataList =  videoService.listValidVideo();
        return R.ok().put("dataList", dataList);
    }
    
    @PostMapping("/listPageValidVideo")
    @Operation(summary = "查询当前页有效视频")
    public R searchPageValidVideo(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = videoService.listPageValidVideo(param);
        return R.ok().put("page", pageUtils);
    }
    @GetMapping("/listVideoByCourseId")
    @Operation(summary = "查询视频分页数据")
    public R listVideoByCourseId(@Valid SearchVideoByCourseIdForm form) {
        ArrayList<HashMap> list = videoService.listVideoByCourseId(form.getCourseId());
        return R.ok().put("page", list);
    }
}