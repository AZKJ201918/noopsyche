package com.azkj.noopsyche.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.lucene.document.FieldType;
import org.jboss.logging.Field;


import java.util.Date;
import java.util.List;

@Data
public class Commodity {


    private Integer id;

    private String title;


    private String subtitle;

    private String price;

    private String detailurl;

    private Integer sales;

    private String url;

    private Integer status;

    private Integer type;

    private Integer flag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    private Date createtime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GTM+8")
    private Date updatetime;

    List<Property> propertyList;
}