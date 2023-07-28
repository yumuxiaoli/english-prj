package com.enstudy.demo.util;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class PageUtil implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int pageIndex;
    /**
     * 列表数据
     */
    private List list;

    public PageUtil(List list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public PageUtil(List list, HashMap param) {
        this.list = list;
        long count = (Integer) param.get("totalCount");
        int page = (Integer)param.get("page");
        int length = (Integer) param.get("length");
        this.totalCount = count;
        this.pageSize = length;
        this.pageIndex = (page - 1) * length;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

}
