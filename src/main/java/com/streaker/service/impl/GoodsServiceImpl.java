package com.streaker.service.impl;

import com.streaker.dao.HomeDao;
import com.streaker.entity.Home;
import com.streaker.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品逻辑接口实现类
 *
 * @author StreakerHan
 * @version 2018/10/22 10:38:31
 **/
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private HomeDao homeDao;

    @Override
    public int addHome(Home home) {
        return homeDao.addHome(home);
    }

    @Override
    public int deleteHome(Integer hid) {
        return homeDao.deleteHome(hid);
    }

    @Override
    public List<Home> getHomeList() {
        List<Home> list= homeDao.getHomeList();
        return list;
    }

    @Override
    public List<Home> getHomeRecently() {
        List<Home> lists = homeDao.getHomeRecently();
        return lists;
    }
}
