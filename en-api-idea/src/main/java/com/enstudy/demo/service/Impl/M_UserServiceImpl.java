package com.enstudy.demo.service.Impl;

import com.enstudy.demo.dao.M_UserMapper;
import com.enstudy.demo.pojo.M_User;
import com.enstudy.demo.service.M_UserService;
import com.enstudy.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class M_UserServiceImpl implements M_UserService {

    @Autowired
    private M_UserMapper userMapper;

    public HashMap searchUserSummary(Integer userId) {
        HashMap map = userMapper.searchUserSummary(userId);
        return map;
    }
//    @Override
//    public M_User regiter(String username, String password, String email, String nickname){
//        String salt = UUID.randomUUID().toString().replace("-","");
//        String newPassword = MD5Util.md5(password,salt,5);
//        userMapper.registerUser(username,newPassword,email,nickname,salt);
//        return null;
//    }
    @Override
    public M_User login(String username, String password){
        M_User user = userMapper.checkUser(username);
        if(user!=null){
            //将密码加密处理
            String newPassword = MD5Util.md5(password,
                    user.getSalt(),
                    5);
            //比较密码是否一致
            if(user.getPassword().equals(newPassword)){
                return user;//登录成功
            }
        }
        //登录失败
        return null;
    };



}
