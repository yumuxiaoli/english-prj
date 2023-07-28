package com.enstudy.demo.service.Impl;

import com.enstudy.demo.dao.UserMapper;
import com.enstudy.demo.pojo.User;
import com.enstudy.demo.util.PageUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询当前页的学生信息
     * @param param
     * @return
     */
    public PageUtil listUserByPage(HashMap param) {
        ArrayList<HashMap> list = userMapper.listPageUser(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除学生信息
     * @param ids
     * @return
     */
    public int deleteUserByIds(Integer[] ids) {
        int rows = userMapper.deleteUserByIds(ids);
        return rows;
    }

    /**
     * 添加学生信息
     * @param user
     * @return
     */

    public int insert(User user) {

        Date now=new Date();
        user.setCreatedAt(now); //设置创建时间
        user.setUpdatedAt(now); //设置更新时间
        int rows = userMapper.insertSelective(user);
        return rows;
    }

    /**
     * 判断昵称是否已经存在
     * @param nickname
     * @param id
     * @return
     */
    public boolean ifNicknameExists(String nickname, Integer id) {
        User user=userMapper.selectByNickname(nickname);
        if(id==null)
            return user!=null;//新增时判断是否重复
        else
            return user!=null&&id!=user.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public User selectById(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新学生信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = userMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效学生
     * @return
     */
    public List<User> listValidUser(){
        return userMapper.listValidUser();
    }

    /**
     * 查询当前页有效学生
     * @return
     */
    public PageUtil listPageValidUser(HashMap param){
        List<User> list = userMapper.listPageValidUser(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
