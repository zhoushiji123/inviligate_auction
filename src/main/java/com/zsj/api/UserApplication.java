package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.ResultMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsj on 2017/1/22.
 */
@RestController
@RequestMapping(value = "/zsj/user")
public class UserApplication {

    @RequestMapping(value = "/test")
    public ResultMessage test(@RequestBody JSONObject object){
        ResultMessage resultMessage = new ResultMessage();
        System.out.println("传入json: " + object.toJSONString());
        System.out.println("test");
        return resultMessage;
    }
}
