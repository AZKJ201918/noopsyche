package com.azkj.noopsyche.common.utils;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.entity.WxUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.azkj.noopsyche.common.utils.EncodeUtil.getUserInfo;


public class LoginUtil {
    public static WxUser login(String code,String encryptedData,String iv){
        Map<String, Object> map = new HashMap<>();
        map.put("appid", Constants.appId);
        map.put("secret", Constants.appSecret);
        map.put("js_code", code);
        map.put("grant_type", Constants.grant_type);
        WxUser wxUser = new WxUser();
        //调用微信接口获取openId用户唯一标识
        String wxReturnValue = UrlUtils.sendPost(Constants.url, map);
        Map<String, Object> tempMap = null;
        try {
            tempMap = JsonUtils.convertJson2Object(wxReturnValue, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempMap.containsKey("errcode")) {
            String errcode = tempMap.get("errcode").toString();
            System.out.println("微信返回的错误码" + errcode);
        } else if (tempMap.containsKey("session_key")) {
            System.out.println("调用微信成功");
            String openid = tempMap.get("openid").toString();
            System.out.println(openid);
            String session_key = "";
            session_key = tempMap.get("session_key").toString();
            Map<String, String> userMap = getUserInfo(encryptedData, session_key, iv);
            String nickName = userMap.get("nickName");
            String headimgurl = userMap.get("avatarUrl");
            wxUser.setOpenid(openid);
            wxUser.setHeadimgurl(headimgurl);
            wxUser.setNickname(nickName);
        }
        return wxUser;
    }
}
