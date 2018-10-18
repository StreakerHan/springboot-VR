package com.streaker.controller;

import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.LogService;
import com.streaker.service.UserService;
import com.streaker.utils.Constant;
import com.streaker.utils.MD5Utils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;


/**
 * 用户控制器
 *
 * @author StreakerHan
 * @version 2018/9/7 11:08:18
 **/
@Controller
public class UserController {


    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    /**
     * 登录界面展示
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 个人资料界面展示
     */
    /*@RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        System.out.println("界面名称："+page);
        return page;
    }*/

    /**
     * 登录认证控制
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseBo login(HttpServletRequest request, String username, String password){
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        if(request.getParameter("rememberMe")!=null){
            token.setRememberMe(true);
        }
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            System.out.println("******************" + token.toString());
            //根据Subject中的用户名获取用户id
            User user2 = (User) SecurityUtils.getSubject().getPrincipal();
            String un = user2.getUsername();
            User user1 = userService.getUserByUsername(un);
            Integer id = user1.getUid();
            String role = user1.getRole();
            //向数据库中插入日志
            logService.addLog(new Date(), id, un, request.getRemoteAddr(),role);
            request.getSession().setAttribute(Constant.LOGIN_SESSION_KEY,token);
            return ResponseBo.ok();
        }catch (UnknownAccountException e){
            return  ResponseBo.error(e.getMessage());
        }catch (IncorrectCredentialsException e){
            return ResponseBo.error(e.getMessage());
        }catch (LockedAccountException e){
            return ResponseBo.error(e.getMessage());
        }catch (AuthenticationException e){
            return ResponseBo.error("认证失败！");
        }
    }

    /**
     * 重定向到首页
     */
    @RequestMapping("/")
    public String redirectIndex(){
        return "redirect:/index";
    }

    /**
     * 将用户信息显示在界面上
     */
    @RequestMapping("/{page}")
    public String index(@PathVariable String page,Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("loginUser", user);
        return page;
    }

    /**
     * 获取请求绑定的登陆对象
     * @param request
     * @return
     */
    /*public User user(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(null == session){
            return null;
        }
        return (User) session.getAttribute(Constant.LOGIN_SESSION_KEY);
    }*/
    /**
     * 更新用户信息
     */
    @PostMapping(value = "/admin/profile")
    @ResponseBody
    public ResponseBo updateUser( @RequestParam(value = "username",required=false) String username, @RequestParam(value = "email",required=false) String email,
                                 @RequestParam(value = "phone",required=false) String phone, HttpServletRequest request, HttpSession session){
        //根据subject中的用户名获取用户的id
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        String un = user2.getUsername();
        User user1 = userService.getUserByUsername(un);
        Integer id = user1.getUid();

        User user = new User();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(email)
                && StringUtils.isNotBlank(phone)) {


            user.setUid(id);
            /*System.out.println(id);*/
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            userService.updateUser(user);

            //更新shiro中subject中的信息。如果不更新，界面显示的信息是更新之前的。
            user2.setUsername(user.getUsername());
            user2.setEmail(user.getEmail());
            user2.setPhone(user.getPhone());
        }
        return ResponseBo.ok();
    }

    /**
     * 将用户设置为管理员
     */
    @PostMapping("/admin/setAdmin")
    @ResponseBody
    public  ResponseBo updateUserAdmin(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateUserAdmin(user);
        System.out.println("****将用户id为"+uid+"的用户设置为管理员！****");
        return ResponseBo.ok();
    }

    /**
     * 将用户设置为黑名单
     */
    @PostMapping("/admin/setBlack")
    @ResponseBody
    public  ResponseBo updateUserBlack(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateUserBlack(user);
        System.out.println("****将用户id为"+uid+"的用户设置为黑名单！****");
        return ResponseBo.ok();
    }

    /**
     * 将黑名单设置为普通用户
     */
    @PostMapping("/admin/setUser")
    @ResponseBody
    public  ResponseBo updateBlackUser(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateBlackUser(user);
        System.out.println("****将黑名单id为"+uid+"的用户设置为普通用户！****");
        return ResponseBo.ok();
    }

    /**
     * 获取用户列表
     * @param
     * @return
     */
    @GetMapping("/user-manage")
    public String getUserList(HttpServletRequest request){
        List<User> users = userService.getUserList();
        request.setAttribute("users",users);
        //输出所有用户的用户名
        /*for (int i = 0 ; i < users.size(); i ++ ){
            System.out.println(users.get(i).getUsername());
        }*/
        return "user-manage";
    }

    /**
     * 根据用户id删除用户信息
     * @param uid
     * @return
     */
    @PostMapping("/user/delete")
    @ResponseBody
    public ResponseBo delUser(@RequestParam(name = "uid") Integer uid){
        userService.deleteUser(uid);
        System.out.println("******删除用户"+ uid);
        return  ResponseBo.ok();
    }

    @RequiresPermissions("user:user")
    @RequestMapping("user/list")
    public String userList(Model model) {
        model.addAttribute("value", "获取用户信息");
        return "user";
    }

    @RequiresPermissions("user:add")
    @RequestMapping("user/add")
    public String userAdd(Model model) {
        model.addAttribute("value", "新增用户");
        return "user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("user/delete")
    public String userDelete(Model model) {
        model.addAttribute("value", "删除用户");
        return "user";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }
}
