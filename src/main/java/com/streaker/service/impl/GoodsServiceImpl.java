package com.streaker.service.impl;

import com.streaker.dao.HomeDao;
import com.streaker.entity.Home;
import com.streaker.service.GoodsService;
import com.streaker.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    private RedisUtils redisUtils;

    @CachePut(value = "home",key = "#home.hid")
    @Override
    public int addHome(Home home) {
        return homeDao.addHome(home);
    }

    @Override
    public int deleteHome(String hid) {
        //删除redis缓存 (key)
        redisUtils.del(hid);
        return homeDao.deleteHomeById(hid);
    }

    @Override
    public int updateHome(Home home) {
        //在redis中添加缓存
        redisUtils.set(home.getHid(),home.getIsShow());
        return homeDao.updateHome(home);
    }

    @Override
    public List<Home> getHomeList() {
        List<Home> list= homeDao.getHomeList();
        return list;
    }

    @Override
    public int selectCount() {
        int count = homeDao.selectCount();
        return count;
    }

    /**
     * 添加首页展示缓存
     * @return
     */
    @Cacheable(cacheNames = "homeList")
    @Override
    public List<Home> getHomeRecently() {
        List<Home> lists = homeDao.getHomeRecently();
        return lists;
    }

    @Override
    public Home findHomeById(String hid) {
        return homeDao.findHomeById(hid);
    }
}
