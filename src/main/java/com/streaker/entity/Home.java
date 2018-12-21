package com.streaker.entity;

import java.util.Date;

/**
 * home entity
 *
 * @author StreakerHan
 * @version 2018/9/6 10:24:25
 **/
public class Home {
    private String hid;

    private String title;

    private String introduce;

    private String url;

    private Date hdate;

    private Integer uid;

    private String username;

    private String picurl;

    private String is_show;

    public String getIsShow() {
        return is_show;
    }

    public void setIsShow(String is_show) {
        this.is_show = is_show;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Date getHdate() {
        return hdate;
    }

    public void setHdate(Date hdate) {
        this.hdate = hdate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
}
