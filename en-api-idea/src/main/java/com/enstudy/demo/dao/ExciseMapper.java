package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Excise;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface ExciseMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Excise record);

	/**
     * 根据主键查询单条数据
     */
    Excise selectByPrimaryKey(Integer id);

	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);

    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageExcise(HashMap param);

    /**
     * 删除练习信息
     * @param ids
     * @return
     */
    int deleteExciseByIds(Integer[] ids);
    /**
     * 根据题目查询练习
     * @param title
     * @return
     */Excise selectByTitle(String title);

    /**
     * 查询有效练习
     * @return
     */
    List<Excise> listValidExcise();

    /**
     * 查询当前页有效练习
     * @return
     */
    List<Excise> listPageValidExcise(HashMap map);
}
