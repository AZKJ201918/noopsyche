package com.azkj.noopsyche.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Discuss {
    private String id;

    private Integer cid;

    private String details;

    private String token;

    private String wxname;

    private String wximg;

    private String detailsImg;

    private Integer evaluate;

    private String plusDetails;//追加评论

    private String orderId;

    private String skuname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    private Date createtime;
}
