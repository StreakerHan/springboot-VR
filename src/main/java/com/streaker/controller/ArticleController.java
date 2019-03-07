package com.streaker.controller;

import com.streaker.annotation.LogAnno;
import com.streaker.entity.Article;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.ArticleService;
import com.streaker.service.LogService;
import com.streaker.service.UserService;
import com.streaker.utils.Constant;
import com.streaker.utils.DateUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 文章操作控制器类
 *
 * @author StreakerHan
 * @version 2018/10/16 15:24:11
 **/
@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    @PostMapping(value = "/add-article")
    @ResponseBody
    @LogAnno
    public ResponseBo addArticle(HttpServletRequest request, @RequestParam(value = "title" ,required = false) String title,
                                 @RequestParam(value = "cdesc",required = false) String cdesc, @RequestParam(value = "content",required = false) String content,
                                 @RequestParam(value = "username",required = false) String username, @RequestParam(value = "email",required = false) String email,
                                 @RequestParam(value = "uid",required = false) Integer uid, @RequestParam(value = "cdate",required = false) Date cdate){

        //根据subject中的用户名获取用户的id
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        articleService.addArticle(title,email,username,cdesc,content,user2.getUid(), DateUtils.dateTransToChina(new Date()),"0");
        logService.addLog(new Date(), user2.getUid(), user2.getUsername(), request.getRemoteAddr(),user2.getRole(), Constant.ADD_ARTICLE);
        return ResponseBo.ok();
    }

    /**
     * 获取文章列表
     */
    @GetMapping("/article-manage")
    @LogAnno
    public String getArticleList(HttpServletRequest request){
        List<Article> articles = articleService.getArticleList();
        request.setAttribute("articles",articles);
        return "article-manage";
    }

    /**
     * 根据id删除文章
     */
    @PostMapping("/delete/article")
    @ResponseBody
    @LogAnno
    public ResponseBo delArticle(HttpServletRequest request,@RequestParam("aid") Integer aid){
        articleService.deleteArticleById(aid);
        //根据subject中的用户名获取用户的id
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        logService.addLog(new Date(), user2.getUid(), user2.getUsername(), request.getRemoteAddr(),user2.getRole(), Constant.DELETE_ARTICLE);
        return  ResponseBo.ok();
    }

}
