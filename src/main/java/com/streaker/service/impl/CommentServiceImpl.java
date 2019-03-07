package com.streaker.service.impl;

import com.streaker.dao.CommentDao;
import com.streaker.entity.Comment;
import com.streaker.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游客留言逻辑接口实现类
 *
 * @author StreakerHan
 * @version 2018/10/17 13:25:32
 **/
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Override
    public int addComment(Comment comment) {
        return commentDao.addComment(comment);
    }

    @Override
    public void deleteComment(Integer cid) {
        commentDao.deleteComment(cid);
    }

    @Override
    public List<Comment> getCommentList() {
        List<Comment> list = commentDao.getCommentList();
        return list;
    }

    @Override
    public Long getCommentCount() {
        return null;
    }

    @Override
    public List<Comment> getCommentRecently() {
        return null;
    }

    @Override
    public int selectCount() {
        int count = commentDao.selectCount();
        return count;
    }
}
