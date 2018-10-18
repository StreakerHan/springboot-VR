package com.streaker.dao;

import com.streaker.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户接口的定义
 *
 * @author StreakerHan
 * @version 2018/9/6 11:23:40
 **/
@Mapper
public interface UserDao {
    /**
     * 用户信息注册接口
     */
    int addUser(User user);

    /**
     * 通过主键id删除用户信息接口
     */
    int deleteUser(@Param("uid") Integer uid);

    /**
     * 修改用户信息接口
     */
    int updateUser(User user);

    /**
     * 将用户设置为管理员接口
      */
    int updateUserAdmin(User user);

    /**
     * 将用户设置为黑名单接口
     */
    int updateUserBlack(User user);

    /**
     * 将黑名单设置为普通用户接口
     */
    int updateBlackUser(User user);

    /**
     * 获取全部用户信息接口
     */
    List<User> getUserList();

    /**
     * 根据用户名查询用户接口
     */
    User getUserByUsername(@Param("username") String username);

    /**
     * 根据用户id查询用户接口
     */
    User getUserById(@Param("uid") Integer uid);

    /**
     * 查询用户总量接口
     */
    Long getUserCount();
}
