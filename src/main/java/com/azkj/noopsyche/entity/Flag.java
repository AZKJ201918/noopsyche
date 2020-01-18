package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.List;

@Data
public class Flag {

    private Integer id;

    private String flagname;

    private Integer status;

    private List<Commodity> commodityList;
}
