package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.service.MongoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zsj on 2017/2/15.
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<JSONObject> findAll() {
        List<JSONObject> list =  mongoTemplate.findAll(JSONObject.class, "users");
        return list;
    }
}
