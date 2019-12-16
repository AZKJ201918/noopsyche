package com.azkj.noopsyche.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private Integer id;

    private Integer propertyid;

    private String spuname;

    private BigDecimal price;

    private Integer status;

    private Date createtime;

    private Date updatetime;


}