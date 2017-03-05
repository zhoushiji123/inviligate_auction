package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.InvigilateDao;
import com.zsj.model.ResultMessage;
import com.zsj.service.InvigilateService;
import com.zsj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsj on 2017/3/4.
 */
@Service
public class InvigilateServiceImpl implements InvigilateService{

    private String collectionName =  "invigilates";

    @Autowired
    private InvigilateDao invigilateDao;


    public ResultMessage addInvigilate(JSONObject obj) {
        ResultMessage resultMessage ;
        obj.put("collectionName",collectionName);
        resultMessage = invigilateDao.insert(obj);
        return resultMessage;
    }
}
