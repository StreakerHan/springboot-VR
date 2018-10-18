package com.streaker.controller;

import com.streaker.entity.Article;
import com.streaker.entity.ResponseBo;
import com.streaker.entity.User;
import com.streaker.service.ArticleService;
import com.streaker.service.UserService;
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

    @PostMapping(value = "/add-article")
    @ResponseBody
    public ResponseBo addArticle(HttpServletRequest request, @RequestParam(value = "title" ,required = false) String title,
                                 @RequestParam(value = "cdesc",required = false) String cdesc, @RequestParam(value = "content",required = false) String content,
                                 @RequestParam(value = "username",required = false) String username, @RequestParam(value = "email",required = false) String email,
                                 @RequestParam(value = "uid",required = false) Integer uid, @RequestParam(value = "cdate",required = false) Date cdate){

        //根据subject中的用户名获取用户的id
        User user2 = (User) SecurityUtils.getSubject().getPrincipal();
        username = user2.getUsername();
        User user1 = userService.getUserByUsername(username);
        uid = user1.getUid();
        articleService.addArticle(title,email,username,cdesc,content,uid,new Date());
        System.out.println("######" + username + uid + title);

        return ResponseBo.ok();
    }

    /**
     * 获取文章列表
     */
    @GetMapping("/article-manage")
    public String getArticleList(HttpServletRequest request){
        List<Article> articles = articleService.getArticleList();
        request.setAttribute("articles",articles);
        System.out.println("#####文章列表");
        System.out.println(articles.toString());
        return "article-manage";
    }

    /**
     * 根据id删除文章
     */
    @PostMapping("/delete/article")
    @ResponseBody
    public ResponseBo delArticle(@RequestParam("aid") Integer aid){
        articleService.deleteArticleById(aid);
        System.out.println("删除文章" + aid + "成功！");
        return  ResponseBo.ok();
    }

}
