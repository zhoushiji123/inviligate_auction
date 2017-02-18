package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.service.MongoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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


    public JSONObject findByName(JSONObject obj) {
        String name = obj.getString("name");
        JSONObject res = mongoTemplate.findOne(new Query(Criteria.where("name").is(name)),JSONObject.class,"users");
        return res;
    }

    public void add(JSONObject obj) {
        mongoTemplate.insert(obj,"users");
    }

    public void updateByName(JSONObject obj) {
        String name = obj.getString("name");
        int age = obj.getInteger("age");
        mongoTemplate.upsert(new Query(Criteria.where("name").is(name)),new Update().set("age",age).set("class",1),"users");
    }

    public void deleteByName(JSONObject obj) {
        String name = obj.getString("name");
        mongoTemplate.remove(new Query(Criteria.where("name").is(name)),"users");
    }
}
