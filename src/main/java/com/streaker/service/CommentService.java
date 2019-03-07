package com.streaker.service;

import com.streaker.entity.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 游客留言逻辑接口类
 *
 * @author StreakerHan
 * @version 2018/10/17 13:24:38
 **/
public interface CommentService {

    /**
     * 用户提交意见接口
     */
    int addComment(Comment comment);

    /**
     *通过主键id删除意见接口
     */
    void deleteComment(@Param("cid") Integer cid);

    /**
     * 查看意见列表接口
     */
    List<Comment> getCommentList();

    /**
     * 查询已提交意见总量接口
     */
    Long getCommentCount();

    /**
     * 查看最近提交的意见接口
     */
    List<Comment> getCommentRecently();

    /**
     * 查询数量
     */
    int selectCount();
}
