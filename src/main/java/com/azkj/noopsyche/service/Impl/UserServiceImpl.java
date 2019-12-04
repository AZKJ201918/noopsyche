package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.LoginUtil;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.common.utils.UUIDUtils;
import com.azkj.noopsyche.dao.WxUserMapper;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    private static AtomicInteger registerCount = new AtomicInteger(0);

    private static AtomicInteger visitCount = new AtomicInteger(0);

    @Override
    public String login(String code, String uuid, String encryptedData, String iv) throws NoopsycheException {
        if (code == null) {
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST, "没有code");
        }
        WxUser wxUser = LoginUtil.login(code, encryptedData, iv);
        if (wxUser == null) {
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST, "没有用户信息");
        }
        String openid = wxUser.getOpenid();
        String headimgurl = wxUser.getHeadimgurl();
        String nickName = wxUser.getNickname();
        WxUser findUser = wxUserMapper.selectUserByOpenId(openid);
        String userUUID = null;
        if (findUser != null) {
            userUUID = findUser.getUuid();
        }
        if (userUUID != null) {//用户信息已经初始化
            System.out.println("openId已经存在,不需要插入");
            WxUser user1 = new WxUser();
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id
                    if (!userUUID.equals(uuid)) {//自己不是自己的分享人
                        user1.setUuid(userUUID);
                        user1.setHeadimgurl(headimgurl);
                        user1.setNickname(nickName);
                        user1.setUuid(uuid);
                        user1.setCreatetime(new Date());
                        wxUserMapper.updateByPrimaryKeySelective(user1);
                    } else {//自己是自己的分享人
                        user1.setUuid(userUUID);
                        user1.setHeadimgurl(headimgurl);
                        user1.setNickname(nickName);
                        user1.setCreatetime(new Date());
                        wxUserMapper.updateByPrimaryKeySelective(user1);
                    }
                } else {//有父id
                    user1 = new WxUser();
                    user1.setToken(userUUID);
                    user1.setHeadimgurl(headimgurl);
                    user1.setNickname(nickName);
                    user1.setCreatetime(new Date());
                    wxUserMapper.updateByPrimaryKeySelective(user1);
                }
            } else {//没有分享人
                user1 = new WxUser();
                user1.setUuid(userUUID);
                user1.setHeadimgurl(headimgurl);
                user1.setNickname(nickName);
                user1.setCreatetime(new Date());
                wxUserMapper.updateByPrimaryKeySelective(user1);
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
            redisUtil.setkey(userUUID,findUser);
            return userUUID;
        } else {//用户没有初始化
            String wxUserId = UUIDUtils.generateMost22UUID();//用户id
            System.out.println("openId不存在,插入数据库");
            WxUser user = new WxUser();
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id,首次进入不可能自己是自己的分享人
                    user.setToken(wxUserId);
                    user.setHeadimgurl(headimgurl);
                    user.setNickname(nickName);
                    user.setOpenid(openid);
                    user.setUuid(uuid);
                    user.setCreatetime(new Date());
                    wxUserMapper.insertSelective(user);
                } else {//有父id
                    user = new WxUser();
                    user.setToken(wxUserId);
                    user.setHeadimgurl(headimgurl);
                    user.setNickname(nickName);
                    user.setOpenid(openid);
                    user.setCreatetime(new Date());
                    wxUserMapper.insertSelective(user);
                }
            } else {//没有分享人
                user = new WxUser();
                user.setToken(wxUserId);
                user.setHeadimgurl(headimgurl);
                user.setNickname(nickName);
                user.setOpenid(openid);
                user.setCreatetime(new Date());
                wxUserMapper.insertSelective(user);
            }
            //Integer count1 =  wxUserMapper.insertWxUser(newUser);
            registerCount.incrementAndGet();
            visitCount.incrementAndGet();
            redisUtil.setkey(wxUserId,findUser);
            return wxUserId;
        }
    }

    public String login1(String openid, String uuid, String headimgurl, String nickName) throws NoopsycheException {
        WxUser findUser = wxUserMapper.selectUserByOpenId(openid);
        String userUUID = null;
        if (findUser != null) {
            userUUID = findUser.getToken();
        }
        if (userUUID != null) {//用户信息已经初始化
            System.out.println("openId已经存在,不需要插入");
            WxUser user1 = new WxUser();
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id
                    if (!userUUID.equals(uuid)) {//自己不是自己的分享人
                        user1.setToken(userUUID);
                        user1.setHeadimgurl(headimgurl);
                        user1.setNickname(nickName);
                        user1.setUuid(uuid);
                        user1.setCreatetime(new Date());
                        wxUserMapper.updateByPrimaryKeySelective(user1);
                    } else {//自己是自己的分享人
                        user1.setToken(userUUID);
                        user1.setHeadimgurl(headimgurl);
                        user1.setNickname(nickName);
                        user1.setCreatetime(new Date());
                        wxUserMapper.updateByPrimaryKeySelective(user1);
                    }
                } else {//有父id//已测试
                    user1 = new WxUser();
                    user1.setToken(userUUID);
                    user1.setHeadimgurl(headimgurl);
                    user1.setNickname(nickName);
                    user1.setCreatetime(new Date());
                    wxUserMapper.updateByPrimaryKeySelective(user1);
                }
            } else {//没有分享人//已测试
                user1 = new WxUser();
                user1.setToken(userUUID);
                user1.setHeadimgurl(headimgurl);
                user1.setNickname(nickName);
                user1.setCreatetime(new Date());
                wxUserMapper.updateByPrimaryKeySelective(user1);
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
            redisUtil.setkey(userUUID,findUser);
            return userUUID;
        } else {//用户没有初始化
            String wxUserId = UUIDUtils.generateMost22UUID();//用户id
            System.out.println("openId不存在,插入数据库");
            WxUser user = new WxUser();
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id,首次进入不可能自己是自己的分享人//已测试
                    user.setToken(wxUserId);
                    user.setHeadimgurl(headimgurl);
                    user.setNickname(nickName);
                    user.setOpenid(openid);
                    user.setUuid(uuid);
                    user.setCreatetime(new Date());
                    wxUserMapper.insertSelective(user);
                } else {//有父id
                    user = new WxUser();
                    user.setToken(wxUserId);
                    user.setHeadimgurl(headimgurl);
                    user.setNickname(nickName);
                    user.setOpenid(openid);
                    user.setCreatetime(new Date());
                    wxUserMapper.insertSelective(user);
                }
            } else {//没有分享人//已测试
                user = new WxUser();
                user.setToken(wxUserId);
                user.setHeadimgurl(headimgurl);
                user.setNickname(nickName);
                user.setOpenid(openid);
                user.setCreatetime(new Date());
                wxUserMapper.insertSelective(user);
            }
            //Integer count1 =  wxUserMapper.insertWxUser(newUser);
            registerCount.incrementAndGet();
            visitCount.incrementAndGet();
            redisUtil.setkey(wxUserId,findUser);
            return wxUserId;
        }
    }

    @Override
    public WxUser SelectUserElement(String token) {
        return wxUserMapper.selectByPrimaryKey(token);
    }
}

