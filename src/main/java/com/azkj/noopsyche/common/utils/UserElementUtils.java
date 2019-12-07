package com.azkj.noopsyche.common.utils;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserElementUtils {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public Boolean UserElement(String token) throws NoopsycheException {

        if(token==null){
            throw  new NoopsycheException(401,"请登录");
        }
        WxUser wxUser= (WxUser) redisUtil.getkey(token);
        if(wxUser==null){
            throw new NoopsycheException(401,"请登录");
        }
        if(userService.SelectUserElement(token)==null){
            throw  new NoopsycheException(401,"请登录");
        }

        return true;
    }
}
