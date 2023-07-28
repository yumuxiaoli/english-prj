package com.enstudy.demo.service.Impl;

import cn.hutool.core.convert.*;
import cn.hutool.core.date.*;
import com.enstudy.demo.dao.SpanMapper;
import com.enstudy.demo.pojo.Span;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SpanServiceImpl {

    @Autowired
    private SpanMapper spanMapper;

    /**
     * 查询当前页的文章信息
     * @param param
     * @return
     */
    public PageUtil listSpanByPage(HashMap param) {
        ArrayList<HashMap> list = spanMapper.listPageSpan(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除文章信息
     * @param ids
     * @return
     */
    public int deleteSpanByIds(Integer[] ids) {
        int rows = spanMapper.deleteSpanByIds(ids);
        return rows;
    }

    /**
     * 添加文章信息
     * @param span
     * @return
     */

    public int insert(Span span) {

        Date now=new Date();
        span.setCreatedAt(now); //设置创建时间
        span.setUpdatedAt(now); //设置更新时间
        int rows = spanMapper.insertSelective(span);
        return rows;
    }

    /**
     * 判断内容是否已经存在
     * @param content
     * @param id
     * @return
     */
    public boolean ifContentExists(String content, Integer id) {
        Span span=spanMapper.selectByContent(content);
        if(id==null)
            return span!=null;//新增时判断是否重复
        else
            return span!=null&&id!=span.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Span selectById(Integer id){
        return spanMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新文章信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = spanMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效文章
     * @return
     */
    public List<Span> listValidSpan(){
        return spanMapper.listValidSpan();
    }

    /**
     * 查询当前页有效文章
     * @return
     */
    public PageUtil listPageValidSpan(HashMap param){
        List<Span> list = spanMapper.listPageValidSpan(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
