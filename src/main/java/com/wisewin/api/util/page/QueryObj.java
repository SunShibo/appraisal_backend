package com.wisewin.api.util.page;

/**
 * Created by sunshibo on 2016/4/8.
 */
public class QueryObj {

    private Integer pageNum ;
    private Integer pageSize = 10 ;
    private Integer lastItemId ;
    private Integer pageOffset ;
    private String search ;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getLastItemId() {
        return lastItemId;
    }

    public void setLastItemId(Integer lastItemId) {
        this.lastItemId = lastItemId;
    }

    public Integer getPageOffset() {
        if (pageNum == null || pageSize == null ) {
            return null ;
        }
        return (pageNum - 1) * pageSize;
    }

    public void setPageOffset(Integer pageOffset) {
        this.pageOffset = pageOffset;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
