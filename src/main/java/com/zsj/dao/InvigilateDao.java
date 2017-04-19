package com.zsj.dao;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.impl.ObjDaoImpl;
import com.zsj.model.PageModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by zsj on 2017/3/4.
 */
@Repository
public class InvigilateDao extends ObjDaoImpl {

    public static final String Invigilate = "invigilates";


    public PageModel<JSONObject> findByDate(JSONObject obj){

        Integer pageIndex = obj.getInteger("pageIndex") ;
        Integer pageSize  =  obj.getInteger("pageSize") ;
        if(pageSize == null || pageIndex == null) {
            pageSize = 100;
            pageIndex = 1;
        }else {
            obj.remove("pageIndex");
            obj.remove("pageSize");
        }

        String datetime = obj.remove("datetime").toString();

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

        criteria.and("datetime").gte(datetime+" 00:00:00").lte(datetime+" 23:59:59");


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
}
