package com.azkj.noopsyche.entity;

import java.util.Date;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLicenseurl() {
        return licenseurl;
    }

    public void setLicenseurl(String licenseurl) {
        this.licenseurl = licenseurl == null ? null : licenseurl.trim();
    }

    public String getIdentityjusturl() {
        return identityjusturl;
    }

    public void setIdentityjusturl(String identityjusturl) {
        this.identityjusturl = identityjusturl == null ? null : identityjusturl.trim();
    }

    public String getIdentitycontraryurl() {
        return identitycontraryurl;
    }

    public void setIdentitycontraryurl(String identitycontraryurl) {
        this.identitycontraryurl = identitycontraryurl == null ? null : identitycontraryurl.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox == null ? null : mailbox.trim();
    }

    public String getPermiturl() {
        return permiturl;
    }

    public void setPermiturl(String permiturl) {
        this.permiturl = permiturl == null ? null : permiturl.trim();
    }

    public String getBankidurl() {
        return bankidurl;
    }

    public void setBankidurl(String bankidurl) {
        this.bankidurl = bankidurl == null ? null : bankidurl.trim();
    }

    public String getCobaurl() {
        return cobaurl;
    }

    public void setCobaurl(String cobaurl) {
        this.cobaurl = cobaurl == null ? null : cobaurl.trim();
    }

    public String getInteriorurl() {
        return interiorurl;
    }

    public void setInteriorurl(String interiorurl) {
        this.interiorurl = interiorurl == null ? null : interiorurl.trim();
    }

    public String getReceptionurl() {
        return receptionurl;
    }

    public void setReceptionurl(String receptionurl) {
        this.receptionurl = receptionurl == null ? null : receptionurl.trim();
    }

    public String getCorporationidentityjusturl() {
        return corporationidentityjusturl;
    }

    public void setCorporationidentityjusturl(String corporationidentityjusturl) {
        this.corporationidentityjusturl = corporationidentityjusturl == null ? null : corporationidentityjusturl.trim();
    }

    public String getCorporationidentitycontraryurl() {
        return corporationidentitycontraryurl;
    }

    public void setCorporationidentitycontraryurl(String corporationidentitycontraryurl) {
        this.corporationidentitycontraryurl = corporationidentitycontraryurl == null ? null : corporationidentitycontraryurl.trim();
    }

    public String getAccountidentityjusturl() {
        return accountidentityjusturl;
    }

    public void setAccountidentityjusturl(String accountidentityjusturl) {
        this.accountidentityjusturl = accountidentityjusturl == null ? null : accountidentityjusturl.trim();
    }

    public String getAccountidentitycontraryurl() {
        return accountidentitycontraryurl;
    }

    public void setAccountidentitycontraryurl(String accountidentitycontraryurl) {
        this.accountidentitycontraryurl = accountidentitycontraryurl == null ? null : accountidentitycontraryurl.trim();
    }

    public String getRelationurl() {
        return relationurl;
    }

    public void setRelationurl(String relationurl) {
        this.relationurl = relationurl == null ? null : relationurl.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Integer accounttype) {
        this.accounttype = accounttype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }
}