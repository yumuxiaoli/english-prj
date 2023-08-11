package com.enstudy.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.service.Impl.UserServiceImpl;;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "学生Web接口")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/listUserByPage")
    @Operation(summary = "查询学生分页数据")
    public R listUserByPage(@Valid @RequestBody SearchUserByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = userService.listUserByPage(param);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/insert")
    @Operation(summary = "添加学生")
    public R insert(@Valid @RequestBody InsertUserForm form) {
        User user = JSONUtil.parse(form).toBean(User.class);
        if (!userService.ifNicknameExists(form.getNickname(), null)) {
            int rows = userService.insert(user);
            return R.ok().put("rows", rows);
        } else {
            return R.error("错误:昵称已存在");
        }
    }

    @PostMapping("/deleteUserByIds")
    @Operation(summary = "删除学生记录")
    public R deleteDeptByIds(@Valid @RequestBody DeleteByIdsForm form) {
        int rows = userService.deleteUserByIds(form.getIds());
        return R.ok().put("rows", rows);
    }

    @PostMapping("/update")
    @Operation(summary = "更新学生")
    public R update(@Valid @RequestBody UpdateUserForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        if (!userService.ifNicknameExists(form.getNickname(), form.getId())) {
            int rows = userService.update(param);
            return R.ok().put("rows", rows);
        } else {
            return R.error("错误:昵称已存在");
        }
    }

    @GetMapping("/listValidUser")
    @Operation(summary = "查询有效学生")
    public R searchValidUser() {
        List<User> dataList = userService.listValidUser();
        return R.ok().put("dataList", dataList);
    }

    @PostMapping("/listPageValidUser")
    @Operation(summary = "查询当前页有效学生")
    public R searchPageValidUser(@Valid @RequestBody SearchFrontByPageForm form) {
        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
        PageUtil pageUtils = userService.listPageValidUser(param);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/loadUser")
    @Operation(summary = "登陆成功后加载用户的基本信息")
    public R loadUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();//获取当前用户的id
        HashMap summary = userService.searchUserSummary(userId);
        System.out.println(summary);
        return R.ok(summary);
    }
}
