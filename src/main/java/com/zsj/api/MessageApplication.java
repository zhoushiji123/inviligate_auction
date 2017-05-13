package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.ResultMessage;
import com.zsj.util.MessageUtil;
import com.zsj.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsj on 2017/5/12.
 */
@RestController
@RequestMapping(value = "zsj/message")
public class MessageApplication {


    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/getIdentifyCode")
    public ResultMessage getIdentifyCode(@RequestBody JSONObject object){
        String phone = object.getString("telephone");
        int code = (int)((Math.random()*9+1)*100000);

        JSONObject param  = new JSONObject();
        param.put("code",String.valueOf(code));
        MessageUtil.aliMsg(phone,param,MessageUtil.ali_template_identify);

        String strCode = String.valueOf(code);
        redisUtil.set(phone,strCode,300); //电话号码 验证码存入缓存 有效时间300s
        return  new ResultMessage();
    }


    @RequestMapping(value = "/testRedis")
    public ResultMessage testRedis(@RequestBody JSONObject object){
        String phone = object.getString("telephone");
        int code = (int)((Math.random()*9+1)*100000);
        String strCode = String.valueOf(code);
//        String content = new String("【监考拍卖系统】---您的注册验证码是:"+code);
//        MessageUtil.send(phone,content);

        System.out.println(strCode);
        redisUtil.set(phone,strCode,120);

        System.out.println(redisUtil.get(phone).toString());
        return  new ResultMessage();
    }
}
