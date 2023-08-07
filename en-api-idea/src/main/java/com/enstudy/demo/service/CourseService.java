package com.enstudy.demo.service;

import com.enstudy.demo.dao.CourseMapper;
import com.enstudy.demo.pojo.Course;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
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
