package com.laughing.help.biz.common;

/**
 * 分页基类
 *
 * @author lifei.pan
 * @create 2018-01-17 上午10:34
 **/
public class PageRequest {
    /**
     * 支持的排序查询(逗号分隔)；支持部分字段
     */
    private String sortList;

    /**
     * 页数，从1开始; 不传默认
     */
    private Integer pageIndex = Integer.valueOf(1);
    /**
     * 一页多少条数据； 不传默认5000
     */
    private Integer pageSize = Integer.valueOf(5000);
    /**
     * 当前页数
     */
    private int currentPage;

    public String getSortList() {
        return sortList;
    }

    public int getCurrentPage() {
        return (pageIndex - 1) * pageSize < 0 ? 0 : ((pageIndex - 1) * pageSize);
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setSortList(String sortList) {
        this.sortList = sortList;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
