package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Span;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface SpanMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Span record);

	/**
     * 根据主键查询单条数据
     */
    Span selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageSpan(HashMap param);

    /**
     * 删除文章信息
     * @param ids
     * @return
     */
    int deleteSpanByIds(Integer[] ids);
    /**
     * 根据内容查询文章
     * @param content
     * @return
     */
    Span selectByContent(String content);
    
    /**
     * 查询有效文章
     * @return
     */
    List<Span> listValidSpan();
    
    /**
     * 查询当前页有效文章
     * @return
     */
    List<Span> listPageValidSpan(HashMap map);
}
