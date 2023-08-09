package com.enstudy.demo.service.Impl;

import com.enstudy.demo.dao.WordsMapper;
import com.enstudy.demo.pojo.Words;
import com.enstudy.demo.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WordsServiceImpl {

    @Autowired
    private WordsMapper wordsMapper;

    /**
     * 查询当前页的单词信息
     * @param param
     * @return
     */
    public PageUtil listWordsByPage(HashMap param) {
        ArrayList<HashMap> list = wordsMapper.listPageWords(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }

    /**
     * 删除单词信息
     * @param ids
     * @return
     */
    public int deleteWordsByIds(Integer[] ids) {
        int rows = wordsMapper.deleteWordsByIds(ids);
        return rows;
    }

    /**
     * 添加单词信息
     * @param words
     * @return
     */

    public int insert(Words words) {

        Date now=new Date();
        words.setCreatedAt(now); //设置创建时间
        words.setUpdatedAt(now); //设置更新时间
        int rows = wordsMapper.insertSelective(words);
        return rows;
    }

    /**
     * 判断单词是否已经存在
     * @param words
     * @param id
     * @return
     */
    public boolean ifWordsExists(String words, Integer id) {
        Words word=wordsMapper.selectByWords(words);
        System.out.println("================================================");
        System.out.println(id);
        System.out.println("================================================");
        if(id==null)
            return words==null;//新增时判断是否重复
        else
            return words!=null&&id!=word.getId();//更新时判断是否重复
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Words selectById(Integer id){
        return wordsMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新单词信息
     * @param map
     * @return
     */

    public int update(HashMap map) {
        Date now=new Date();
        map.put("previousUpdatedAt",map.get("updatedAt"));//获取上次更新时间,在乐观锁中使用
        map.put("updatedAt",now); //设置本次更新时间
        int rows = wordsMapper.updateByPrimaryKeySelective(map);
        return rows;
    }

    /**
     * 查询有效单词
     * @return
     */
    public List<Words> listValidWords(){
        return wordsMapper.listValidWords();
    }

    /**
     * 查询当前页有效单词
     * @return
     */
    public PageUtil listPageValidWords(HashMap param){
        List<Words> list = wordsMapper.listPageValidWords(param);
        PageUtil pageUtils = new PageUtil(list, param);
        return pageUtils;
    }
    /**
     * 随机查询单词翻译
     * @return
     */
    public ArrayList<String> randWords(String word){
        ArrayList<String> list = wordsMapper.randWords(word);
        return list;
    }

//    /**
//     * 练习返回选项
//     * @param param
//     * @return
//     */
//    public PageUtil excise(HashMap param) {
//        ArrayList<HashMap> list = wordsMapper.listPageWords(param);
//        PageUtil pageUtils = new PageUtil(list, param);
//        return pageUtils;
//    }
}
