package com.azkj.noopsyche.entity;

public class Enter {
    private Integer id;

    private String token;

    private String identityurl;

    private String businessurl;

    private String iphone;

    private String name;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getIdentityurl() {
        return identityurl;
    }

    public void setIdentityurl(String identityurl) {
        this.identityurl = identityurl == null ? null : identityurl.trim();
    }

    public String getBusinessurl() {
        return businessurl;
    }

    public void setBusinessurl(String businessurl) {
        this.businessurl = businessurl == null ? null : businessurl.trim();
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone == null ? null : iphone.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}