package com.streaker.service;

/**
 * 网站访问次数逻辑接口类
 *
 * @author StreakerHan
 * @version 2019/4/18 23:08:17
 **/
public interface AccountService {

    /**
     * 访问次数增加
     */
    int addAccount();

    /**
     * 查询访问次数
     */
    int queryAccount();
}
