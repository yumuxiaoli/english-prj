package com.enstudy.demo.dao;



import com.enstudy.demo.pojo.M_User;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface M_UserMapper {

    M_User checkUser(String username);

    public HashMap searchUserSummary(Integer userId);
}
