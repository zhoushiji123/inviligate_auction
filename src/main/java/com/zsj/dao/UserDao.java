package com.zsj.dao;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.impl.ObjDaoImpl;
import com.zsj.model.ResultMessage;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by zsj on 2017/2/21.
 */
@Repository
public class UserDao extends ObjDaoImpl{


    @Override
    public ResultMessage updateById(JSONObject obj) {
        ResultMessage resultMessage = new ResultMessage();
        collectionName = obj.getString("collectionName");
        String id  = obj.getString("_id");
        Query query = new Query(Criteria.where("_id").is(id));



        return resultMessage;
    }
}
