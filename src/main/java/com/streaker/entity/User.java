package com.streaker.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * user entity
 *
 * @author StreakerHan
 * @version 2018/9/6 10:20:28
 **/
public class User implements Serializable{

    private static final long serialVersionUID = -8366929034564774130L;

    private Integer uid;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String role;

    private Date udate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getUdate() {
        return udate;
    }

    public void setUdate(Date udate) {
        this.udate = udate;
    }
}
