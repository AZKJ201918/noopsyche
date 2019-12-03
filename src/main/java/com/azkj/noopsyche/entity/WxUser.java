package com.azkj.noopsyche.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WxUser {
    private String token;

    private String openid;

    private String nickname;

    private String headimgurl;

    private String phone;

    private String uuid;

    private String unionid;

    private BigDecimal integral;

    private BigDecimal retailmoney;

    private Integer issign;

    private Integer isretail;

    private Date createtime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl == null ? null : headimgurl.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid == null ? null : unionid.trim();
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public BigDecimal getRetailmoney() {
        return retailmoney;
    }

    public void setRetailmoney(BigDecimal retailmoney) {
        this.retailmoney = retailmoney;
    }

    public Integer getIssign() {
        return issign;
    }

    public void setIssign(Integer issign) {
        this.issign = issign;
    }

    public Integer getIsretail() {
        return isretail;
    }

    public void setIsretail(Integer isretail) {
        this.isretail = isretail;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}