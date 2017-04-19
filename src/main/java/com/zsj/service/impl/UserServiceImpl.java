package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.AuthDao;
import com.zsj.dao.UserDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.UserService;
import com.zsj.util.MD5;
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

    @Autowired
    private AuthDao authDao;

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
        param2.put("telphone",obj.getString("telephone"));
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
        if(pageModel.getCount() == 0){
            pageModel.setMessage("用户名或者密码错误");
            pageModel.setSuccess(false);
        }else{
            //登录成功 获取token并插入一条认证记录
//            JSONObject user = pageModel.getData().get(0);
//            String username = user.getString("username");
//            String token = MD5.getMD5(username);
//            JSONObject param = new JSONObject();
//            param.put("collectionName",AuthDao.auth);
//            param.put("username",username);
//            param.put("token",token);
//            authDao.insert(param);
        }
        return pageModel;
    }


    public ResultMessage updatePassword(JSONObject obj) {
        JSONObject param = new JSONObject();
        JSONObject query = new JSONObject();
        JSONObject update = new JSONObject();

        query.put("username",obj.getString("username"));
        update.put("password",obj.getString("newPassword"));

        param.put("collectionName","users");
        param.put("queryParam",query);
        param.put("updateParam",update);

        ResultMessage resultMessage = userDao.update(param);

        return resultMessage;
    }
}
