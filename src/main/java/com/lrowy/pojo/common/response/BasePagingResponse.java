package com.lrowy.pojo.common.response;

import java.io.Serializable;

public class BasePagingResponse<T>  extends BaseResponse<T>  implements Serializable {
    private static final long serialVersionUID = -2318695489851376315L;

    //公共变量
    public static final String ASC = "asc";
    public static final String DESC = "desc";

    //当前页数
    private int pageNo = 1;

    //每页条数
    private int pageSize = 20;

    /**
     * 结果总数
     * @TODO:是否用实际数量？或大数据量时显示当前最大的数目。
     */
    private long totalCount = -1;

    public BasePagingResponse(){

    }

    public BasePagingResponse(int pageNo) {
        this.pageNo = pageNo;
    }

    public BasePagingResponse(int pageNo,int pageSize) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }


    /**
     * 获得每页显示条数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 根据pageNo和pageSize计算当前页最后一条记录在总结果集中的位置,序号从1开始.
     */
    public int getLast() {
        int last = ((pageNo) * pageSize);
        return last > totalCount ? (int) totalCount : last;
    }

    /**
     * 获得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    private long getTotalPages() {
        if (totalCount < 0) {
            return -1;
        }

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    private boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 取得下页的页号, 序号从1开始.
     * 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否还有上一页.
     */
    private boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 取得上页的页号, 序号从1开始.
     * 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    public int getLastPage() {
        int totalPage = (int) (totalCount / pageSize);
        totalPage = totalCount % pageSize > 0 ? totalPage + 1 : totalPage;
        return totalPage == 0 ? 1 : totalPage;
    }
}
