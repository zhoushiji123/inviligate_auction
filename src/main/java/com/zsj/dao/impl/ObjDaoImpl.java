package com.zsj.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.ObjDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zsj on 2017/2/21.
 */
public  abstract class ObjDaoImpl  implements ObjDao {


    @Autowired
    public MongoTemplate mongoTemplate;

    public String collectionName ;




    public abstract ResultMessage updateById(JSONObject obj) ;



    public PageModel<JSONObject> findByPage(JSONObject obj) {
        int pageindex = obj.getInteger("pageIndex");
        int pageSize = obj.getInteger("pageSize");
        collectionName = obj.getString("collectionName");
        PageModel<JSONObject> pageModel = new PageModel();

        List<JSONObject> datalist = new ArrayList();
        Query query = new Query().skip( (pageindex -1) * pageSize).limit(pageSize);
        List<JSONObject> list = mongoTemplate.find(query,JSONObject.class,collectionName);

        if(list.size() == 0){
            pageModel.setMessage("没有查询到数据");
            pageModel.setSuccess(false);
            return  pageModel;
        }

        for(JSONObject jsonObject : list){
            datalist.add(this.setId(jsonObject));
        }

        pageModel.setData(datalist);
        pageModel.setCount(datalist.size());
        pageModel.setPageSize(pageSize);
        pageModel.setPageIndex(pageindex);

        return pageModel;
    }

    public PageModel<JSONObject> findAll(JSONObject obj) {
        PageModel<JSONObject> pageModel  = new PageModel();
        collectionName = obj.getString("collectionName");
        List<JSONObject> datalist = new ArrayList<JSONObject>();

        List<JSONObject> list =  mongoTemplate.findAll(JSONObject.class, collectionName);

        if(list.size() == 0){
            pageModel.setMessage("没有查询到数据");
            pageModel.setSuccess(false);
            return  pageModel;
        }

        for(JSONObject jsonObject : list){
            datalist.add(this.setId(jsonObject));
        }
        pageModel.setPageSize(100);
        pageModel.setPageIndex(1);
        pageModel.setCount(datalist.size());
        pageModel.setData(datalist);

        return pageModel;
    }

    public ResultMessage findById(JSONObject obj) {
        ResultMessage resultMessage  = new ResultMessage();
        String id = obj.getString("_id");
        collectionName = obj.getString("collectionName");
        Query query = new Query(Criteria.where("_id").is(id));

        JSONObject res = mongoTemplate.findOne(query,JSONObject.class,collectionName);
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

    public ResultMessage insert(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.remove("collectionName").toString();
        mongoTemplate.insert(obj,collectionName);
        resultMessage.setMessage("添加成功");
        return resultMessage;
    }


    public ResultMessage deleteById(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.remove("collectionName").toString();
        String id =  obj.getString("_id");
        mongoTemplate.remove(new Query(Criteria.where("_id").is(id)),collectionName);
        resultMessage.setMessage("删除成功");
        return resultMessage;
    }

    public JSONObject setId(JSONObject obj) {
        ObjectId objectId = obj.getObject("_id",ObjectId.class);
        String id =objectId.toHexString();
        obj.put("_id",id);
        return obj;
    }
}
