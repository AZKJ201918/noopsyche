package com.azkj.noopsyche.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Orders {
    private Integer id;

    private String orderid;

    private String token;

    private BigDecimal price;

    private BigDecimal finalprice;

    private Integer status;

    private Integer addressid;

    private String remark;

    private Date createtime;

    private Date outtime;

    private Date sendtime;

    private Date recievetime;

    private Date recieveouttime;

    private String company;

    private String courier;

    private Date paytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(BigDecimal finalprice) {
        this.finalprice = finalprice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Date getRecievetime() {
        return recievetime;
    }

    public void setRecievetime(Date recievetime) {
        this.recievetime = recievetime;
    }

    public Date getRecieveouttime() {
        return recieveouttime;
    }

    public void setRecieveouttime(Date recieveouttime) {
        this.recieveouttime = recieveouttime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier == null ? null : courier.trim();
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
        this.paytime = paytime;
    }
}