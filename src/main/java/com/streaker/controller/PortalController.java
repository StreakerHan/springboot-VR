package com.streaker.controller;

import com.streaker.annotation.LogAnno;
import com.streaker.entity.Article;
import com.streaker.entity.Comment;
import com.streaker.entity.Home;
import com.streaker.entity.ResponseBo;
import com.streaker.service.AccountService;
import com.streaker.service.ArticleService;
import com.streaker.service.CommentService;
import com.streaker.service.GoodsService;
import com.streaker.utils.DateUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 门户网站控制器
 *
 * @author StreakerHan
 * @version 2018/10/17 12:09:26
 **/
@Controller
public class PortalController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AccountService accountService;


    /**
     * 前台展示文章/房屋列表
     */
    @GetMapping("/portal-index")
    public String potalIndexShow(HttpServletRequest request){
        List<Article> articles = articleService.getFormArticle();
        List<Home> homes = goodsService.getHomeRecently();
        request.setAttribute("articles", articles);
        request.setAttribute("homes", homes);
        accountService.addAccount();
        return "portal-index";
    }

    /**
     * 游客留言添加
     */
    @PostMapping("/addComment")
    @ResponseBody
    @LogAnno
    public ResponseBo addComment(HttpServletRequest request, @RequestParam(value = "username",required = false) String username,
                                 @RequestParam(value = "email",required = false) String email,
                                 @RequestParam(value = "comment",required = false) String comment,
                                 @RequestParam(value = "cdate",required = false) Date cdate){
        Comment comment1 = new Comment();
        comment1.setComment(comment);
        comment1.setUsername(username);
        comment1.setCdate(DateUtils.dateTransToChina(new Date()));
        comment1.setEmail(email);
        commentService.addComment(comment1);
        return ResponseBo.ok();

    }
}
