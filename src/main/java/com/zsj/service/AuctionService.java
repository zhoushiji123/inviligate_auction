package com.zsj.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;

/**
 * Created by zsj on 2017/4/18.
 */
public interface AuctionService {

    /**
     * @param obj
     * {
     *    "username":""  //参加竞拍的用户名
     *    "invigilate_id":""  //竞拍的监考id
     *    "my_price":""   //我竞拍的价格
     *    "current_price": ""   //该监考的实时价格
     *    "state":""   //竞拍状态
     * }
     * @return
     */
    ResultMessage takePartInAuction(JSONObject obj);

    PageModel<JSONObject> getMyAuction(JSONObject obj);

    /**
     * @param obj
     * {
     *   "invigilate_id":""  //监考id
     *
     * }
     * @return
     */
    ResultMessage endAuction(JSONObject obj);


    ResultMessage delAuction(JSONObject obj);





}
