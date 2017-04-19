package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsj on 2017/4/18.
 */
@RestController
@RequestMapping(value = "zsj/auction")
public class AuctionApplication {

    @Autowired
    private AuctionService auctionService;


    @RequestMapping(value = "/takePartInAuction")
    public ResultMessage takePartInAuction(@RequestBody JSONObject obj){
        return auctionService.takePartInAuction(obj);
    }

    @RequestMapping(value = "/endAuction")
    public ResultMessage endAuction(@RequestBody JSONObject obj){
        return auctionService.endAuction(obj);
    }

    @RequestMapping(value = "/getMyAuction")
    public PageModel<JSONObject> getMyAuction(@RequestBody JSONObject obj){
        return auctionService.getMyAuction(obj);
    }

    @RequestMapping(value = "/delAuction")
    public ResultMessage delAuction(@RequestBody JSONObject obj){
        return auctionService.delAuction(obj);
    }

}
