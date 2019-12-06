package com.azkj.noopsyche.service;

import com.azkj.noopsyche.common.exception.NoopsycheException;
import com.azkj.noopsyche.entity.Bank;
import com.azkj.noopsyche.entity.Register;

import java.util.List;
import com.azkj.noopsyche.entity.WxUser;

public interface UserService {
    String login(String code, String uuid, String encryptedData, String iv) throws NoopsycheException;
    String login1(String openid, String uuid, String headimgurl, String nickName) throws NoopsycheException;

    void findRegister(String token) throws NoopsycheException;

    void addRegister(Register register, Bank bank,String smsCode) throws NoopsycheException;

    void modifyPhone(Register register);

    void addBank(Bank bank);

    List<Bank> findBank(String token) throws NoopsycheException;

    void removeBank(Bank bank);

    void modifyBank(Bank bank);

    WxUser SelectUserElement(String token);

    void sendSmsCode(String phone) throws Exception;

    void exchangePea(Integer score, String uuid) throws NoopsycheException;
}
