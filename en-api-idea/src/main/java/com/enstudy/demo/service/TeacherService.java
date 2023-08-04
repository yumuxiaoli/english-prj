package com.enstudy.demo.service;


import com.enstudy.demo.pojo.Teacher;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public interface TeacherService {

    public PageUtil listTeacherByPage(HashMap param);

    public int deleteTeacherByIds(Integer[] ids);
    
    public int insert(Teacher teacher);

    public boolean ifNumberExists(String number, Integer id) ;

    public Teacher selectById(Integer id);

    public int update(HashMap map);

    public List<Teacher> listValidTeacher();

    public PageUtil listPageValidTeacher(HashMap param);
}
