package com.enstudy.demo.controller;

import com.enstudy.demo.controller.form.LoginForm;
import com.enstudy.demo.dto.R;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.service.Impl.userServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController",description = "用户Web接口")
public class UserController {

    @Autowired
    private userServiceImpl userService;

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
}
