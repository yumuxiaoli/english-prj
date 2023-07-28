package com.enstudy.demo.dao;

import com.enstudy.demo.pojo.Video;
import org.apache.ibatis.annotations.*;
import java.util.*;

@Mapper
public interface VideoMapper {

	/**
	* 根据主键删除一条记录
	*/
    int deleteByPrimaryKey(Integer id);

	/**
     * 插入一条数据
     */
    int insertSelective(Video record);

	/**
     * 根据主键查询单条数据
     */
    Video selectByPrimaryKey(Integer id);
	
	/**
     * 根据主键和时间戳进行更新
     */
	int updateByPrimaryKeySelective(HashMap map);
    
    /**
     * 查询当前页的数据
     */
    public ArrayList<HashMap> listPageVideo(HashMap param);

    /**
     * 删除视频信息
     * @param ids
     * @return
     */
    int deleteVideoByIds(Integer[] ids);
    /**
     * 根据题目查询视频
     * @param title
     * @return
     */
    Video selectByTitle(String title);
    
    /**
     * 查询有效视频
     * @return
     */
    List<Video> listValidVideo();
    
    /**
     * 查询当前页有效视频
     * @return
     */
    List<Video> listPageValidVideo(HashMap map);
}
