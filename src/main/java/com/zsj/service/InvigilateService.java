package com.zsj.service;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;

/**
 * Created by zsj on 2017/3/4.
 */
public interface InvigilateService {

    /**
     *
     * @param obj
     * {
    "school": ""  //学校
    "address" : "" // 具体地址 楼名 ， 教室号
    "subject" : ""  //监考科目
    "date" : //监考日期
    "datetime":"" ,   //监考时间
    "price" : ""  // 价格
    "seller" : ""  //卖家
    "buyer" : "" // 买家
    "content" : ""   //备注内容
    "state" : "" //状态  :  未拍下,未完成,已完成

    "create_time":"" //数据生成时间 (后端添加)
    }
     * @return
     */
    ResultMessage addInvigilate(JSONObject obj);


    PageModel<JSONObject> getInvigilates(JSONObject obj);

    PageModel<JSONObject> getInvigilatesByDate(JSONObject obj);

    ResultMessage approve(JSONObject obj) ;

    ResultMessage deny(JSONObject obj) ;

    ResultMessage buy(JSONObject obj) ;

    ResultMessage update(JSONObject obj) ;

    ResultMessage delete(JSONObject obj) ;

    ResultMessage finish(JSONObject obj) ;

    ResultMessage giveup(JSONObject obj) ;
}
