package com.enstudy.demo.service;
import com.enstudy.demo.pojo.Words;
import com.enstudy.demo.util.PageUtil;
import java.util.*;

public interface WordsService {

    public PageUtil listWordsByPage(HashMap param);

    public int deleteWordsByIds(Integer[] ids);
    
    public int insert(Words words);

    public boolean ifWordsExists(String words, Integer id);

    public Words selectById(Integer id);

    public int update(HashMap map);

    public List<Words> listValidWords();

    public PageUtil listPageValidWords(HashMap param);

    public ArrayList<String> randwords(String word);
}
