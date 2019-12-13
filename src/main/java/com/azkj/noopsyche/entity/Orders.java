package com.azkj.noopsyche.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
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

    private List<Sku> skuList;

    private Integer couponid;
}