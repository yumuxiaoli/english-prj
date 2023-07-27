package com.enstudy.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String salt;
    private Data create_time;
    private Data update_time;
    private Data delete_time;

    private List<User> user = new ArrayList<>();
}
