package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.UserDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsj on 2017/2/22.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public ResultMessage register(JSONObject obj) {
        ResultMessage resultMessage ;
        String username = obj.getString("username");
        JSONObject param = new JSONObject();
        param.put("collectionName","users");
        param.put("username",username);
        PageModel<JSONObject> pageModel = userDao.findByTerm(param);
        if(pageModel.getCount() > 0){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("用户名已经存在");
            return resultMessage;
        }

        JSONObject param2 = new JSONObject();
        param2.put("telephone",obj.getString("telephone"));
        param2.put("collectionName","users");
        pageModel = userDao.findByTerm(param2);
        if(pageModel.getCount() > 0){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("该手机号已经注册过了");
            return resultMessage;
        }


        resultMessage = userDao.insert(obj);
        return resultMessage;
    }



    public PageModel<JSONObject> login(JSONObject obj) {
        PageModel<JSONObject> pageModel = userDao.findByTerm(obj);
        if(pageModel.getCount() == 0)
            pageModel.setMessage("用户名或者密码错误");
        return pageModel;
    }
}
