package com.enstudy.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.enstudy.demo.controller.form.*;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.pojo.Words;
import com.enstudy.demo.service.Impl.UserServiceImpl;;

import com.enstudy.demo.service.Impl.WordsServiceImpl;
import com.enstudy.demo.util.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/english")
@Tag(name = "EnglishController", description = "前台接口")
public class EnglishController {

    @Autowired
    private UserServiceImpl userService;
    private WordsServiceImpl wordsService;

    @PostMapping("login")
    @Operation(summary = "登陆系统")
    public R login(@Valid @RequestBody LoginForm form
            , HttpSession session
            , RedirectAttributes redirectAttributes) {
        User user = userService.login(form.getUsername(), form.getPassword());
        R r=R.ok().put("result", user!=null?true:false);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("User", user);
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

    @GetMapping("/logout")
    @Operation(summary = "退出")
    public R logout(){
        StpUtil.logout();
        return R.ok();
    }

//    @GetMapping("/excise")
//    @Operation(summary = "练习")
//    public R excise(@Valid @RequestBody SearchWordsByPageForm form){
//        HashMap param = JSONUtil.parse(form).toBean(HashMap.class);
//        PageUtil pageUtils = wordsService.listWordsByPage(param);
//        return R.ok().put("page", pageUtils);
//    }

}