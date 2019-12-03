package com.azkj.noopsyche.util;


import com.azkj.noopsyche.entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil{
    @Autowired
    private RedisTemplate redisTemplate;
    ValueOperations ops = redisTemplate.opsForValue();
    public void setWxUser(WxUser wxUser){
        ops.set(wxUser,wxUser,15L, TimeUnit.MINUTES);
    }
    public WxUser getWxUser(WxUser wxUser){
        WxUser o = (WxUser) ops.get(wxUser);
        return o;
    }
}
