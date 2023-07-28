package com.enstudy.demo.service.Impl;

import cn.hutool.core.convert.*;
import com.enstudy.demo.dao.CourseMapper;
import com.enstudy.demo.pojo.Course;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class CourseServiceImpl {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 查询当前页的课程信息
     * @param param
     * @return
     */
    public PageUtil listCourseByPage(HashMap param) {
        ArrayList<HashMap> list = courseMapper.listPageCourse(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除课程信息
     * @param ids
     * @return
     */
    public int deleteCourseByIds(Integer[] ids) {
        int rows = courseMapper.deleteCourseByIds(ids);
        return rows;
    }

    /**
     * 添加课程信息
     * @param course
     * @return
     */
    @Transactional(rollbackFor = Exception.class) //默认RuntimeException回滚,修改为Exception
    public int insert(Course course) {
        courseMapper.forwardMove(null, course.getSort());//从插入位置开始后移一位
        Date now=new Date();
        course.setCreatedAt(now); //设置创建时间
        course.setUpdatedAt(now); //设置更新时间
        int rows = courseMapper.insertSelective(course);
        return rows;
    }

    /**
     * 判断课程名是否已经存在
     * @param name
     * @param id
     * @return
     */
    public boolean ifNameExists(String name, Integer id) {
        Course course=courseMapper.selectByName(name);
        if(id==null)
            return course!=null;//新增时判断是否重复
        else
            return course!=null&&id!=course.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Course selectById(Integer id){
        return courseMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新课程信息
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class) //默认RuntimeException回滚,修改为Exception
    public int update(HashMap map) {
        int oldSort = Convert.toInt(map.get("oldSort"));//旧顺序
        int newSort = Convert.toInt(map.get("sort"));//新顺序
        if(oldSort< newSort){ //向后移
            courseMapper.backMove(oldSort+1, newSort);
        }
        if(oldSort> newSort){ //向前移
            courseMapper.forwardMove(oldSort-1, newSort);
        }
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = courseMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效课程
     * @return
     */
    public List<Course> listValidCourse(){
        return courseMapper.listValidCourse();
    }

    /**
     * 查询当前页有效课程
     * @return
     */
    public PageUtil listPageValidCourse(HashMap param){
        List<Course> list = courseMapper.listPageValidCourse(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
