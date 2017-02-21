package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
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
        Query query = new Query();
        List<JSONObject> list =  mongoTemplate.findAll(JSONObject.class, "users");
        for(JSONObject jsonObject : list){
            datalist.add(this.setId(jsonObject));
        }
        return datalist;
    }


    public ResultMessage findById(JSONObject obj) {
        ResultMessage resultMessage  = new ResultMessage();
        String id = obj.getString("_id");

        Query query = new Query(Criteria.where("_id").is(id));

        JSONObject res = mongoTemplate.findOne(query,JSONObject.class,"users");
        if(res  == null){
            resultMessage.setSuccess(false);
            resultMessage.setMessage("没有查询到数据");
        }
        else{
            res = this.setId(res);
            resultMessage.setData(res);
        }

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

    public PageModel<JSONObject> findByPage(JSONObject obj) {
        int pageindex = obj.getInteger("pageIndex");
        int pageSize = obj.getInteger("pageSize");

        PageModel<JSONObject> pageModel = new PageModel();

        List<JSONObject> datalist = new ArrayList();
        Query query = new Query().skip( (pageindex -1) * pageSize).limit(pageSize);
        List<JSONObject> list = mongoTemplate.find(query,JSONObject.class,"users");

        for(JSONObject jsonObject : list){
            datalist.add(this.setId(jsonObject));
        }

        pageModel.setData(datalist);
        pageModel.setCount(datalist.size());
        pageModel.setPageSize(pageSize);
        pageModel.setPageIndex(pageindex);

        return pageModel;
    }
}
