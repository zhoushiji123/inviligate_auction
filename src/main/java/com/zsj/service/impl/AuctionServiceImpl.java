package com.zsj.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.AuctionDao;
import com.zsj.dao.InvigilateDao;
import com.zsj.dao.ResultDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.AuctionService;
import com.zsj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zsj on 2017/4/18.
 */
@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private InvigilateDao invigilateDao;

    @Autowired
    private ResultDao resultDao;

    public ResultMessage takePartInAuction(JSONObject obj) {
        ResultMessage resultMessage ;
        String username = obj.getString("username");
        String invigilate_id = obj.getString("invigilate_id");

        int my_price = obj.getInteger("my_price");
        int current_price = obj.getInteger("current_price");

        JSONObject checkParam = new JSONObject();
        checkParam.put("collectionName",InvigilateDao.Invigilate);
        checkParam.put("_id",invigilate_id);
        JSONObject invigilate = invigilateDao.findByTerm(checkParam).getData().get(0);

        String seller = invigilate.getString("seller");
        if(seller.equals(username)){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("不能竞价你自己的监考！！");
            return  resultMessage;
        }

        String state = invigilate.getString("state");
        if(!state.equals("竞拍中")){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("该监考已经结束竞拍了！");
            return  resultMessage;
        }

        int ivg_price = invigilate.getInteger("current_price");
        if(current_price > ivg_price){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("竞价价格不能比监考当前价格大！！");
            return resultMessage;
        }

        JSONObject param  = new JSONObject();
        param.put("collectionName",AuctionDao.Auction);
        param.put("username",username);
        param.put("invigilate_id",invigilate_id);

        PageModel<JSONObject> pageModel = auctionDao.findByTerm(param);

        if(pageModel.getCount() == 0 ){
            //第一次参加竞拍
            //修改其他人的竞拍
            //生成一条竞拍记录
            JSONObject param2 = new JSONObject();
            JSONObject queryParamOthers = new JSONObject();
            JSONObject updateParamOthers = new JSONObject();
            param2.put("collectionName",AuctionDao.Auction);
            queryParamOthers.put("invigilate_id",invigilate_id);
            updateParamOthers.put("current_price",current_price);
            updateParamOthers.put("state","竞拍落后");
            param2.put("queryParam",queryParamOthers);
            param2.put("updateParam",updateParamOthers);

            resultMessage = auctionDao.update(param2);

            obj.put("collectionName",AuctionDao.Auction);
            resultMessage = auctionDao.insert(obj);

        }
        else{
            //不是第一次参加
            //修改其他人的竞拍
            //修改自己的竞拍
            JSONObject param2 = new JSONObject();
            JSONObject queryParamOthers = new JSONObject();
            JSONObject updateParamOthers = new JSONObject();
            param2.put("collectionName",AuctionDao.Auction);
            queryParamOthers.put("invigilate_id",invigilate_id);
            updateParamOthers.put("current_price",current_price);
            updateParamOthers.put("state","竞拍落后");
            param2.put("queryParam",queryParamOthers);
            param2.put("updateParam",updateParamOthers);

            resultMessage = auctionDao.update(param2);

            JSONObject param3 = new JSONObject();
            JSONObject queryParamMine = new JSONObject();
            JSONObject updateParamMine = new JSONObject();
            queryParamMine.put("username",username);
            queryParamMine.put("invigilate_id",invigilate_id);
            updateParamMine.put("my_price",my_price);
            updateParamMine.put("current_price",current_price);
            updateParamMine.put("state","竞拍领先");
            param3.put("collectionName",AuctionDao.Auction);
            param3.put("queryParam",queryParamMine);
            param3.put("updateParam",updateParamMine);

            resultMessage = auctionDao.update(param3);
        }

        //根据id修改监考当前竞拍价格 buyer也是竞拍领先者的名字
        JSONObject param2 = new JSONObject();
        JSONObject queryParamOthers = new JSONObject();
        JSONObject updateParamOthers = new JSONObject();
        param2.put("collectionName",InvigilateDao.Invigilate);
        queryParamOthers.put("_id",invigilate_id);
        updateParamOthers.put("current_price",current_price);
        updateParamOthers.put("buyer",username);
        param2.put("queryParam",queryParamOthers);
        param2.put("updateParam",updateParamOthers);
        resultMessage = invigilateDao.update(param2);

        return resultMessage;
    }


    public PageModel<JSONObject> getMyAuction(JSONObject obj) {

        obj.put("collectionName",AuctionDao.Auction);
        return auctionDao.findByTerm(obj);
    }

    public ResultMessage endAuction(JSONObject obj) {

        ResultMessage resultMessage;

        String invigilate_id = obj.getString("invigilate_id");

        //查询到当前监考 和领先者的名字 和状态(只能竞拍中)
        JSONObject param = new JSONObject();
        param.put("_id",invigilate_id);
        param.put("collectionName",InvigilateDao.Invigilate);
        PageModel<JSONObject> pageModel = invigilateDao.findByTerm(param);
        String username = pageModel.getData().get(0).getString("buyer");

        String state = pageModel.getData().get(0).getString("state");
        if(!state.equals("竞拍中")){
            resultMessage = new ResultMessage();
            resultMessage.setMessage("只能操作于竞拍中的监考");
            resultMessage.setSuccess(false);
            return  resultMessage;
        }

        //修改所有该监考的竞拍信息状态为     "竞拍失败"
        JSONObject param2 = new JSONObject();
        JSONObject queryParam2 = new JSONObject();
        JSONObject updateParam2 = new JSONObject();
        queryParam2.put("invigilate_id",invigilate_id);
        updateParam2.put("state","竞拍失败");
        param2.put("collectionName",AuctionDao.Auction);
        param2.put("queryParam",queryParam2);
        param2.put("updateParam",updateParam2);

        resultMessage =  auctionDao.update(param2);

        //修改最后竞拍领先者的竞拍信息状态为   "竞拍成功"

        JSONObject param3 = new JSONObject();
        JSONObject queryParam3 = new JSONObject();
        JSONObject updateParam3 = new JSONObject();
        queryParam3.put("username",username);
        queryParam3.put("invigilate_id",invigilate_id);
        updateParam3.put("state","竞拍成功");
        param3.put("collectionName",AuctionDao.Auction);
        param3.put("queryParam",queryParam3);
        param3.put("updateParam",updateParam3);

        resultMessage =  auctionDao.update(param3);

        //修改改监考的状态为  "未完成"
        JSONObject param4 = new JSONObject();
        JSONObject queryParam4 = new JSONObject();
        JSONObject updateParam4 = new JSONObject();
        queryParam4.put("_id",invigilate_id);
        updateParam4.put("state","未完成");
        param4.put("collectionName",InvigilateDao.Invigilate);
        param4.put("queryParam",queryParam4);
        param4.put("updateParam",updateParam4);

        resultMessage = invigilateDao.update(param4);

        //添加一条 拍卖结果信息
        String message = "用户--"+username+"--成功拍下了监考，编号为:"+invigilate_id;
        JSONObject param5 = new JSONObject();
        param5.put("date_time", DateUtil.getCurrentTime());
        param5.put("message",message);
        param5.put("collectionName",ResultDao.Results);
        resultMessage = resultDao.insert(param5);

        return resultMessage;
    }


    public ResultMessage delAuction(JSONObject obj) {
        obj.put("collectionName",AuctionDao.Auction);
        return auctionDao.deleteByTerm(obj);
    }
}
