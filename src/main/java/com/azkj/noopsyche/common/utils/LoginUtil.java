package com.azkj.noopsyche.common.utils;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.dao.WxUserMapper;
import com.azkj.noopsyche.entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.azkj.noopsyche.common.utils.EncodeUtil.getUserInfo;

@Component
public class LoginUtil {
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    private static AtomicInteger registerCount = new AtomicInteger(0);

    private static AtomicInteger visitCount = new AtomicInteger(0);

    public WxUser login(String code, String encryptedData, String iv, String uuid) throws NoopsycheException {
        Map<String, Object> map = new HashMap<>();
        map.put("appid", Constants.appId);
        map.put("secret", Constants.appSecret);
        map.put("js_code", code);
        map.put("grant_type", Constants.grant_type);
        WxUser user = new WxUser();
        //调用微信接口获取openId用户唯一标识
        String wxReturnValue = UrlUtils.sendPost(Constants.url, map);
        Map<String, Object> tempMap = null;
        String userUUID = null;
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
            if (openid==null){
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"用户信息错误");
            }
            WxUser findUser = wxUserMapper.selectUserByOpenId(openid);
            if (findUser != null) {
                userUUID = findUser.getToken();
            }
            user.setOpenid(openid);
            user.setCreatetime(new Date());
            if (userUUID != null) {//用户信息已经初始化
                System.out.println("openId已经存在,不需要插入");
                user.setToken(userUUID);
                user.setIslogin(false);
                if (uuid != null) {//有分享人
                    String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                    if (mySuperiorid == null) {//没有父id
                        if (!userUUID.equals(uuid)) {//自己不是自己的分享人
                            user.setUuid(uuid);
                            wxUserMapper.updateByPrimaryKeySelective(user);
                        }
                    }
                }
                Date date = new Date();
                String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
                Date createtime = findUser.getCreatetime();
                String format1 = "";
                if (createtime != null) {
                    format1 = new SimpleDateFormat("yyyy-MM-dd").format(createtime);
                }
                if (format != null && !format.equals(format1)) {
                    visitCount.incrementAndGet();
                }
                redisUtil.setObject("uuid:"+userUUID,user,15L);
            } else {//用户没有初始化
                userUUID = UUIDUtils.generateMost22UUID();//用户id
                user.setToken(userUUID);
                if (uuid != null) {//有分享人
                    user.setUuid(uuid);
                }
                String session_key = "";
                session_key = tempMap.get("session_key").toString();
                Map<String, String> userMap = getUserInfo(encryptedData, session_key, iv);
                String nickName = userMap.get("nickName");
                String headimgurl = userMap.get("avatarUrl");
                user.setOpenid(openid);
                user.setHeadimgurl(headimgurl);
                user.setNickname(nickName);
                user.setIslogin(true);
                wxUserMapper.insertSelective(user);
                //Integer count1 =  wxUserMapper.insertWxUser(newUser);
                registerCount.incrementAndGet();
                visitCount.incrementAndGet();
                redisUtil.setObject("uuid:"+userUUID,user,15L);
            }
        }
        return user;
    }
}
