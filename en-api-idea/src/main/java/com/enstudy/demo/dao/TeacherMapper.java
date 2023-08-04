package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Teacher;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface TeacherMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Teacher record);

	/**
     * 根据主键查询单条数据
     */
    Teacher selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageTeacher(HashMap param);

    /**
     * 删除讲师信息
     * @param ids
     * @return
     */
    int deleteTeacherByIds(Integer[] ids);
    /**
     * 根据编号查询讲师
     * @param number
     * @return
     */
    Teacher selectByNumber(String number);
    
    /**
     * 查询有效讲师
     * @return
     */
    List<Teacher> listValidTeacher();
    
    /**
     * 查询当前页有效讲师
     * @return
     */
    List<Teacher> listPageValidTeacher(HashMap map);
}
