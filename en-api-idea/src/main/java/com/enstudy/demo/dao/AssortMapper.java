package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Assort;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface AssortMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Assort record);

	/**
     * 根据主键查询单条数据
     */
    Assort selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageAssort(HashMap param);

    /**
     * 删除分类信息
     * @param ids
     * @return
     */
    int deleteAssortByIds(Integer[] ids);
    /**
     * 根据职位名称查询分类
     * @param name
     * @return
     */
    Assort selectByName(String name);
    
    /**
     * 查询有效分类
     * @return
     */
    List<Assort> listValidAssort();
    
    /**
     * 查询当前页有效分类
     * @return
     */
    List<Assort> listPageValidAssort(HashMap map);
}
