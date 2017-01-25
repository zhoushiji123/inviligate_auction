package com.zsj.test;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.DADD;
import com.zsj.util.DateUtil;
import com.zsj.util.HttpUtil;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zsj on 2017/1/22.
 */
public class Test {

    public static void main(String[] args) {

        JSONObject param  = new JSONObject();
        param.put("factory_model_name","carparkmanageV1_0");
        String res = HttpUtil.postJson("http://121.199.5.95:10010//tdp/carparkmanage/vehicleSourceAnalysis.htm",param);
        if(StringUtils.isEmpty(res)){
            System.out.println("res is null");
        }else{
            JSONObject object = JSONObject.parseObject(res.trim());
            System.out.println(object);
        }


    }
}
