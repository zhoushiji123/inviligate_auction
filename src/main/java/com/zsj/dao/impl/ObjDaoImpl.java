package com.zsj.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.ObjDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.util.DateUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by zsj on 2017/2/21.
 */
public  class ObjDaoImpl  implements ObjDao {

    @Autowired
    public MongoTemplate mongoTemplate;
    public String collectionName ;

    public PageModel<JSONObject> findByPage(JSONObject obj) {
        Integer pageIndex = obj.getInteger("pageIndex") ;
        Integer pageSize  =  obj.getInteger("pageSize") ;
        if(pageSize == null || pageIndex == null){
            pageSize = 100;
            pageIndex = 1;
        }
        collectionName = obj.getString("collectionName");
        PageModel<JSONObject> pageModel = new PageModel();

//        Criteria criteria ;
//        criteria = Criteria.where("create_time").gt("2017-03-05 17:37:00").where("create_time").lt("2017-03-05 17:38:00");

        List<JSONObject> datalist = new ArrayList();
        Query query = new Query().skip( (pageIndex -1) * pageSize).limit(pageSize).with(new Sort(Sort.Direction.DESC,"create_time"));
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
        pageModel.setPageIndex(pageIndex);
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

    public PageModel<JSONObject> findByTerm(JSONObject obj) {
        Integer pageIndex = obj.getInteger("pageIndex") ;
        Integer pageSize  =  obj.getInteger("pageSize") ;
        if(pageSize == null || pageIndex == null) {
            pageSize = 100;
            pageIndex = 1;
        }else {
            obj.remove("pageIndex");
            obj.remove("pageSize");
        }

        Object datetime = obj.remove("datetime");

        PageModel<JSONObject> pageModel = new PageModel<JSONObject>();
        collectionName = obj.remove("collectionName").toString();
        Criteria criteria ;

        Set<String> keySet = obj.keySet();
        Iterator<String> keyIterator =  keySet.iterator();
        if(keySet.size() == 1){
            //只有1个条件
            String key = keyIterator.next();
            Object value = obj.getObject(key,Object.class);
            criteria = Criteria.where(key).is(value);
        }else{
            //有多个条件
            String key = keyIterator.next();
            Object value = obj.getObject(key,Object.class);
            criteria = Criteria.where(key).is(value);

            while (keyIterator.hasNext()){
                key = keyIterator.next();
                value = obj.getObject(key,Object.class);
                criteria.and(key).is(value);
            }
        }

        if(datetime!=null){
            criteria.and("datetime").gte(DateUtil.getCurrentTime());
        }

        Query query = new Query(criteria).skip((pageIndex -1)*pageSize ).limit(pageSize).with(new Sort(Sort.Direction.DESC,"create_time"));

        List<JSONObject> resList = mongoTemplate.find(query,JSONObject.class,collectionName);
        if(resList.size()  == 0){
            pageModel.setSuccess(false);
            pageModel.setMessage("没有查询到数据");
        }
        else{
            List<JSONObject> dataList = new ArrayList<JSONObject>();
            for(JSONObject res : resList){
                res = this.setId(res);
                dataList.add(res);
            }
            pageModel.setData(dataList);
            pageModel.setCount(dataList.size());
            pageModel.setPageIndex(pageIndex);
            pageModel.setPageSize(pageSize);
        }

        return pageModel;
    }

    public ResultMessage insert(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.remove("collectionName").toString();
        obj.put("create_time", DateUtil.getCurrentTime());
        mongoTemplate.insert(obj,collectionName);
        resultMessage.setMessage("添加成功");
        return resultMessage;
    }

    public ResultMessage update(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.getString("collectionName");

        JSONObject findParam = new JSONObject();
        findParam.put("collectionName",collectionName);
        PageModel<JSONObject> pageModel = this.findAll(findParam);
        if(pageModel.getCount() == 0){
            resultMessage.setSuccess(false);
            resultMessage.setMessage("没有查询到数据，不能更新操作");
            return resultMessage;
        }


        JSONObject queryParam = obj.getJSONObject("queryParam");
        JSONObject updateParam  = obj.getJSONObject("updateParam");

        Criteria criteria ;
        Update update = new Update();

        Set<String> queryKeySet = queryParam.keySet();
        Iterator<String> queryIterator = queryKeySet.iterator();
        Set<String> updateKeySet = updateParam.keySet();
        Iterator<String> updateIterator =  updateKeySet.iterator();

        if(queryKeySet.size() == 1){
            //只有1个查询条件
            String key = queryIterator.next();
            Object value = queryParam.get(key);
            criteria = Criteria.where(key).is(value);
        }else{
            //有多个查询条件
            String key = queryIterator.next();
            Object value = queryParam.get(key);
            criteria = Criteria.where(key).is(value);
            while (queryIterator.hasNext()){
                key = queryIterator.next();
                value = queryParam.get(key);
                criteria.and(key).is(value);
            }
        }

        while(updateIterator.hasNext()){
            String key = updateIterator.next();
            Object value = updateParam.get(key);
            update.set(key,value);
        }

        Query query = new Query(criteria);
        mongoTemplate.updateMulti(query,update,collectionName);

        resultMessage.setMessage("修改成功");
        return resultMessage;
    }


    public ResultMessage deleteByTerm(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.remove("collectionName").toString();

        Criteria criteria ;
        Set<String> keySet = obj.keySet();
        Iterator<String> keyIterator =  keySet.iterator();
        if(keySet.size() == 1){
            //只有1个条件
            String key = keyIterator.next();
            Object value = obj.getObject(key,Object.class);
            criteria = Criteria.where(key).is(value);
        }else{
            //有多个条件
            String key = keyIterator.next();
            Object value = obj.getObject(key,Object.class);
            criteria = Criteria.where(key).is(value);

            while (keyIterator.hasNext()){
                key = keyIterator.next();
                value = obj.getObject(key,Object.class);
                criteria.and(key).is(value);
            }
        }

        Query query = new Query(criteria);
        mongoTemplate.remove(query,collectionName);

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
