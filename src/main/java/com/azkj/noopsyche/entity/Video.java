package com.azkj.noopsyche.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Video {


    private Integer id;

    private String name;

    private String username;

    private Date createtime;

    private Date endtime;

    private String status;

    private String introduce;

    private Integer mark;


}