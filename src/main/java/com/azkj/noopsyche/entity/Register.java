package com.azkj.noopsyche.entity;

import lombok.Data;

@Data
public class Register {
    private Integer id;

    private String token;

    private String phone;

    private String name;

    private Bank bank;

    private String smsCode;//验证码
}