package com.enstudy.demo.service;


import com.enstudy.demo.pojo.M_User;

import java.util.HashMap;

public interface M_UserService { ;

    public M_User login(String username, String password);
//    public M_User regiter(String username,String password,String email,String nickname);

    public HashMap searchUserSummary(Integer userId);
}
