package com.enstudy.demo.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.enstudy.demo.controller.form.LoginForm;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.M_User;
import com.enstudy.demo.service.Impl.M_UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
@Tag(name = "M_UserController",description = "用户Web接口")
public class M_UserController {

    @Autowired
    private M_UserServiceImpl userService;

    @PostMapping("login")
    @Operation(summary = "登陆系统")
    public R login(@Valid @RequestBody LoginForm form
            , HttpSession session
            , RedirectAttributes redirectAttributes) {
        M_User user = userService.login(form.getUsername(), form.getPassword());
        R r=R.ok().put("result", user!=null?true:false);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("User", user);
            System.out.println(r);
            return r;
        } else {
            //model.addAttribute("msg","密码或用户名错误");
            redirectAttributes.addFlashAttribute("msg", "密码或用户名错误");
            return r;
        }
    }

    //    /**
    //     * 登陆成功后加载用户的基本信息
    //     */
    @GetMapping("/loadUserInfo")
    @Operation(summary = "登陆成功后加载用户的基本信息")
    public R loadUserInfo() {
        int userId = StpUtil.getLoginIdAsInt();//获取当前用户的id
        HashMap summary = userService.searchUserSummary(userId);
        System.out.println(summary);
        return R.ok(summary);
    }

    @GetMapping("/logout")
    @Operation(summary = "退出")
    public R logout(){
        StpUtil.logout();
        return R.ok();
    }

}
