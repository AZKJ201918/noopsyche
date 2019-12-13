package com.azkj.noopsyche.entity;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

@Data
public class Bank {
    private Integer id;

    private String bankid;

    private String token;

    private Integer status;

}