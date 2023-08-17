package com.enstudy.demo.service;

import com.enstudy.demo.pojo.Excise;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

public interface ExciseService {

    public PageUtil listExciseByPage(HashMap param);

    public int deleteExciseByIds(Integer[] ids);
    
    public int insert(Excise excise);

    public boolean ifTitleExists(String title, Integer id);

    public Excise selectById(Integer id);
    
    public int update(HashMap map) ;

    public List<Excise> listValidExcise();

    public PageUtil listPageValidExcise(HashMap param);

    public ArrayList<HashMap> questionlist();
}
