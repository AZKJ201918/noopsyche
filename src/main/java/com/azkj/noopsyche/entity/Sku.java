package com.azkj.noopsyche.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Sku {
    private Integer id;

    private Integer spuid;

    private String skuname;

    private BigDecimal skuprice;

    private String imgurl;

    private Integer enable;

    private Date createtime;

    private Date updatetime;

    private Integer repertory;

    private Integer carNum;

    private Integer num;//商品购买数量
}