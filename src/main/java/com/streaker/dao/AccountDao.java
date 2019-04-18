package com.streaker.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 网站访问次数接口类
 *
 * @author StreakerHan
 * @version 2019/4/18 22:53:50
 **/
@Mapper
public interface AccountDao {

    /**
     * 访问次数增加
     */
    int addAccount();

    /**
     * 查询访问次数
     */
    int queryAccount();
}
