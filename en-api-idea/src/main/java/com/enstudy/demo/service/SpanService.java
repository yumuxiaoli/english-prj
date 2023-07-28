package com.enstudy.demo.service;

import cn.hutool.core.convert.*;
import cn.hutool.core.date.*;
import com.enstudy.demo.pojo.Span;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

public interface SpanService {

    public PageUtil listSpanByPage(HashMap param) ;
    public int deleteSpanByIds(Integer[] ids);

    public int insert(Span span);

    public boolean ifContentExists(String content, Integer id);

    public Span selectById(Integer id);

    public int update(HashMap map) ;

    public List<Span> listValidSpan();

    public PageUtil listPageValidSpan(HashMap param);
}
