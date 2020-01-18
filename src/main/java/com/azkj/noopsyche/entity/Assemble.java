package com.azkj.noopsyche.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Assemble {
    private Integer id;

    private Integer size;

    private BigDecimal discount;

    private Integer spuid;

    private Date createtime;

    private Integer status;

}