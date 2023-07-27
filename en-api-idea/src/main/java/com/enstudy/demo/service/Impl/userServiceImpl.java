package com.enstudy.demo.service.Impl;

import com.enstudy.demo.dao.UserDao;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.service.UserService;
import com.enstudy.demo.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


//    @Override
//    public User regiter(String username, String password, String email, String nickname){
//        String salt = UUID.randomUUID().toString().replace("-","");
//        String newPassword = MD5Util.md5(password,salt,5);
//        userMapper.registerUser(username,newPassword,email,nickname,salt);
//        return null;
//    }
    @Override
    public User login(String username,String password){
        User user = userDao.checkUser(username);
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
