package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.List;

@Data
public class People {
    private Integer id;

    private Integer gid;

    private String token;

    private Integer status;

    private Integer ptype;

    private Groups groups;

    private Commodity commodity;

    private WxUser wxUser;

    private List<People> myPeopleList;//同一个拼团下的所有人
}