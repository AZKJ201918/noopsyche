package com.azkj.noopsyche.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Coupon {
    private Integer id;

    private String type;

    private BigDecimal subtract;

    private BigDecimal discount;

    private BigDecimal fullsubtract;

    private BigDecimal fulls;

    private BigDecimal fulldiscount;

    private BigDecimal fulld;

    private Date outtime;

    private Integer use;

    private Integer status;

    private Integer day;

    private BigDecimal money;

    private BigDecimal integral;

    private Integer pea;

}