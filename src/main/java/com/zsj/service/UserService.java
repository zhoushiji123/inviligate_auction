package com.zsj.service;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;

/**
 * Created by zsj on 2017/2/22.
 */
public interface UserService {

    ResultMessage  register(JSONObject obj);


    PageModel<JSONObject>  login(JSONObject obj);

    ResultMessage updatePassword(JSONObject obj);

}
