package com.enstudy.demo.exception;

import lombok.Data;

@Data
public class CodingException extends RuntimeException {
    private String msg;
    private int code = 500;

    public CodingException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CodingException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CodingException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CodingException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}