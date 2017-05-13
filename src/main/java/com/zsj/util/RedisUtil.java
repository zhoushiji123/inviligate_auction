package com.zsj.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by zsj on 2017/5/12.
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    public   void set(String key , Object value,long time){
        stringRedisTemplate.opsForValue().set(key,(String)value,time);
    }

    public   void set(String key , Object value){
        stringRedisTemplate.opsForValue().set(key,(String)value);
    }

    public  Object get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public  void delete(String key){
        stringRedisTemplate.delete(key);
    }

    public  boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }
}
