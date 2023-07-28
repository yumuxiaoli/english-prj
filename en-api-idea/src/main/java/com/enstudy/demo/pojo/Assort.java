package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Assort {
    private Integer id;
    private String name;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}