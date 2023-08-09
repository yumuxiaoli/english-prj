package com.enstudy.demo.service.Impl;


import com.enstudy.demo.dao.ExciseMapper;
import com.enstudy.demo.pojo.Excise;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ExciseServiceImpl {

    @Autowired
    private ExciseMapper exciseMapper;

    /**
     * 查询当前页的练习信息
     * @param param
     * @return
     */
    public PageUtil listExciseByPage(HashMap param) {
        ArrayList<HashMap> list = exciseMapper.listPageExcise(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除练习信息
     * @param ids
     * @return
     */
    public int deleteExciseByIds(Integer[] ids) {
        int rows = exciseMapper.deleteExciseByIds(ids);
        return rows;
    }

    /**
     * 添加练习信息
     * @param excise
     * @return
     */

    public int insert(Excise excise) {

        Date now=new Date();
        excise.setCreatedAt(now); //设置创建时间
        excise.setUpdatedAt(now); //设置更新时间
        int rows = exciseMapper.insertSelective(excise);
        return rows;
    }

    /**
     * 判断题目是否已经存在
     * @param title
     * @param id
     * @return
     */
    public boolean ifTitleExists(String title, Integer id) {
        Excise excise=exciseMapper.selectByTitle(title);
        if(id==null)
            return excise!=null;//新增时判断是否重复
        else
            return excise!=null&&id!=excise.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Excise selectById(Integer id){
        return exciseMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新练习信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = exciseMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效练习
     * @return
     */
    public List<Excise> listValidExcise(){
        return exciseMapper.listValidExcise();
    }

    /**
     * 查询当前页有效练习
     * @return
     */
    public PageUtil listPageValidExcise(HashMap param){
        List<Excise> list = exciseMapper.listPageValidExcise(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
