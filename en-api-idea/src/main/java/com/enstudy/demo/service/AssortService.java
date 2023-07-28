package com.enstudy.demo.service;


import com.enstudy.demo.pojo.Assort;
import com.enstudy.demo.util.PageUtil;

import java.util.*;


public interface AssortService {


    public PageUtil listAssortByPage(HashMap param);


    public int deleteAssortByIds(Integer[] ids);


    public int insert(Assort assort);
    

    public boolean ifNameExists(String name, Integer id);

    public Assort selectById(Integer id);

    public int update(HashMap map);

    public List<Assort> listValidAssort();

    public PageUtil listPageValidAssort(HashMap param);
}
