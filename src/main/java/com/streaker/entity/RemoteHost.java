package com.streaker.entity;

import ch.ethz.ssh2.Connection;

import java.io.Serializable;

/**
 * 远程服务器
 *
 * @author StreakerHan
 * @version 2018/12/14 13:55:05
 **/
public class RemoteHost implements Serializable{
    private static final long serialVersionUID = -2855531869780532230L;

    public static String getDEFAULTCHART() {
        return DEFAULTCHART;
    }

    public static void setDEFAULTCHART(String DEFAULTCHART) {
        RemoteHost.DEFAULTCHART = DEFAULTCHART;
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        RemoteHost.conn = conn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    //字符编码默认是utf-8
    private static String  DEFAULTCHART="UTF-8";
    private static Connection conn;
    private String ip;
    private String userName;
    private String userPwd;

}
