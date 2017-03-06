package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.InvigilateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsj on 2017/3/4.
 */
@RestController
@RequestMapping(value = "zsj/invigilate")
public class InvigilateApplication {

    @Autowired
    private InvigilateService invigilateService;


    @RequestMapping(value = "/addInvigilate")
    public ResultMessage register(@RequestBody JSONObject obj){
        return invigilateService.addInvigilate(obj);
    }



    @RequestMapping(value = "/getInvigilates")
    public PageModel<JSONObject> getInvigilates(@RequestBody JSONObject obj) {
        return invigilateService.getInvigilates(obj);
    }


    @RequestMapping(value = "/getInvigilatesByDate")
    public PageModel<JSONObject> getInvigilatesByDate(@RequestBody JSONObject obj) {
        return invigilateService.getInvigilatesByDate(obj);
    }

    @RequestMapping(value = "/approve")
    public ResultMessage approve(@RequestBody JSONObject obj){
        return invigilateService.approve(obj);
    }


    @RequestMapping(value = "/deny")
    public ResultMessage deny(@RequestBody JSONObject obj){
        return invigilateService.deny(obj);
    }

    @RequestMapping(value = "/buy")
    public ResultMessage buy(@RequestBody JSONObject obj){
        return invigilateService.buy(obj);
    }
}
