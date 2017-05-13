package com.zsj.test;

import com.alibaba.fastjson.JSONObject;
import com.zsj.util.MessageUtil;

/**
 * Created by zsj on 2017/1/22.
 */
public class Test {

    public static void main(String[] args) {
        String phone = "13003608006";

        JSONObject param = new JSONObject();
        param.put("code","123567");
//        param.put("seller","zsj");
//        param.put("buyer","test1");

        MessageUtil.aliMsg(phone,param,MessageUtil.ali_template_identify);
    }

}
