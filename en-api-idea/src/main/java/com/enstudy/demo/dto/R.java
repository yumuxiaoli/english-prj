package com.enstudy.demo.dto;

import java.util.HashMap;
import java.util.Map;


public class R extends HashMap<String, Object> {

    public R() {
        put("code", ResultEnum.SUCCESS.getCode());
        put("msg", ResultEnum.SUCCESS.getDesc());
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        if(map!=null)
            r.putAll(map);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(String msg) {
        return error(ResultEnum.BUSINIESS_FAIL.getCode(), msg);
    }

    public static R error() {
        return error(ResultEnum.BUSINIESS_FAIL.getCode(), ResultEnum.BUSINIESS_FAIL.getDesc());
    }

}