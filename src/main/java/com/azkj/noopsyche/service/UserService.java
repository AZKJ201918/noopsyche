package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.WxUser;

public interface UserService {
    String login(String code, String uuid, String encryptedData, String iv) throws NoopsycheException;
    String login1(String openid, String uuid, String headimgurl, String nickName) throws NoopsycheException;

    WxUser SelectUserElement(String token);
}
