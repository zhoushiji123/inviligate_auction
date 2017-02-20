package com.zsj.service;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.ResultMessage;

import java.util.List;

/**
 * Created by zsj on 2017/2/15.
 */
public interface MongoService {

    List<JSONObject> findAll();

    List<JSONObject> findByPage(JSONObject obj);

    ResultMessage findByName(JSONObject obj);

    void add(JSONObject obj);

    void updateByName(JSONObject obj);

    void deleteByName(JSONObject obj);


    JSONObject setId(JSONObject obj);

}
