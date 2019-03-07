package com.streaker.utils;

import org.springframework.stereotype.Component;

/**
 * 定义静态常量
 *
 * @author StreakerHan
 * @version 2018/10/8 10:11:42
 **/
@Component
public class Constant {

    /**
     * session的key
     */
    public static String LOGIN_SESSION_KEY = "login_user";

    /**
     * 日志：登录后来管理界面
     */
    public static String LOGIN_MANAGE = "登录后台管理界面";

    /**
     * 修改个人信息
     */
    public static String UPDATE_PERSON_INFO = "修改个人信息";

    /**
     * 修改人员角色
     */
    public static String UPDATE_USER_ROLE = "修改人员角色";

    /**
     * 删除用户
     */
    public static String DELETE_USER = "删除用户";

    /**
     * 添加文章
     */
    public static String ADD_ARTICLE = "添加文章";

    /**
     * 添加文章
     */
    public static String DELETE_ARTICLE = "删除文章";

    /**
     * 添加商品
     */
    public static String ADD_GOOD = "添加商品";

    /**
     * 删除商品
     */
    public static String DELETE_GOOD = "删除商品";

    /**
     * 商品下架
     */
    public static String DOWN_GOOD = "商品下架";

    /**
     * 删除商品
     */
    public static String UP_GOOD = "商品上架";
}
