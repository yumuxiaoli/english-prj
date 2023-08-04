package com.enstudy.demo.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.json.JSONObject;

import com.enstudy.demo.dto.ResultEnum;
import com.enstudy.demo.exception.CodingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e) {
        JSONObject json = new JSONObject();
        //处理后端验证失败产生的异常
        //例如：public R deleteDeptByIds(@Valid @RequestBody DeleteDeptByIdsForm form)
        if (e instanceof MethodArgumentNotValidException) { //当Controller为单参，使用@valid验证失败时抛出此异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            json.set("error", exception.getBindingResult().getFieldError().getDefaultMessage());
        }
        //例如：public R insert(@Valid InsertProductForm form, MultipartFile uploadFile)
        else if (e instanceof BindException) { //当Controller为多参，使用@valid验证某个参数失败时抛出此异常
            BindException exception = (BindException) e;
            json.set("error", exception.getBindingResult().getFieldError().getDefaultMessage());
        }
        else if (e instanceof CodingException) { //处理业务异常
            log.error("执行异常", e);
            CodingException exception = (CodingException) e;
            json.set("msg", exception.getMsg());
            json.set("code", ResultEnum.BUSINIESS_FAIL.getCode());
        }
        else { //处理其余的异常
            log.error("执行异常", e);
            json.set("error", "执行异常");
        }
        return json.toString();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public String unLoginHandler(Exception e) {
        JSONObject json = new JSONObject();
        json.set("error", e.getMessage());
        return json.toString();
    }

}