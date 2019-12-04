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



    public void setWxUser(WxUser wxUser){
        ops.set(wxUser,wxUser,15L, TimeUnit.MINUTES);
    }


    public WxUser getWxUser(WxUser wxUser){
        WxUser o = (WxUser) ops.get(wxUser);
        return o;
    }
}
