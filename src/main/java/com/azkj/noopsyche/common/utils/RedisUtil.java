package com.azkj.noopsyche.common.utils;


import com.azkj.noopsyche.entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil{
    @Autowired
    private RedisTemplate redisTemplate;


    private ValueOperations ops = null;

    @PostConstruct
    public void  init() {
        ops = redisTemplate.opsForValue();
    }



    public void setObject(String token,Object object,Long time){
        ops.set(token,object,time,TimeUnit.MINUTES);
    }

    public void setObjectSeconds(String token,Object object,Long time){
        ops.set(token,object,time,TimeUnit.MILLISECONDS);
    }

    public Object getObject(String token){
        Object o = ops.get(token);
        return o;
    }
}
