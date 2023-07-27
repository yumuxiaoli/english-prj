package com.enstudy.demo.dao;



import com.enstudy.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    public Integer login(String username,String password);

     User checkUser(String username);

}
