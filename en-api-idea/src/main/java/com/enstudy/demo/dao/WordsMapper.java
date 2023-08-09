package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Words;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface WordsMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Words record);

	/**
     * 根据主键查询单条数据
     */
    Words selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageWords(HashMap param);

    /**
     * 删除单词信息
     * @param ids
     * @return
     */
    int deleteWordsByIds(Integer[] ids);
    /**
     * 根据单词查询单词
     * @param words
     * @return
     */
    Words selectByWords(String words);
    
    /**
     * 查询有效单词
     * @return
     */
    List<Words> listValidWords();
    
    /**
     * 查询当前页有效单词
     * @return
     */
    List<Words> listPageValidWords(HashMap map);

    /**
     * 随机查询单词翻译
     * @return
     */
    public ArrayList<String> randWords(String words);
}
