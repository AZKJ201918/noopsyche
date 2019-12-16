package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Property {
    private Integer id;

    private Integer spuid;

    private String propertyname;

    private Date createtime;

    private Date updatetime;

    List<Product> productList;

}