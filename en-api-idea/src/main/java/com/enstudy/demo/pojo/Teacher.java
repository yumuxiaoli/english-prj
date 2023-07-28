package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Teacher {
    private Integer id;
    private Integer number;
    private String name;
    private String image;
    private Integer imageSize;
    private String introduce;
    private Integer sort;
    private Date createdAt;
    private Date updatedAt;
}