package com.azkj.noopsyche.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class WxUser implements Serializable {

    private String token;

    private String openid;

    private String nickname;

    private String headimgurl;

    private String pea;

    private String uuid;

    private String unionid;

    private BigDecimal integral;

    private BigDecimal retailmoney;

    private Integer issign;

    private Integer isretail;

    private Date createtime;

    private boolean islogin; //是否首次登录

}