package com.azkj.noopsyche.common.utils;


import com.azkj.noopsyche.entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
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

    private HashOperations hos=null;

    @PostConstruct
    public void  init1() {
        ops = redisTemplate.opsForValue();
    }

    @PostConstruct
    public void  begin() {
        hos = redisTemplate.opsForHash();
    }

    public void setObject(String token,Object object,Long time){
        ops.set(token,object,time,TimeUnit.MINUTES);
    }

    public void setObjectLong(String token,Object object){
        ops.set(token,object);
    }

    public void setHashObjectLong(String token,String id,Object object){
        hos.put(token,id,object);
    }

    public Object getHashObjectLong(String token,String id){
        Object o = hos.get(token, id);
        return o;
    }
    public void deleteHashObjectLong(String token,String id){
        hos.delete(token, id);
    }
    public Object getHashEntriesLong(String token){
        Object o = hos.entries(token);
        return o;
    }
    public void setObjectSeconds(String token,Object object,Long time){
        ops.set(token,object,time,TimeUnit.MILLISECONDS);
    }

    public Object getObject(String token){
        Object o = ops.get(token);
        return o;
    }
    public void setkey(String key,String value){
        ops.set(key,value);
    }

    public Object getkey(String key){
        Object o = ops.get(key);
        return o;
    }
}
