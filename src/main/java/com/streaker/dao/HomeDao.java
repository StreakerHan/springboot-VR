package com.streaker.dao;

import com.streaker.entity.Home;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 房子接口的定义
 *
 * @author StreakerHan
 * @version 2018/9/6 11:25:01
 **/
@Mapper
public interface HomeDao {

    /**
     * 添加房子信息接口
     */
    int addHome(Home home);

    /**
     * 通过主键id删除房子接口
     */
    int deleteHomeById(@Param("hid") String hid);

    /**
     * 更新房子信息接口
     */
    int updateHome(Home home);

    /**
     * 查看房子列表接口
     */
    List<Home> getHomeList();

    /**
     *查询房子总量接口
     */
    int selectCount();

    /**
     * 查询最近添加的房子信息接口（只需要查询id和title）
     */
    List<Home> getHomeRecently();

    /**
     * 根据title或desc搜索房子信息接口
     */
    List<Home> searchHome(@Param("param") String param);

    /**
     * 根据id查询房子
     */
    Home findHomeById(@Param("hid") String hid);
}
