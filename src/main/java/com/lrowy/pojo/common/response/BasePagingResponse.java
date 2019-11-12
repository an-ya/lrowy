package com.lrowy.pojo.common.response;

import java.io.Serializable;

public class BasePagingResponse<T>  extends BaseResponse<T>  implements Serializable {
    private static final long serialVersionUID = -2318695489851376315L;

    //当前页数
    private int pageNo = 1;

    //每页条数
    private int pageSize = 20;

    //总数
    private int totalCount = -1;

    public BasePagingResponse() {

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
