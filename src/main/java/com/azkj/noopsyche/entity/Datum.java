package com.azkj.noopsyche.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.Date;

@Data
public class Datum {

    private Integer id;

    private String licenseurl;

    private String identityjusturl;

    private String identitycontraryurl;

    private String phone;

    private String mailbox;

    private String permiturl;

    private String bankidurl;

    private String cobaurl;

    private String interiorurl;

    private String receptionurl;

    private String corporationidentityjusturl;

    private String corporationidentitycontraryurl;

    private String accountidentityjusturl;

    private String accountidentitycontraryurl;

    private String relationurl;

    private Integer type;

    private Integer accounttype;

    private Integer status;

    private String token;

    private Date createtime;

    private String explain;

    private String mininame;
}