package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by zsj on 2017/1/22.
 */
@RestController
@RequestMapping(value = "/zsj/user")
public class UserApplication {




    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public ResultMessage test(@RequestBody JSONObject object){
        ResultMessage resultMessage = new ResultMessage();
        System.out.println("传入json: " + object.toJSONString());
        System.out.println("test");
        return resultMessage;
    }



    @RequestMapping(value = "/register")
    public ResultMessage register(@RequestBody JSONObject obj){
        return userService.register(obj);
    }



    @RequestMapping(value = "/login")
    public PageModel<JSONObject> login(@RequestBody JSONObject obj){
        PageModel<JSONObject> pageModel = userService.login(obj);
        return pageModel;
    }



    @RequestMapping(value = "/updatePassword")
    public ResultMessage updatePassword(@RequestBody JSONObject obj){
        return userService.updatePassword(obj);
    }



}
