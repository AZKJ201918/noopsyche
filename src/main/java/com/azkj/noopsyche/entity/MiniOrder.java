package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.Date;
@Data
public class MiniOrder {
    private Integer id;

    private String token;

    private Integer mid;

    private Date createtime;

    private Long price;

    private Date endtime;

    private Integer status;

    private Long preferential;

    private String orderid;


}