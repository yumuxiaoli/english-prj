package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.User;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface UserMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(User record);

	/**
     * 根据主键查询单条数据
     */
    User selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageUser(HashMap param);

    /**
     * 删除学生信息
     * @param ids
     * @return
     */
    int deleteUserByIds(Integer[] ids);
    /**
     * 根据昵称查询学生
     * @param nickname
     * @return
     */
    User selectByNickname(String nickname);
    
    /**
     * 查询有效学生
     * @return
     */
    List<User> listValidUser();
    
    /**
     * 查询当前页有效学生
     * @return
     */
    List<User> listPageValidUser(HashMap map);

    User checkUser(String username);

    public HashMap searchUserSummary(Integer userId);

    public int updatePassword(HashMap param);
}
