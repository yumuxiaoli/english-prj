package com.enstudy.demo.service.Impl;

import cn.hutool.core.convert.*;
import com.enstudy.demo.dao.TeacherMapper;
import com.enstudy.demo.pojo.Teacher;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class TeacherServiceImpl {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 查询当前页的讲师信息
     * @param param
     * @return
     */
    public PageUtil listTeacherByPage(HashMap param) {
        ArrayList<HashMap> list = teacherMapper.listPageTeacher(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除讲师信息
     * @param ids
     * @return
     */
    public int deleteTeacherByIds(Integer[] ids) {
        int rows = teacherMapper.deleteTeacherByIds(ids);
        return rows;
    }

    /**
     * 添加讲师信息
     * @param teacher
     * @return
     */
    @Transactional(rollbackFor = Exception.class) //默认RuntimeException回滚,修改为Exception
    public int insert(Teacher teacher) {
        teacherMapper.forwardMove(null, teacher.getSort());//从插入位置开始后移一位
        Date now=new Date();
        teacher.setCreatedAt(now); //设置创建时间
        teacher.setUpdatedAt(now); //设置更新时间
        int rows = teacherMapper.insertSelective(teacher);
        return rows;
    }

    /**
     * 判断编号是否已经存在
     * @param number
     * @param id
     * @return
     */
    public boolean ifNumberExists(Integer number, Integer id) {
        Teacher teacher=teacherMapper.selectByNumber(number);
        if(id==null)
            return teacher!=null;//新增时判断是否重复
        else
            return teacher!=null&&id!=teacher.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Teacher selectById(Integer id){
        return teacherMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新讲师信息
     * @param map
     * @return
     */
    @Transactional(rollbackFor = Exception.class) //默认RuntimeException回滚,修改为Exception
    public int update(HashMap map) {
        int oldSort = Convert.toInt(map.get("oldSort"));//旧顺序
        int newSort = Convert.toInt(map.get("sort"));//新顺序
        if(oldSort< newSort){ //向后移
            teacherMapper.backMove(oldSort+1, newSort);
        }
        if(oldSort> newSort){ //向前移
            teacherMapper.forwardMove(oldSort-1, newSort);
        }
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = teacherMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效讲师
     * @return
     */
    public List<Teacher> listValidTeacher(){
        return teacherMapper.listValidTeacher();
    }

    /**
     * 查询当前页有效讲师
     * @return
     */
    public PageUtil listPageValidTeacher(HashMap param){
        List<Teacher> list = teacherMapper.listPageValidTeacher(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
