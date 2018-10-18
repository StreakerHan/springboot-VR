package com.streaker.dao;

import com.streaker.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * shiro 角色权限接口类
 *
 * @author StreakerHan
 * @version 2018/9/11 09:58:20
 **/
@Mapper
public interface UserPermissionDao {

    List<Permission> findByUserName(String userName);
}
