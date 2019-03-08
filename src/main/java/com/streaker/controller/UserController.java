package com.streaker.controller;

import com.streaker.annotation.LogAnno;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.*;
import com.streaker.utils.Constant;
import com.streaker.utils.DateUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import static com.streaker.utils.MD5Utils.encrypt;


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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 登录界面展示
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 注册界面展示
     */
    @GetMapping("/userRegister")
    public String register(){
        return "userRegister";
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ResponseBo register(@RequestParam(value = "username",required = false) String username,
                               @RequestParam(value = "password",required = false) String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(encrypt(username,password));
        user.setRole("1");
        user.setPhone("");
        user.setEmail("");
        user.setUdate(DateUtils.dateTransToChina(new Date()));
        userService.addUser(user);
        return ResponseBo.ok();
    }

    /**
     * 登录认证控制
     */
    @PostMapping("/login")
    @ResponseBody
    @LogAnno
    public ResponseBo login(HttpServletRequest request, String username, String password){
        password = encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        if(request.getParameter("rememberMe")!=null){
            token.setRememberMe(true);
        }
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(token);
            //根据Subject中的用户名获取用户id
            User user2 = (User) SecurityUtils.getSubject().getPrincipal();
            //向数据库中插入日志
            //logService.addLog(new Date(), user2.getUid(), user2.getUsername(), request.getRemoteAddr(),user2.getRole(),Constant.LOGIN_MANAGE);
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
        int count = articleService.selectCount();
        model.addAttribute("articleCount",count);
        int count1 = commentService.selectCount();
        model.addAttribute("commentCount",count1);
        int count2 = goodsService.selectCount();
        model.addAttribute("goodsCount",count2);
        return page;
    }

    /**
     * 获取请求绑定的登陆对象
     * @param request
     * @return
     */
    public User user(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(null == session){
            return null;
        }
        return (User) session.getAttribute(Constant.LOGIN_SESSION_KEY);
    }
    /**
     * 更新用户信息
     */
    @PostMapping(value = "/admin/profile")
    @ResponseBody
    @LogAnno
    public ResponseBo updateUser( @RequestParam(value = "username",required=false) String username, @RequestParam(value = "email",required=false) String email,
                                 @RequestParam(value = "phone",required=false) String phone, HttpServletRequest request, HttpSession session){
        //根据subject中的用户名获取用户的id
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        User user = new User();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(email)
                && StringUtils.isNotBlank(phone)) {


            user.setUid(user2.getUid());
            user.setUsername(username);
            user.setEmail(email);
            user.setPhone(phone);
            userService.updateUser(user);

            //更新shiro中subject中的信息。如果不更新，界面显示的信息是更新之前的。
            user2.setUsername(user.getUsername());
            user2.setEmail(user.getEmail());
            user2.setPhone(user.getPhone());
        }
        logService.addLog(new Date(), user2.getUid(), user.getUsername(), request.getRemoteAddr(),user2.getRole(),Constant.UPDATE_PERSON_INFO);
        return ResponseBo.ok();
    }

    /**
     * 将用户设置为管理员
     */
    @PostMapping("/admin/setAdmin")
    @ResponseBody
    @LogAnno
    public  ResponseBo updateUserAdmin(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateUserAdmin(user);
        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user1.getUid(), user1.getUsername(), request.getRemoteAddr(),user1.getRole(),Constant.UPDATE_USER_ROLE);
        return ResponseBo.ok();
    }

    /**
     * 将用户设置为黑名单
     */
    @PostMapping("/admin/setBlack")
    @ResponseBody
    @LogAnno
    public  ResponseBo updateUserBlack(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateUserBlack(user);
        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user1.getUid(), user1.getUsername(), request.getRemoteAddr(),user1.getRole(),Constant.UPDATE_USER_ROLE);
        return ResponseBo.ok();
    }

    /**
     * 将黑名单设置为普通用户
     */
    @LogAnno
    @PostMapping("/admin/setUser")
    @ResponseBody
    public  ResponseBo updateBlackUser(@RequestParam(value = "uid",required = false) Integer uid, HttpServletRequest request){
        User user = userService.getUserById(uid);
        userService.updateBlackUser(user);
        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user1.getUid(), user1.getUsername(), request.getRemoteAddr(),user1.getRole(),Constant.UPDATE_USER_ROLE);
        return ResponseBo.ok();
    }

    /**
     * 获取用户列表
     * @param
     * @return
     */
    @LogAnno
    @GetMapping("/user-manage")
    public String getUserList(HttpServletRequest request){
        List<User> users = userService.getUserList();
        request.setAttribute("users",users);
        return "user-manage";
    }

    /**
     * 根据用户id删除用户信息
     * @param uid
     * @return
     */
    @LogAnno
    @PostMapping("/user/delete")
    @ResponseBody
    public ResponseBo delUser(HttpServletRequest request,@RequestParam(name = "uid") Integer uid){
        userService.deleteUser(uid);
        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user1.getUid(), user1.getUsername(), request.getRemoteAddr(),user1.getRole(),Constant.DELETE_USER);
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
