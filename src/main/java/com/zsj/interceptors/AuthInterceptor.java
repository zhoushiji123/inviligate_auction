package com.zsj.interceptors;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by zsj on 2017/3/23.
 */
public class AuthInterceptor implements HandlerInterceptor {


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
        return true;
    }

    //调用控制器后，渲染视图之前 执行
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //渲染视图之后执行
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
