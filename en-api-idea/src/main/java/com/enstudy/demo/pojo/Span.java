package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Span {
    private Integer id;
    private String title;
    private String content;
    private Integer courseId;
    private Date createdAt;
    private Date updatedAt;
}