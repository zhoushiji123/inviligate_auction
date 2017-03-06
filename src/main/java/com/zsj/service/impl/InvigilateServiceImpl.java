package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.InvigilateDao;
import com.zsj.model.PageModel;
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


    public PageModel<JSONObject> getInvigilates(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.findByTerm(obj);
    }


    public PageModel<JSONObject> getInvigilatesByDate(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.findByDate(obj);
    }



    public ResultMessage approve(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.update(obj) ;
    }

    public ResultMessage deny(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.deleteByTerm(obj) ;
    }


    public ResultMessage buy(JSONObject obj) {
        ResultMessage resultMessage;

        obj.put("collectionName",collectionName);
        JSONObject queryParam = obj.getJSONObject("queryParam");
        JSONObject updateParam = obj.getJSONObject("updateParam");
        String buyer = updateParam.getString("buyer");

        queryParam.put("collectionName",collectionName);
        PageModel<JSONObject> pageModel = invigilateDao.findByTerm(queryParam);
        JSONObject data1 = pageModel.getData().get(0);
        String seller = data1.getString("seller");

        if(seller.equals(buyer)){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("不能购买自己发布的监考!");
            return  resultMessage;
        }

        resultMessage = invigilateDao.update(obj);

        return resultMessage;
    }
}
