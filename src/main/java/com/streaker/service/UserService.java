package com.streaker.service;

import com.streaker.entity.User;

import java.util.List;

/**
 * 用户接口的定义
 *
 * @author StreakerHan
 * @version 2018/9/7 10:36:24
 **/
public interface UserService {

    /**
     * 新增用户信息
     */
    int addUser(User user);

    /**
     * 根据用户名查询用户
     */
    User getUserByUsername(String username);

    /**
     * 更新用户信息
     */
    int updateUser(User user);

    /**
     * 将用户设置为管理员
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
     * 根据用户id查询用户
     */
    User getUserById(Integer uid);

    /**
     * 获取用户列表
     */
    List<User> getUserList();

    /**
     * 根据用户id删除用户
     */
    void deleteUser(Integer uid);

}
