package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.*;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    String login(WxUser wxUser) throws NoopsycheException;
    String login1(String openid, String uuid, String headimgurl, String nickName) throws NoopsycheException;

    void findRegister(String token) throws NoopsycheException;

    void addRegister(Register register, String smsCode) throws NoopsycheException;

    void modifyPhone(Register register) throws NoopsycheException;

    void addBank(Bank bank);

    List<Bank> findBank(String token) throws NoopsycheException;

    void removeBank(Bank bank);

    void modifyBank(Bank bank);

    WxUser SelectUserElement(String token);

    void sendSmsCode(String phone) throws Exception;

    void exchangePea(Integer score, String uuid) throws NoopsycheException;

    WxUser encode(String code, String encryptedData, String iv, String uuid) throws NoopsycheException;

    List<Coupon> findAllNewCoupon() throws NoopsycheException;

    void addNewCoupon(String token, List<Integer> couponids) throws ParseException, NoopsycheException;

    List<Coupon> findAllCoupon() throws NoopsycheException;

    void addCoupon(List<UserCoupon> userCouponList) throws Exception;

    PageInfo<WxUser> findNext(String token, Integer page, Integer limit) throws NoopsycheException;

    void addAnnocyCoupon(List<UserCoupon> userCouponList) throws NoopsycheException, ParseException;
}
