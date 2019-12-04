package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.utils.LoginUtil;
import com.azkj.noopsyche.common.utils.RedisUtil;
import com.azkj.noopsyche.common.utils.UUIDUtils;
import com.azkj.noopsyche.dao.BankMapper;
import com.azkj.noopsyche.dao.RegisterMapper;
import com.azkj.noopsyche.dao.WxUserMapper;
import com.azkj.noopsyche.entity.Bank;
import com.azkj.noopsyche.entity.Register;
import com.azkj.noopsyche.entity.WxUser;
import com.azkj.noopsyche.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private WxUserMapper wxUserMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private BankMapper bankMapper;

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
        if (openid==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"用户信息错误");
        }
        WxUser findUser = wxUserMapper.selectUserByOpenId(openid);
        String userUUID = null;
        if (findUser != null) {
            userUUID = findUser.getUuid();
        }
        WxUser user = new WxUser();
        user.setHeadimgurl(headimgurl);
        user.setNickname(nickName);
        user.setOpenid(openid);
        user.setCreatetime(new Date());
        if (userUUID != null) {//用户信息已经初始化
            System.out.println("openId已经存在,不需要插入");
            user.setToken(userUUID);
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id
                    if (!userUUID.equals(uuid)) {//自己不是自己的分享人
                        user.setUuid(uuid);
                    }
                }
            }
            wxUserMapper.updateByPrimaryKeySelective(user);
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
            redisUtil.setObject("uuid:"+userUUID,wxUser,15L);
            return userUUID;
        } else {//用户没有初始化
            String wxUserId = UUIDUtils.generateMost22UUID();//用户id
            user.setToken(wxUserId);
            if (uuid != null) {//有分享人
                user.setUuid(uuid);
            }
            wxUserMapper.insertSelective(user);
            //Integer count1 =  wxUserMapper.insertWxUser(newUser);
            registerCount.incrementAndGet();
            visitCount.incrementAndGet();
            redisUtil.setObject("uuid:"+wxUserId,user,15L);
            return wxUserId;
        }
    }

    public String login1(String openid, String uuid, String headimgurl, String nickName) throws NoopsycheException {
        WxUser findUser = wxUserMapper.selectUserByOpenId(openid);
        String userUUID = null;
        if (findUser!=null){
            userUUID=findUser.getToken();
        }
        WxUser user = new WxUser();
        user.setHeadimgurl(headimgurl);
        user.setNickname(nickName);
        user.setOpenid(openid);
        user.setCreatetime(new Date());
        if (userUUID != null) {//用户信息已经初始化
            System.out.println("openId已经存在,不需要插入");
            user.setToken(userUUID);
            if (uuid != null) {//有分享人
                String mySuperiorid = wxUserMapper.selectSuperioridByOpenid(openid);
                if (mySuperiorid == null) {//没有父id
                    if (!userUUID.equals(uuid)) {//自己不是自己的分享人
                        user.setUuid(uuid);
                    }
                }
            }
            wxUserMapper.updateByPrimaryKeySelective(user);
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
            redisUtil.setObject(userUUID,user,15L);
            return userUUID;
        } else {//用户没有初始化
            String wxUserId = UUIDUtils.generateMost22UUID();//用户id
            user.setToken(wxUserId);
            if (uuid != null) {//有分享人
                user.setUuid(uuid);
            }
            wxUserMapper.insertSelective(user);
            //Integer count1 =  wxUserMapper.insertWxUser(newUser);
            registerCount.incrementAndGet();
            visitCount.incrementAndGet();
            redisUtil.setObject(wxUserId,user,15L);
            return wxUserId;
        }
    }

    @Override
    public void findRegister(String token) throws NoopsycheException {
        Register register = registerMapper.selectRegisterByToken(token);
        if (register==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"用户未注册");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addRegister(Register register, Bank bank) {
        registerMapper.insertSelective(register);
        if (bank!=null){
            bank.setToken(register.getToken());
            bank.setStatus(0);
            bankMapper.insertSelective(bank);
        }
    }

    @Override
    public void modifyPhone(Register register) {
        registerMapper.updateByToken(register);
    }

    @Override
    public void addBank(Bank bank) {
        Integer bankid=bankMapper.selectByToken(bank.getToken());
        if (bankid==null){//没有默认银行卡
            bank.setStatus(0);
        }else {
            bank.setStatus(1);
        }
        bankMapper.insertSelective(bank);
    }

    @Override
    public List<Bank> findBank(String token) throws NoopsycheException {
        List<Bank> bankList = bankMapper.selectBank(token);
        if (bankList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有查到银行卡相关信息");
        }
        return bankList;
    }

    @Override
    public void removeBank(Bank bank) {
        bankMapper.deleteByBankId(bank);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyBank(Bank bank) {
        bankMapper.updateStatusToFeiMonren(bank.getToken());
        bankMapper.updateByBankId(bank);
    }
}

