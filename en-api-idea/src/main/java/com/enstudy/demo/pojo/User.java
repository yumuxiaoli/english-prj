package com.enstudy.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private String salt;
    private String image;
    private Integer imageSize;
    private String email;
    private String phone;
    private Date createdAt;
    private Date updatedAt;
}