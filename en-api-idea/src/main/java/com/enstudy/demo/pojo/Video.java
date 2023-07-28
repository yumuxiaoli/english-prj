package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Video {
    private Integer id;
    private String title;
    private String video;
    private String videoSize;
    private Integer courseId;
    private Date createdAt;
    private Date updatedAt;
}