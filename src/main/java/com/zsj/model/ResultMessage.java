package com.zsj.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by zsj on 2017/2/18.
 */
public class ResultMessage implements Serializable {

    private  boolean success = true;
    private Object data ;
    private String message = "操作成功";


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
       return JSON.toJSONString(this);
    }
}

