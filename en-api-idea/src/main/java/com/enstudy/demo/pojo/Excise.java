package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Excise {
    private Integer id;
    private String title;
    private String questiontype;
    private String rightanswer;
    private String selecta;
    private String selectb;
    private String selectc;
    private String selectd;
    private String exlog;
    private Date createdAt;
    private Date updatedAt;
}