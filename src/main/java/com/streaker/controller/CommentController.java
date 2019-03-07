package com.streaker.controller;

import com.streaker.annotation.LogAnno;
import com.streaker.entity.Comment;
import com.streaker.entity.ResponseBo;
import com.streaker.service.CommentService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 游客留言管理控制器
 *
 * @author StreakerHan
 * @version 2018/10/17 13:41:21
 **/
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 后台获取留言列表
     * @param request
     * @return
     */
    @GetMapping("/comment-manage")
    public String getCommentList(HttpServletRequest request){
        List<Comment> comments = commentService.getCommentList();
        request.setAttribute("comments",comments);
        return "comment-manage";
    }

    /**
     * 根据id删除留言
     */
    @PostMapping("/delete/comment")
    @ResponseBody
    @LogAnno
    public ResponseBo delComment(@RequestParam(value = "cid") Integer cid){
        commentService.deleteComment(cid);
        return ResponseBo.ok();
    }
}
