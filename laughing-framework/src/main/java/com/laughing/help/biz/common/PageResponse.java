package com.laughing.help.biz.common;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应基类
 *
 * @author lifei.pan
 * @create 2018-01-17 上午10:34
 **/
public class PageResponse<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 4300652266386688926L;

    /**
     * 符合条件的总记录数
     */
    private Long totalRecords = 0L;
    /**
     * 当前所在的页数
     */
    private Integer pageIndex = 0;
    /**
     * 一页默认的条数; 默认5000
     */
    private Integer pageSize = 5000;
    /**
     * 当前页记录
     */
    private List<T> records;

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
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

    /**
     * Getter method for property <tt>records</tt>.
     *
     * @return property value of records
     */
    public List<T> getRecords() {
        return records;
    }

    /**
     * Setter method for property <tt>records</tt>.
     *
     * @param records value to be assigned to property records
     */
    public void setRecords(List<T> records) {
        this.records = records;
    }
}
