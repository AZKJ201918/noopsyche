package com.azkj.noopsyche.common.utils;


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

    public void setkey(String token,Object object ){
        ops.set(token,object,15L, TimeUnit.MINUTES);
    }

    public void setkey(String key,String value){
        ops.set(key,value);
    }

    public Object getkey(String key){
        Object o = ops.get(key);
        return o;
    }
}
