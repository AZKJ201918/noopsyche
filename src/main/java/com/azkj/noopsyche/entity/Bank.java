package com.azkj.noopsyche.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

@Data
public class Bank {
    @Field("id")
    private Integer id;
    @Field("bankid")
    private String bankid;
    @Field("token")
    private String token;
    @Field("status")
    private Integer status;

}