package com.azkj.noopsyche.service.Impl;

import com.azkj.noopsyche.common.constants.Constants;
import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.common.pay.PayUtil;
import com.azkj.noopsyche.common.utils.*;
import com.azkj.noopsyche.common.utils.sm.sendSmsUtil;
import com.azkj.noopsyche.dao.*;
import com.azkj.noopsyche.entity.*;
import com.azkj.noopsyche.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
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
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private UserCouponMapper userCouponMapper;
    @Autowired
    private PayUtil payUtil;

    private static AtomicInteger registerCount = new AtomicInteger(0);

    private static AtomicInteger visitCount = new AtomicInteger(0);


    @Override
    public String login(WxUser wxUser) throws NoopsycheException {
        if (wxUser == null) {
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST, "没有用户信息");
        }
        String openid = wxUser.getOpenid();
        String headimgurl = wxUser.getHeadimgurl();
        String nickName = wxUser.getNickname();
        String uuid = wxUser.getUuid();
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
    public void addRegister(Register register, String smsCode) throws NoopsycheException {
       String code = (String) redisUtil.getObject("phone:" + register.getPhone());
        if (code==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"你还没有发送验证码,或者验证码已过期");
        }
        if (!code.equals(smsCode)){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"验证码输入不正确");
        }
        if (register==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"注册信息输入不完整");
        }
        registerMapper.insertSelective(register);
        Bank bank = register.getBank();
        if (bank!=null){
            bank.setToken(register.getToken());
            bank.setStatus(0);
            bankMapper.insertSelective(bank);
        }
    }

    @Override
    public void modifyPhone(Register register) throws NoopsycheException {
        if (register.getSmsCode()==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"请输入验证码");
        }
        String code = (String) redisUtil.getObject("phone:" + register.getPhone());
        if (code==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"请发送验证码");
        }
        if (!code.equals(register.getSmsCode())){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"验证码输入不正确");
        }
        registerMapper.updateByToken(register);
    }

    @Override
    public void addBank(Bank bank) {
        String bankid=bankMapper.selectByToken(bank.getToken());
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


    @Override
    public void sendSmsCode(String phone) throws Exception {
        int code= (int) (Math.random()*900000+100000);
        Object object = redisUtil.getObject("phone:" + phone);
        if (object!=null){
           throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"请勿重新发送验证码");
        }
        Integer num = (Integer) redisUtil.getObject("getSmsCodeTime:" + phone);
        if (num!=null&&num>3){
           throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"每天只能发送三次验证码");
        }
        sendSmsUtil.execute(phone,code+"");
        redisUtil.setObject("phone:"+phone,code+"",3L);
        if (num==null){
            num=1;
        }else {
            num++;
        }
        long now = new Date().getTime();
        long time = DateUtil.getTodayEndTime().getTime();
        redisUtil.setObjectSeconds("getSmsCodeTime:"+phone,num,time-now);
    }

    @Override
    public void exchangePea(Integer score, String uuid) throws NoopsycheException {
        ExchangePea exchangePea=wxUserMapper.selectPea();
        if (exchangePea==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有兑换信息");
        }
        Integer change=null;
        if (exchangePea.getStatus()==1){
            change=exchangePea.getActiveexchange();
        }else {
            change=exchangePea.getNormalexchange();
        }
        Integer pea  =score/change;
        wxUserMapper.updatePea(pea,uuid);
    }

    @Override
    public WxUser encode(String code, String encryptedData, String iv, String uuid) throws NoopsycheException {
        if (code == null) {
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST, "解码失败");
        }
        WxUser user = loginUtil.login(code, encryptedData, iv, uuid);
        return user;
    }

    @Override
    public List<Coupon> findAllNewCoupon() throws NoopsycheException {
        List<Coupon> couponList = wxUserMapper.selectAllNewCoupon();
        if (couponList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"暂无新人优惠");
        }
        return couponList;
    }

    @Override
    public void addNewCoupon(String token, List<Integer> couponids) throws ParseException {
        UserCoupon userCoupon = new UserCoupon();
        for (Integer couponid:couponids){
            Integer day=wxUserMapper.selectOutDayByCouponid(couponid);
            userCoupon.setToken(token);
            userCoupon.setCouponid(couponid);
            userCoupon.setRecievetime(new Date());
            if (day!=null){
                userCoupon.setOuttime(DateUtil.plusDay2(day));
            }
            userCouponMapper.insertSelective(userCoupon);
        }
    }

    @Override
    public List<Coupon> findAllCoupon() throws NoopsycheException {
        List<Coupon> couponList = wxUserMapper.selectAllCoupon();
        if (couponList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"未找到非新人优惠劵信息");
        }
        return wxUserMapper.selectAllCoupon();
    }

    @Override
    @Transactional
    public void addCoupon(List<UserCoupon> userCouponList) throws NoopsycheException {
        BigDecimal money=new BigDecimal("0");
        BigDecimal integral=new BigDecimal("0");
        Integer pea=0;
        String token=null;
        Coupon coupon=null;
        for (UserCoupon userCoupon:userCouponList){
            token=userCoupon.getToken();
            Integer couponid = userCoupon.getCouponid();
            Integer num = userCoupon.getNum();
            coupon = couponMapper.selectByPrimaryKey(couponid);
            if (coupon==null){
                throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"id为"+couponid+"的优惠劵不存在");
            }
            Integer status = coupon.getStatus();
            BigDecimal needIntegral= coupon.getIntegral();
            BigDecimal needMoney = coupon.getMoney();
            Integer needPea = coupon.getPea();
            if (status==1){
                money=money.add(needMoney);
            }
            if (status==2){
                integral=integral.add(needIntegral);
                userCouponMapper.insertSelective(userCoupon);
            }
            if (status==3){
                pea+=needPea;
                userCouponMapper.insertSelective(userCoupon);
            }
            if (status==4){
               integral=integral.add(needIntegral);
               money=money.add(needMoney);
            }
        }
        WxUser user=wxUserMapper.selectIntegralAndPea(token);
        BigDecimal myIntegral = user.getIntegral();
        Integer myPea = user.getPea();
        if (pea>myPea){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"臻豆不足无法兑换");
        }
        if (integral.compareTo(myIntegral)>0){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"积分不足无法兑换");
        }
        if (integral.compareTo(new BigDecimal("0"))>0){
            wxUserMapper.updateIntegral(integral,token);
        }
        if (pea!=0){
            wxUserMapper.updatePeaJian(pea,token);
        }

    }

    @Override
    public PageInfo<WxUser> findNext(String token, Integer page, Integer limit) throws NoopsycheException {
        PageHelper.startPage(page,limit);
        List<WxUser> wxUserList = wxUserMapper.selectNext(token);
        if (wxUserList==null){
            throw new NoopsycheException(Constants.RESP_STATUS_BADREQUEST,"没有下级下级信息");
        }
        PageInfo<WxUser> pageInfo = new PageInfo<>(wxUserList);
        return pageInfo;
    }

    @Override
    public WxUser SelectUserElement(String token) {
        return wxUserMapper.selectByPrimaryKey(token);
    }
}

