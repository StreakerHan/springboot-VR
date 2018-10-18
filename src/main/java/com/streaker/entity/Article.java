package com.streaker.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章实体类
 *
 * @author StreakerHan
 * @version 2018/10/16 13:22:33
 **/
public class Article implements Serializable{

    private static final long serialVersionUID = 1715242434392707568L;

    private Integer aid;

    private String title;

    private String cdesc;

    private String username;

    private Integer uid;

    private String content;

    private String email;

    private Date cdate;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCdesc() {
        return cdesc;
    }

    public void setCdesc(String cdesc) {
        this.cdesc = cdesc;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
    }
}
