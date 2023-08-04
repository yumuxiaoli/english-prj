package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Words {
    private Integer id;
    private String words;
    private String pron;
    private String explains;
    private String grades;
    private Date createdAt;
    private Date updatedAt;
}