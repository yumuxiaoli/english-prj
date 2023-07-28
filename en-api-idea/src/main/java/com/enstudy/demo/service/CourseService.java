package com.enstudy.demo.service;

import com.enstudy.demo.pojo.Course;
import com.enstudy.demo.util.PageUtil;
import java.util.*;

public interface CourseService {

    public PageUtil listCourseByPage(HashMap param);

    public int deleteCourseByIds(Integer[] ids);

    public int insert(Course course);

    public boolean ifNameExists(String name, Integer id);

    public Course selectById(Integer id);

    public int update(HashMap map);

    public List<Course> listValidCourse();

    public PageUtil listPageValidCourse(HashMap param);
}
