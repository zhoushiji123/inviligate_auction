package com.zsj.service;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;

/**
 * Created by zsj on 2017/4/19.
 */
public interface ResultService {

    PageModel<JSONObject> getResult(JSONObject obj);

    ResultMessage delResult(JSONObject obj);
}
