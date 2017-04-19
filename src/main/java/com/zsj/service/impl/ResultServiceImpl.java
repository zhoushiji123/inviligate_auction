package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.ResultDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsj on 2017/4/19.
 */
@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultDao resultDao;


    public PageModel<JSONObject> getResult(JSONObject obj) {
        obj.put("collectionName", ResultDao.Results);
        return resultDao.findByPage(obj);
    }

    public ResultMessage delResult(JSONObject obj) {
        obj.put("collectionName", ResultDao.Results);
        return resultDao.deleteByTerm(obj);
    }
}
