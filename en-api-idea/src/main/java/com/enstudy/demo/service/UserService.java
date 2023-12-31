package com.enstudy.demo.service;

import com.enstudy.demo.pojo.User;
import com.enstudy.demo.util.PageUtil;

import java.util.*;

public interface UserService {

    public HashMap searchUserSummary(Integer userId);
    public PageUtil listUserByPage(HashMap param) ;

    public int deleteUserByIds(Integer[] ids) ;

    public int insert(User user);

    public boolean ifNicknameExists(String nickname, Integer id);

    public User selectById(Integer id);

    public int update(HashMap map) ;

    public List<User> listValidUser();

    public PageUtil listPageValidUser(HashMap param);

    public User login(String username, String password);

    public int register(User user);

    public int updatePassword(HashMap param);
}
