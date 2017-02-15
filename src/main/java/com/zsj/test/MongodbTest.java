package com.zsj.test;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;
import com.zsj.service.MongoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

/**
 * Created by zsj on 2017/2/15.
 */
public class MongodbTest {

    public static void main(String[] args) {
//        try {
//            Mongo mongo =  new Mongo("127.0.0.1",27017);
//            List<String> dblist = mongo.getDatabaseNames();
//            DB db = mongo.getDB("test1");
//            DBCollection collection = db.getCollection("users");
//            DBCursor datas = collection.find();
//            System.out.println("文档个数 ： "+ datas.numSeen());
//            while (datas.hasNext()){
//                DBObject object = datas.next();
//                JSONObject jsonObject = JSON.parseObject(object.toString());
//                System.out.println(object);
//                System.out.println(jsonObject);
//            }
//
//        }catch (UnknownHostException e){
//            e.printStackTrace();
//        }

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MongoService mongoService = (MongoService)applicationContext.getBean("mongoServiceImpl");
        List<JSONObject> list = mongoService.findAll();
        for(JSONObject jsonObject : list)
            System.out.println(jsonObject);


    }
}