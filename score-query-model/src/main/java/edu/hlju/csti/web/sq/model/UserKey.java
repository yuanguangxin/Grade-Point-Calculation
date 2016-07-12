package edu.hlju.csti.web.sq.model;

import java.util.Date;

public class UserKey {
    private Long id;

    private String schoolNum;

    private String publicKey;

    private String privateKey;

    private String loginCookie;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolNum() {
        return schoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        this.schoolNum = schoolNum;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getLoginCookie() {
        return loginCookie;
    }

    public void setLoginCookie(String loginCookie) {
        this.loginCookie = loginCookie;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}