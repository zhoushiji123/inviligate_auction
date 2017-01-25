package com.zsj.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Created by zsj on 2017/1/25.
 */
public class HttpUtil {

    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpget(String url){
        String res = "";
        try{
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }


    /**
     * post发送json参数
     * @param url
     * @param param
     * @return
     */
    public static String postJson(String url, JSONObject param){
        String res = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type","application/json");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(param)));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        return  res;
    }

    /**
     * post发送表单
     * @param url
     * @param param
     * @return
     */
    public static String postForm(String url,JSONObject param){
        String res = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            HttpPost httpPost = new HttpPost(url);
         //   httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
            httpPost.setEntity(new StringEntity(JSONObject.toJSONString(param)));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }
        return  res;
    }

}
