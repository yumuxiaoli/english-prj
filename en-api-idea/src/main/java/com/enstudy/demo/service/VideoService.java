package com.enstudy.demo.service;

import com.enstudy.demo.pojo.Video;
import com.enstudy.demo.util.PageUtil;
import java.util.*;

public interface VideoService {
    public PageUtil listVideoByPage(HashMap param);

    public int deleteVideoByIds(Integer[] ids);
    
    public int insert(Video video);

    public boolean ifTitleExists(String title, Integer id);

    public Video selectById(Integer id);
    
    public int update(HashMap map);

    public List<Video> listValidVideo();

    public PageUtil listPageValidVideo(HashMap param);

}
