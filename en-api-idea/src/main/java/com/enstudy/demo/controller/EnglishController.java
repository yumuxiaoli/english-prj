package com.enstudy.demo.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.service.UserService;
import com.enstudy.demo.service.WordsService;
import com.enstudy.demo.util.MD5Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/english")
@Tag(name = "EnglishController", description = "前台接口")
public class EnglishController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @Operation(summary = "登陆系统")
    public R login(@Valid @RequestBody LoginForm form
            , RedirectAttributes redirectAttributes) {
        User user = userService.login(form.getUsername(), form.getPassword());
        R r=R.ok().put("result", user!=null?true:false);
        if (user != null) {
            StpUtil.login(user.getId());
            return r;
        } else {
            //model.addAttribute("msg","密码或用户名错误");
            redirectAttributes.addFlashAttribute("msg", "密码或用户名错误");
            return r;
        }
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public R insert(@Valid @RequestBody RegisterForm form) {
        User user = JSONUtil.parse(form).toBean(User.class);
        if(!userService.ifNicknameExists(form.getNickname(), null)) {
            int rows = userService.register(user);
            return R.ok().put("rows", rows);
        }else {
            return R.error("错误:昵称已存在");
        }
    }
    @PostMapping("/updatePassword")
    @SaCheckLogin // 检查是否登录过
    @Operation(summary = "修改密码")
    public R updatePassword(@Valid @RequestBody UpdatePasswordForm form,User user) {
        int userId = StpUtil.getLoginIdAsInt();
        String salt = UUID.randomUUID().toString().replace("-","");
        String newPassword = MD5Util.md5(form.getPassword(),salt,5);
        HashMap param = new HashMap() {{
            put("userId", userId);
            put("password", newPassword);
            put("salt",salt);
        }};
        int rows = userService.updatePassword(param);
        return R.ok().put("rows", rows);
    }

    @GetMapping("/logout")
    @Operation(summary = "退出")
    public R logout(){
        StpUtil.logout();
        return R.ok();
    }


}