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

    private Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    private UserDao userDao;

    public ResultMessage register(JSONObject obj) {

        return userDao.insert(obj);
    }



    public PageModel<JSONObject> login(JSONObject obj) {
        if(logger.isDebugEnabled()){
            logger.debug("用户登录--传入json参数为："+obj.toJSONString());
        }
        return userDao.findByTerm(obj);
    }
}
