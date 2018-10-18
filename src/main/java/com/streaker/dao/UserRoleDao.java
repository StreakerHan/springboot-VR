package com.streaker.dao;

import com.streaker.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * shiro 用户角色接口
 *
 * @author StreakerHan
 * @version 2018/9/11 09:56:25
 **/
@Mapper
public interface UserRoleDao {

    List<Role> findByUserName(String userName);
}
