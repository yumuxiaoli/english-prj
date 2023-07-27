package com.enstudy.demo.dto;

public enum ResultEnum {

    SUCCESS(666, "业务处理成功"),
    BUSINIESS_FAIL(777, "业务处理失败");

    private Integer code;

    private String desc;

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}


