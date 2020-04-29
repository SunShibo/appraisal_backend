package com.wisewin.api.query;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 */
public class QueryInfo implements Serializable {
    private static final long serialVersionUID = 961699786815859106L;
    /**
     * 排序方式: asc
     */
    public static final String ORDER_ASC = "ASC";
    /**
     * 排序方式: desc
     */
    public static final String ORDER_DESC = "DESC";

    private Integer pageSize = 10;
    private Integer pageOffset = 1;
    private String sort;
    private String order;

    public QueryInfo(Integer pageSize, String sort,
                     String order, Integer pageOffset) {
        this.pageSize = pageSize;
        this.sort = sort;
        this.order = order;
        this.pageOffset = pageOffset < 0 ? 0 : pageOffset;
    }

    public QueryInfo() {
        super();
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
