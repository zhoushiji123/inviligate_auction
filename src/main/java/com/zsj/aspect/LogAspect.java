package com.zsj.aspect;


import com.alibaba.fastjson.JSONObject;
import com.zsj.model.PageModel;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by zsj on 2017/2/26.
 */
@Component
@Aspect
public class LogAspect {


    private Logger logger = Logger.getLogger(this.getClass());


    @Pointcut("execution(* com.zsj.service.*.*(..) )")
    public void point(){

    }

    @Around("point()")
    public Object log(ProceedingJoinPoint proceedingJoinPoint){
        try {
            String method = proceedingJoinPoint.getSignature().getName();
            JSONObject param = (JSONObject)(proceedingJoinPoint.getArgs()[0]);
            logger.debug("接口"+method+"--传入参数："+param.toJSONString());

            Object res = (proceedingJoinPoint.proceed());
            logger.debug("接口"+method+"--返回结果："+res);

            return  res;
        }catch (Throwable t){
            t.printStackTrace();
        }
        return null;
    }




}
