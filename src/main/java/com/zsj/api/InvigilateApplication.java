package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
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



}
