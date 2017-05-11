package com.zsj.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.AuctionDao;
import com.zsj.dao.InvigilateDao;
import com.zsj.dao.ResultDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.service.InvigilateService;
import com.zsj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zsj on 2017/3/4.
 */
@Service
public class InvigilateServiceImpl implements InvigilateService{

    private String collectionName =  "invigilates";

    @Autowired
    private InvigilateDao invigilateDao;

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private ResultDao resultDao;


    public ResultMessage addInvigilate(JSONObject obj) {
        ResultMessage resultMessage ;
        obj.put("collectionName",collectionName);
        resultMessage = invigilateDao.insert(obj);
        return resultMessage;
    }


    public PageModel<JSONObject> getInvigilates(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.findByTerm(obj);
    }


    public PageModel<JSONObject> getInvigilatesByDate(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.findByDate(obj);
    }



    public ResultMessage approve(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.update(obj) ;
    }

    public ResultMessage deny(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.deleteByTerm(obj) ;
    }


    public ResultMessage buy(JSONObject obj) {
        ResultMessage resultMessage;
        String invigilate_id = obj.getString("_id");
        String username = obj.getString("buyer"); //参与的用户名

        //判断不能购买自己的监考和监考状态
        JSONObject param1 = new JSONObject();
        param1.put("collectionName",InvigilateDao.Invigilate);
        param1.put("_id",invigilate_id);
        PageModel<JSONObject> pageModel = invigilateDao.findByTerm(param1);
        JSONObject invigilate = pageModel.getData().get(0); //所购买或者竞拍的监考
        String seller = invigilate.getString("seller");

        int price = invigilate.getInteger("price");

        if(seller.equals(username)){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("不能购买自己发布的监考!");
            return  resultMessage;
        }

        String state = invigilate.getString("state");
        if(!state.equals("竞拍中")){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("该监考已经结束竞拍了！");
            return  resultMessage;
        }

        //判断日期是否冲突
        if(!this.isDateConflict(username,invigilate)){
            resultMessage = new ResultMessage();
            resultMessage.setSuccess(false);
            resultMessage.setMessage("发生日期冲突，在这个时间段的前后3小时，您已经有监考竞拍的参与了！");
            return  resultMessage;
        }

        //其他所有人的竞拍信息 修改为竞拍失败 当前价格为 一口价
        JSONObject param2 = new JSONObject();
        JSONObject queryParam2 = new JSONObject();
        JSONObject updateParam2 = new JSONObject();
        queryParam2.put("invigilate_id",invigilate_id);
        updateParam2.put("state","竞拍失败");
        updateParam2.put("current_price",price);
        param2.put("collectionName",AuctionDao.Auction);
        param2.put("queryParam",queryParam2);
        param2.put("updateParam",updateParam2);
        resultMessage = auctionDao.update(param2);

        //自己的竞拍 修改为 竞拍成功 我的价格和当前价格都是一口价
        JSONObject param3 = new JSONObject();
        JSONObject queryParam3 = new JSONObject();
        JSONObject updateParam3 = new JSONObject();
        queryParam3.put("username",username);
        queryParam3.put("invigilate_id",invigilate_id);
        updateParam3.put("state","竞拍成功");
        updateParam3.put("my_price",price);
        updateParam3.put("current_price",price);
        param3.put("collectionName",AuctionDao.Auction);
        param3.put("queryParam",queryParam3);
        param3.put("updateParam",updateParam3);
        resultMessage =  auctionDao.update(param3);


        //修改监考信息(buyer,current_price,state)
        JSONObject param4 = new JSONObject();
        JSONObject queryParam4 = new JSONObject();
        JSONObject updateParam4 = new JSONObject();
        param4.put("collectionName",InvigilateDao.Invigilate);
        queryParam4.put("_id",invigilate_id);
        updateParam4.put("buyer",username);
        updateParam4.put("state","未完成");
        updateParam4.put("current_price",price);
        param4.put("queryParam",queryParam4);
        param4.put("updateParam",updateParam4);
        resultMessage = invigilateDao.update(param4);

        //添加一条 拍卖结果信息
        String message = "用户--"+username+"--成功拍下了监考，编号为:"+invigilate_id;
        JSONObject param5 = new JSONObject();
        param5.put("date_time", DateUtil.getCurrentTime());
        param5.put("message",message);
        param5.put("collectionName", ResultDao.Results);
        resultMessage = resultDao.insert(param5);

        return resultMessage;
    }

    public ResultMessage update(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.update(obj) ;
    }

    public ResultMessage delete(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.deleteByTerm(obj) ;
    }

    public ResultMessage finish(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.update(obj) ;
    }

    public ResultMessage giveup(JSONObject obj) {
        obj.put("collectionName",collectionName);
        return invigilateDao.update(obj) ;
    }


    /**
     * 判断 竞拍者/买家 所参与竞拍/购买的监考是否有时间上的冲突
     * 遍历所有该竞拍者所参与的竞拍/已购买的监考
     * 要竞拍/购买的监考，跟其他已经竞拍/购买的监考比较时间，
     * 如果时间相差大于3或者小于-3 小时，且监考id不同，则视为发生冲突
     * @param username 用户名
     * @param invigilate 监考
     * @return
     */
    public  boolean isDateConflict(String username,JSONObject invigilate){

        String id1 = invigilate.getString("_id");
        Date date1  =invigilate.getDate("datetime");

        JSONObject param1 = new JSONObject();
        param1.put("collectionName",InvigilateDao.Invigilate);
        param1.put("buyer",username);

        PageModel<JSONObject> pageModel = invigilateDao.findByTerm(param1);
        List<JSONObject> myIvgs = pageModel.getData();
        if(pageModel.getCount() == 0)
            return  true ;    //第一次购买或者竞拍

        for (JSONObject myivg : myIvgs){
            String id2 = myivg.getString("_id");
            Date date2 = myivg.getDate("datetime");
            String state = myivg.getString("state");
            int diffhour = Math.abs(DateUtil.daysDiff(date1,date2));
            if(!id1.equals(id2) && diffhour<=3 && !state.equals("已完成"))
                return false;   //监考id不相等，小时差小于3小时，发生日期冲突
        }

        return true;
    }
}
