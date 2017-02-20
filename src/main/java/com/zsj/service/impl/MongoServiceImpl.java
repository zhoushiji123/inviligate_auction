package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.ResultMessage;
import com.zsj.service.MongoService;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsj on 2017/2/15.
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<JSONObject> findAll() {
        List<JSONObject> datalist = new ArrayList<JSONObject>();
        List<JSONObject> list =  mongoTemplate.findAll(JSONObject.class, "users");
        for(JSONObject jsonObject : list){
            datalist.add(this.setId(jsonObject));
        }
        return datalist;
    }


    public ResultMessage findByName(JSONObject obj) {
        ResultMessage resultMessage  = new ResultMessage();
        String name = obj.getString("name");
        JSONObject res = mongoTemplate.findOne(new Query(Criteria.where("name").is(name)),JSONObject.class,"users");
        res = this.setId(res);
        resultMessage.setData(res);
        return resultMessage;
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



    public JSONObject setId(JSONObject obj) {
        ObjectId objectId = obj.getObject("_id",ObjectId.class);
        String id =objectId.toHexString();
        obj.put("_id",id);
        return obj;
    }

    public List<JSONObject> findByPage(JSONObject obj) {
        int pageindex = obj.getInteger("pageIndex");
        int pageSize = obj.getInteger("pageSize");
        List<JSONObject> datalist = new ArrayList<JSONObject>();
//        List<JSONObject> list = mongoTemplate.find(new Query(Criteria.where("").));
        return null;
    }
}
