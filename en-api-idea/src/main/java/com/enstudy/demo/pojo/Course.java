package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Course {
    private Integer id;
    private String name;
    private String introduce;
    private String details;
    private String image;
    private Integer imageSize;
    private Integer assortId;
    private Integer teacherId;
    private Date createdAt;
    private Date updatedAt;
}