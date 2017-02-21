package com.zsj.model;

import java.util.List;

/**
 * Created by zsj on 2017/2/21.
 */
public class PageModel<T> {

    private  int pageIndex  ;
    private  int pageSize ;
    private  boolean  success = true ;
    private List<T> data ;
    private  int count ;
    private  String message  = "查询成功" ;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "PageModel{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", success=" + success +
                ", data=" + data +
                ", count=" + count +
                ", message='" + message + '\'' +
                '}';
    }
}
