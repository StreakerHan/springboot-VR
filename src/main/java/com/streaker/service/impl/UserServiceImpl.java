package com.streaker.service.impl;

import com.streaker.dao.UserDao;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 用户接口的实现类
 *
 * @author StreakerHan
 * @version 2018/9/7 10:41:19
 **/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    /**
     * 根据用户名查询用户方法的实现
     * @param username
     * @return
     */
    @Override
    public User getUserByUsername(String username) {
        User user = userDao.getUserByUsername(username);
        return user;
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        if(null == user.getUid()){
            ResponseBo.error("用户不能为空！");
        }
        return userDao.updateUser(user);
    }

    /**
     * 将用户设置为管理员
     * @param user
     * @return
     */
    @Override
    public int updateUserAdmin(User user) {
        if (null == user.getUid()){
            ResponseBo.error("用户不能为空！");
        }
        return userDao.updateUserAdmin(user);
    }

    /**
     * 将用户设置为黑名单
     * @param user
     * @return
     */
    @Override
    public int updateUserBlack(User user) {
        if(null == user.getUid()){
            ResponseBo.error("用户不能为空！");
        }
        return userDao.updateUserBlack(user);
    }

    /**
     * 将黑名单设置为普通用户
     * @param user
     * @return
     */
    @Override
    public int updateBlackUser(User user) {
        if (null == user.getUid()){
            ResponseBo.error("用户不能为空！");
        }
        return userDao.updateBlackUser(user);
    }

    /**
     * 根据id查询用户信息
     * @param uid
     * @return
     */
    @Override
    public User getUserById(Integer uid) {
        User user = userDao.getUserById(uid);
        return user;
    }

    /**
     *获取用户列表
     */
    @Override
    public List<User> getUserList() {
        List<User> list = userDao.getUserList();
        return list;
    }

    @Override
    @Transactional
    public void deleteUser(Integer uid) {
        if(null == uid) {
            ResponseBo.error("删除失败！");
        }
        userDao.deleteUser(uid);
    }

}
