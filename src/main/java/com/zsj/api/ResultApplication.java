package com.zsj.api;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zsj on 2017/4/19.
 */
@RestController
@RequestMapping(value = "zsj/result")
public class ResultApplication {

    @Autowired
    private ResultService resultService;

    @RequestMapping(value = "/getResult")
    PageModel<JSONObject> getResult(@RequestBody JSONObject object){
        return resultService.getResult(object);
    }


    @RequestMapping(value = "/delResult")
    ResultMessage delResult(@RequestBody JSONObject object){
        return resultService.delResult(object);
    }
}
