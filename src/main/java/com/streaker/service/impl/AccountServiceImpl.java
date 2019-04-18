package com.streaker.service.impl;

import com.streaker.dao.AccountDao;
import com.streaker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网站访问次数接口实现类
 *
 * @author StreakerHan
 * @date 2019/4/18 23:10:00
 **/
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountDao accountDao;

    @Override
    public int addAccount() {
        return accountDao.addAccount();
    }

    @Override
    public int queryAccount() {
        return accountDao.queryAccount();
    }
}
