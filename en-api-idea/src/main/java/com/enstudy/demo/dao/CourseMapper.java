package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Course;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface CourseMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Course record);

	/**
     * 根据主键查询单条数据
     */
    Course selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageCourse(HashMap param);

    /**
     * 删除课程信息
     * @param ids
     * @return
     */
    int deleteCourseByIds(Integer[] ids);
    /**
     * 根据课程名查询课程
     * @param name
     * @return
     */
    Course selectByName(String name);
    
    /**
     * 查询有效课程
     * @return
     */
    List<Course> listValidCourse();
    
    /**
     * 查询当前页有效课程
     * @return
     */
    List<Course> listPageValidCourse(HashMap map);
}
