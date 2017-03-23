package com.zsj.interceptors;

import com.alibaba.fastjson.JSONObject;
import com.zsj.dao.AuthDao;
import com.zsj.dao.ObjDao;
import com.zsj.model.PageModel;
import com.zsj.model.ResultMessage;
import com.zsj.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by zsj on 2017/3/23.
 */
public class AuthInterceptor implements HandlerInterceptor {


    @Autowired
    private AuthDao authDao;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        System.out.println("------------调用之前拦截--------");
//        String token = request.getHeader("Authorization");
//        System.out.println(token);
//        Enumeration<String> headernames = request.getHeaderNames();
//        while (headernames.hasMoreElements()){
//            System.out.println(headernames.nextElement());
//        }

       /* BufferedReader bufferedReader = request.getReader();   //获得requestBody
        String str,bodyStr = "";
        while((str = bufferedReader.readLine())!= null){
            bodyStr += str;
        }*/

        /*PrintWriter printWriter = response.getWriter();
        printWriter.append(new JSONObject().toJSONString());*/  //responceBody写入json

//        ResultMessage resultMessage = new ResultMessage() ;
//        String token = request.getHeader("Authorization");
//        JSONObject object = new JSONObject();
//        object.put("collectionName","auth");
//        object.put("token",token);
//        PageModel<JSONObject> pageModel = authDao.findByTerm(object);
//        JSONObject res = pageModel.getData().get(0);
//        if(res == null ){
//            //没有根据token找到认证记录
//            resultMessage.setSuccess(false);
//            resultMessage.setMessage("用户认证不合法！！");
//            JSONObject resJson =(JSONObject) JSONObject.toJSON(resultMessage);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.append(resJson.toJSONString());
//            return  false;
//        }
//        String create_time  = res.getString("create_time");
//        int diffSeconds = DateUtil.secondDiff(DateUtil.stringToDate(create_time,DateUtil.YMD_DASH_WITH_TIME),new Date());
//        if(diffSeconds > 1800){
//            //token有效时间超过30分钟
//            JSONObject object1 = new JSONObject();
//            object1.put("collectionName","auth");
//            object1.put("token",token);
//            authDao.deleteByTerm(object1);
//
//            resultMessage.setSuccess(false);
//            resultMessage.setMessage("用户认证超时！！");
//            JSONObject resJson =(JSONObject) JSONObject.toJSON(resultMessage);
//            PrintWriter printWriter = response.getWriter();
//            printWriter.append(resJson.toJSONString());
//            return  false;
//        }

        return true;
    }

    //调用控制器后，渲染视图之前 执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //渲染视图之后执行
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
