package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.Date;
@Data
public class UserCoupon {
    private Integer id;

    private String token;

    private Integer couponid;

    private Date recievetime;

    private Date outtime;

    private Coupon coupon;

    private Integer num;//购买的数量

}