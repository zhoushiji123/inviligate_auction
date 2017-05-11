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

//        JSONObject param  = new JSONObject();
//        param.put("factory_model_name","carparkmanageV1_0");
//        String res = HttpUtil.postJson("http://121.199.5.95:10010//tdp/carparkmanage/vehicleSourceAnalysis.htm",param);
//        if(StringUtils.isEmpty(res)){
//            System.out.println("res is null");
//        }else{
//            JSONObject object = JSONObject.parseObject(res.trim());
//            System.out.println(object);
//        }

        Date date1 =DateUtil.stringToDate("2017-05-11 17:00:00",DateUtil.YMD_DASH_WITH_TIME);
        Date date2 =DateUtil.stringToDate("2017-05-11 19:30:00",DateUtil.YMD_DASH_WITH_TIME);

        int hour = DateUtil.hourDiff(date1,date2);
        if(Math.abs(hour) <= 3)
            System.out.println("日期发生冲突了，相差"+hour+"小时");

    }
}
