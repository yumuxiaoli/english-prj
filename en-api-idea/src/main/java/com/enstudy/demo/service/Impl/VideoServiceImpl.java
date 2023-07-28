package com.enstudy.demo.service.Impl;

import com.enstudy.demo.dao.VideoMapper;
import com.enstudy.demo.pojo.Video;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VideoServiceImpl {

    @Autowired
    private VideoMapper videoMapper;

    /**
     * 查询当前页的视频信息
     * @param param
     * @return
     */
    public PageUtil listVideoByPage(HashMap param) {
        ArrayList<HashMap> list = videoMapper.listPageVideo(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除视频信息
     * @param ids
     * @return
     */
    public int deleteVideoByIds(Integer[] ids) {
        int rows = videoMapper.deleteVideoByIds(ids);
        return rows;
    }

    /**
     * 添加视频信息
     * @param video
     * @return
     */

    public int insert(Video video) {

        Date now=new Date();
        video.setCreatedAt(now); //设置创建时间
        video.setUpdatedAt(now); //设置更新时间
        int rows = videoMapper.insertSelective(video);
        return rows;
    }

    /**
     * 判断题目是否已经存在
     * @param title
     * @param id
     * @return
     */
    public boolean ifTitleExists(String title, Integer id) {
        Video video=videoMapper.selectByTitle(title);
        if(id==null)
            return video!=null;//新增时判断是否重复
        else
            return video!=null&&id!=video.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Video selectById(Integer id){
        return videoMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新视频信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = videoMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效视频
     * @return
     */
    public List<Video> listValidVideo(){
        return videoMapper.listValidVideo();
    }

    /**
     * 查询当前页有效视频
     * @return
     */
    public PageUtil listPageValidVideo(HashMap param){
        List<Video> list = videoMapper.listPageValidVideo(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}

