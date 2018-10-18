package com.streaker.entity;

import java.io.Serializable;

/**
 * shiro 角色实体类
 *
 * @author StreakerHan
 * @version 2018/9/11 09:44:13
 **/
public class Role implements Serializable{

    private static final long serialVersionUID = -227437593919820521L;

    private Integer id;
    private String name;
    private String memo;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMemo() {
        return memo;
    }
}
