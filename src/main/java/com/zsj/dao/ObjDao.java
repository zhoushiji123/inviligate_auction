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
     *      "pageIndex" : 1       //页号
     *      "collectionName" : "" //集合名称
     *  }
     * @return
     */
    PageModel<JSONObject> findByPage(JSONObject obj);

    /**
     * 条件查询(支持单条件,多条件,支持分页 )
     * @param obj
     * {
     *      "pageSize" : 1,         //页面大小
     *      "pageIndex" : 1,       //页号
     *      "collectionName" : "", //集合名称
     *      "":"", //查询条件(可多个)
     * }
     * @return
     */
    PageModel<JSONObject> findByTerm(JSONObject obj);

    /**
     * 插入一条数据
     * @param obj
     * {
     *     "collectionName":"", //集合名
     *     "":"" //插入参数
     * }
     * @return
     */
    ResultMessage insert(JSONObject obj);

    /**
     * 修改一条记录
     * @param obj
     * {
     *     "collectionName" :"" ,//集合名
     *     "queryParam":{}, //查询条件
     *     "updateParam":{} //修改内容
     * }
     * @return
     */
    ResultMessage update(JSONObject obj);


    ResultMessage deleteByTerm(JSONObject obj);


    /**
     * 将objectId 转 string
     * @param obj
     * @return
     */
    JSONObject setId(JSONObject obj);


    PageModel<JSONObject> findAll(JSONObject obj);


}
