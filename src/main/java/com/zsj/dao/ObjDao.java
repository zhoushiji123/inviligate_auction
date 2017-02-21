package com.zsj.dao;

import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;

/**
 * Created by zsj on 2017/2/18.
 */
public interface ObjDao {

    /**
     * 分页查询
     * @param obj
     *  {
     *      "pageSize" : 1         //页面大小
     *       "pageIndex" : 1       //页号
     *       "collectionName" : "" //集合名称
     *  }
     * @return
     */
    PageModel<JSONObject> findByPage(JSONObject obj);

    PageModel<JSONObject> findAll(JSONObject obj);

    /**
     * 条件查询(支持单条件 多条件 )
     * 可分页
     * @param obj
     * @return
     */
    PageModel<JSONObject> findByTerm(JSONObject obj);

    ResultMessage insert(JSONObject obj);

    ResultMessage updateById(JSONObject obj);

    ResultMessage deleteById(JSONObject obj);


    /**
     * 将objectId 转 string
     * @param obj
     * @return
     */
    JSONObject setId(JSONObject obj);





}
