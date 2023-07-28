package com.enstudy.demo.service.Impl;

;
import com.enstudy.demo.dao.AssortMapper;
import com.enstudy.demo.pojo.Assort;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AssortServiceImpl {

    @Autowired
    private AssortMapper assortMapper;

    /**
     * 查询当前页的分类信息
     * @param param
     * @return
     */
    public PageUtil listAssortByPage(HashMap param) {
        ArrayList<HashMap> list = assortMapper.listPageAssort(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除分类信息
     * @param ids
     * @return
     */
    public int deleteAssortByIds(Integer[] ids) {
        int rows = assortMapper.deleteAssortByIds(ids);
        return rows;
    }

    /**
     * 添加分类信息
     * @param assort
     * @return
     */

    public int insert(Assort assort) {

        Date now=new Date();
        assort.setCreatedAt(now); //设置创建时间
        assort.setUpdatedAt(now); //设置更新时间
        int rows = assortMapper.insertSelective(assort);
        return rows;
    }

    /**
     * 判断职位名称是否已经存在
     * @param name
     * @param id
     * @return
     */
    public boolean ifNameExists(String name, Integer id) {
        Assort assort=assortMapper.selectByName(name);
        if(id==null)
            return assort!=null;//新增时判断是否重复
        else
            return assort!=null&&id!=assort.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Assort selectById(Integer id){
        return assortMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新分类信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = assortMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效分类
     * @return
     */
    public List<Assort> listValidAssort(){
        return assortMapper.listValidAssort();
    }

    /**
     * 查询当前页有效分类
     * @return
     */
    public PageUtil listPageValidAssort(HashMap param){
        List<Assort> list = assortMapper.listPageValidAssort(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
}
