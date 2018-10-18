package com.streaker.shiro;

import com.streaker.dao.UserDao;
import com.streaker.dao.UserPermissionDao;
import com.streaker.dao.UserRoleDao;
import com.streaker.entity.Permission;
import com.streaker.entity.Role;
import com.streaker.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取用户的角色和权限
 *
 * @author StreakerHan
 * @version 2018/9/7 10:09:07
 **/
public class ShiroRealm extends AuthorizingRealm{

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserPermissionDao userPermissionDao;
    /**
     * 获取用户角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();

        System.out.println("用户" + userName + "获取权限-----ShiroRealm.doGetAuthorizationInfo");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = userRoleDao.findByUserName(userName);
        Set<String> roleSet = new HashSet<String>();
        for (Role r : roleList) {
            roleSet.add(r.getName());
        }
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Permission> permissionList = userPermissionDao.findByUserName(userName);
        Set<String> permissionSet = new HashSet<String>();
        for (Permission p : permissionList) {
            permissionSet.add(p.getName());
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        System.out.println("******"+token.toString() + "加密后的密码："+password);
        User user = userDao.getUserByUsername(userName);
        if (user == null) {
            System.out.println("****用户名不存在****");
            throw new UnknownAccountException("用户名不存在！");
        }
        if (!password.equals(user.getPassword())) {
            System.out.println("****用户名或密码错误！****");
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (user.getRole().equals("0")) {
            System.out.println("****账号已被锁定,请联系管理员！****");
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }
}
